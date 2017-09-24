package de.datasec.phenix.server.handler;

import de.datasec.phenix.server.listener.PhenixServerPacketListener;
import de.datasec.phenix.shared.PacketListener;
import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by DataSec on 20.09.2017.
 */
public class PhenixServerHandler extends SimpleChannelInboundHandler<Packet> {

    private PhenixServerPacketListener packetListener;

    public PhenixServerHandler(PacketListener packetListener) {
        this.packetListener = (PhenixServerPacketListener) packetListener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, Packet packet) throws Exception {
        packetListener.setContext(context);
        packetListener.onPacket(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}