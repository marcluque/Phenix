package de.datasec.phenix.shared.network.initializer;

import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.network.packetsystem.serialize.PacketDecoder;
import de.datasec.phenix.shared.network.packetsystem.serialize.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixChannelInitializer extends ChannelInitializer<SocketChannel> {

    private boolean isServer;

    private Protocol packetProtocol;

    public PhenixChannelInitializer(boolean isServer, Protocol packetProtocol) {
        this.isServer = isServer;
        this.packetProtocol = packetProtocol;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // In
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
        pipeline.addLast(new PacketDecoder(packetProtocol));

        // Out
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new PacketEncoder(packetProtocol));

        //pipeline.addLast(isServer ? new PhenixServerHandler() : new PhenixClientHandler());
    }
}
