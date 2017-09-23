package de.datasec.phenix.server.handler;

import de.datasec.phenix.server.listener.PhenixServerPacketListener;
import de.datasec.phenix.shared.PacketListener;
import de.datasec.phenix.shared.packetsystem.packets.GetPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by DataSec on 20.09.2017.
 */
public class PhenixServerHandler extends ChannelInboundHandlerAdapter {

    private PhenixServerPacketListener packetListener;

    public PhenixServerHandler(PacketListener packetListener) {
        this.packetListener = (PhenixServerPacketListener) packetListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
        context.writeAndFlush(new GetPacket("Object1"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
