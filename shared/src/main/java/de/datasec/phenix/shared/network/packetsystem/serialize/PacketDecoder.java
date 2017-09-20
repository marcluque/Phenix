package de.datasec.phenix.shared.network.packetsystem.serialize;

import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.network.packetsystem.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    private Protocol protocol;

    public PacketDecoder(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readInt();

        if(length > 0) {
            Packet packet = protocol.createPacket(in.readByte());
            packet.read(in);
            out.add(packet);
        }
    }
}
