package de.datasec.phenix.server.listener;

import de.datasec.phenix.server.cache.PhenixServerCache;
import de.datasec.phenix.shared.PacketListener;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.packets.ContainsPacket;
import de.datasec.phenix.shared.packetsystem.packets.GetPacket;
import de.datasec.phenix.shared.packetsystem.packets.PutPacket;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixServerPacketListener implements PacketListener {

    private PhenixServerCache cache;

    private ChannelHandlerContext context;

    public PhenixServerPacketListener(PhenixServerCache cache) {
        this.cache = cache;
    }

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
            default:
                throw new IllegalArgumentException(String.format("packet with id %d is not registered", packet.getId()));
        }
    }

    private void onGetPacket(GetPacket getPacket) {
        context.writeAndFlush(new GetPacket(cache.get(getPacket.getValue())));
    }

    private void onPutPacket(PutPacket putPacket) {
        cache.put(putPacket.getKey(), putPacket.getValue(), putPacket.isOverrideIfKeyExists() == 1, putPacket.getTimeToLive());
    }

    private void onContainsPacket(ContainsPacket packet) {
        context.writeAndFlush((packet.isKey() == 1) ? new ContainsPacket(cache.containsKey(packet.getValue())) : new ContainsPacket(cache.containsValue(packet.getValue())));
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }
}
