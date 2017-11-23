package de.datasec.phenix.server.initializer;

import de.datasec.phenix.server.handler.PhenixServerHandler;
import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.packetsystem.PacketListener;
import de.datasec.phenix.shared.packetsystem.serialize.PacketDecoder;
import de.datasec.phenix.shared.packetsystem.serialize.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixChannelInitializer extends ChannelInitializer<SocketChannel> {

    private Protocol protocol;

    private PacketListener packetListener;

    public PhenixChannelInitializer(Protocol protocol, PacketListener packetListener) {
        this.protocol = protocol;
        this.packetListener = packetListener;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // In
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
        pipeline.addLast(new PacketDecoder(protocol));

        // Out
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new PacketEncoder(protocol));

        pipeline.addLast(new PhenixServerHandler(packetListener));
    }
}