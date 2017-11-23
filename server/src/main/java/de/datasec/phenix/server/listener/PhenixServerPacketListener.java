package de.datasec.phenix.server.listener;

import de.datasec.phenix.server.cache.PhenixServerCache;
import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketListener;
import de.datasec.phenix.shared.packetsystem.packets.*;
import io.netty.channel.ChannelHandlerContext;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixServerPacketListener implements PacketListener {

    private Protocol protocol;

    private PhenixServerCache cache;

    private ChannelHandlerContext context;

    public PhenixServerPacketListener(Protocol protocol, PhenixServerCache cache) {
        this.protocol = protocol;
        this.cache = cache;
    }

    // TODO: Probably start to support filter options, when querying for specific keys or values
    @Override
    public void onPacket(Packet packet) {
        byte id = protocol.getPacketId(packet);

        switch (id) {
            case 0:
                onGetPacket((GetPacket) packet);
                break;
            case 1:
                onPutPacket((PutPacket) packet);
                break;
            case 2:
                onContainsPacket((ContainsPacket) packet);
                break;
            case 3:
                onRemovePacket((RemovePacket) packet);
                break;
            case 4:
                onTypeOfPacket((TypeOfPacket) packet);
                break;
            case 5:
                onGetKeysPacket();
                break;
            case 6:
                onRandomKeyPacket();
                break;
            case 7:
                onRenamePacket((RenamePacket) packet);
                break;
            case 8:
                onTimeToLivePacket((TimeToLivePacket) packet);
                break;
            case 9:
                onGetValuesPacket();
                break;
            default:
                throw new IllegalArgumentException(String.format("packet with id %d is not registered", id));
        }
    }

    private void onGetPacket(GetPacket getPacket) {
        context.writeAndFlush(new GetPacket(cache.get(getPacket.getObject())));
    }

    private void onPutPacket(PutPacket putPacket) {
        cache.put(putPacket.getKey(), putPacket.getValue(), putPacket.isOverrideIfKeyExists() == 1, putPacket.getTimeToLive());
    }

    private void onContainsPacket(ContainsPacket containsPacket) {
        context.writeAndFlush((containsPacket.isKey() == 1) ? new GetPacket(cache.containsKey(containsPacket.getObject())) : new GetPacket(cache.containsValue(containsPacket.getObject())));
    }

    private void onRemovePacket(RemovePacket removePacket) {
        if (removePacket.getObject() instanceof Object[]) {
            long amountRemoved = 0;
            Object[] objects = (Object[]) removePacket.getObject();
            for (Object object : objects) {
                if (cache.remove(object)) {
                    amountRemoved++;
                }
            }

            context.writeAndFlush(new GetPacket(amountRemoved));
        } else {
            context.writeAndFlush(new GetPacket(cache.getAndRemove(removePacket.getObject())));
        }
    }

    private void onTypeOfPacket(TypeOfPacket typeOfPacket) {
        context.writeAndFlush(new GetPacket(cache.get(typeOfPacket.getObject()).getClass().getTypeName()));
    }

    private void onGetKeysPacket() {
        context.writeAndFlush(new GetPacket(cache.getKeys()));
    }

    private void onRandomKeyPacket() {
        Set<Object> set = cache.getKeys();
        context.writeAndFlush(new GetPacket(set.toArray()[ThreadLocalRandom.current().nextInt(0, set.size())]));
    }

    private void onRenamePacket(RenamePacket renamePacket) {
        Object obj = cache.rename(renamePacket.getOldKey(), renamePacket.getNewKey());
        System.out.println(obj);
        context.writeAndFlush(new GetPacket(obj));
    }

    private void onTimeToLivePacket(TimeToLivePacket timeToLivePacket) {
        if (timeToLivePacket.getReturnTTL() == 1) {
            context.writeAndFlush(new GetPacket(cache.getTimeToLive(timeToLivePacket.getObject())));
        } else {
            context.writeAndFlush(new GetPacket(cache.setTimeToLive(timeToLivePacket.getObject(), timeToLivePacket.getTimeToLive())));
        }
    }

    private void onGetValuesPacket() {
        context.writeAndFlush(new GetPacket(cache.getValues()));
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }
}