package de.datasec.phenix.client.network;

import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.network.initializer.PhenixChannelInitializer;
import de.datasec.phenix.shared.network.packetsystem.Packet;
import de.datasec.phenix.shared.network.packetsystem.packets.GetPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by DataSec on 05.09.2017.
 */
public class Client {

    private String host;

    private int port;

    private Channel channel;

    private Protocol protocol;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        protocol.registerPacket((byte) 5, GetPacket.class);
    }

    public void start() throws Exception {
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new PhenixChannelInitializer(false, protocol));

        // Start the client
        channel = bootstrap.connect(host, port).sync().channel();
    }

    public <T> T send(Packet packet) {

        return null;
    }
}
