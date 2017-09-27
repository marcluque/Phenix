package de.datasec.phenix.server.listener;

import de.datasec.phenix.server.cache.PhenixServerCache;
import de.datasec.phenix.shared.PacketListener;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.packets.*;
import io.netty.channel.ChannelHandlerContext;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixServerPacketListener implements PacketListener {

    private PhenixServerCache cache;

    private ChannelHandlerContext context;

    public PhenixServerPacketListener(PhenixServerCache cache) {
        this.cache = cache;
    }

    @Override
    public void onPacket(Packet packet) {
        switch (packet.getId()) {
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
            default:
                throw new IllegalArgumentException(String.format("packet with id %d is not registered", packet.getId()));
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
        if (removePacket.getValue() instanceof Object[]) {
            long amountRemoved = 0;
            Object[] objects = (Object[]) removePacket.getValue();
            for (Object object : objects) {
                if (cache.remove(object)) {
                    amountRemoved++;
                }
            }

            context.writeAndFlush(new GetPacket(amountRemoved));
        } else {
            context.writeAndFlush(new GetPacket(cache.getAndRemove(removePacket.getValue())));
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
        context.writeAndFlush(new GetPacket(cache.rename(renamePacket.getOldKey(), renamePacket.getNewKey())));
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }
}