package de.datasec.phenix.client.handler;

import de.datasec.phenix.client.listener.PhenixClientPacketListener;
import de.datasec.phenix.shared.PacketListener;
import de.datasec.phenix.shared.packetsystem.packets.GetPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by DataSec on 20.09.2017.
 */
public class PhenixClientHandler extends ChannelInboundHandlerAdapter {

    private PhenixClientPacketListener packetListener;

    public PhenixClientHandler(PacketListener packetListener) {
        this.packetListener = (PhenixClientPacketListener) packetListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
        packetListener.onGetPacket((GetPacket) packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
