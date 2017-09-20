package de.datasec.phenix.shared.network.packetsystem.serialize;

import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.network.packetsystem.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PacketEncoder extends MessageToMessageEncoder<Packet> {

    private Protocol protocol;

    public PacketEncoder(Protocol packetProtocol) {
        this.protocol = packetProtocol;
    }

    @Override
    protected void encode(ChannelHandlerContext context, Packet packet, List<Object> out) throws Exception {
        ByteBuf byteBuf = context.alloc().buffer();
        byteBuf.writeByte(protocol.getPacketId(packet));
        packet.write(byteBuf);
        out.add(byteBuf);
    }
}
