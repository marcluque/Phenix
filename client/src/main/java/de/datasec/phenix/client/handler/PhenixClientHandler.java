package de.datasec.phenix.client.handler;

import de.datasec.phenix.client.listener.PhenixClientPacketListener;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by DataSec on 20.09.2017.
 */
public class PhenixClientHandler extends SimpleChannelInboundHandler<Packet> {

    private PhenixClientPacketListener packetListener;

    public PhenixClientHandler(PacketListener packetListener) {
        this.packetListener = (PhenixClientPacketListener) packetListener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, Packet packet) throws Exception {
        packetListener.onPacket(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}