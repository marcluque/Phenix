package de.datasec.phenix.client;

import de.datasec.phenix.client.listener.PhenixClientPacketListener;
import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.initializer.PhenixChannelInitializer;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.packets.GetPacket;
import de.datasec.phenix.shared.packetsystem.packets.PutPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by DataSec on 05.09.2017.
 */
public class Client {

    private String host;

    private int port;

    private Channel channel;

    private Protocol protocol;

    private PhenixClientPacketListener packetListener = new PhenixClientPacketListener(this);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private AtomicReference<Object> responseObject = new AtomicReference<>();

    private CountDownLatch countDownLatch = new CountDownLatch(2);

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        protocol = new Protocol();
        protocol.registerPacket((byte) 1, GetPacket.class);
        protocol.registerPacket((byte) 2, PutPacket.class);
    }

    public void start() throws Exception {
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new PhenixChannelInitializer(false, protocol, packetListener));

        // Start the client
        channel = bootstrap.connect(host, port).sync().channel();
    }

    public Future<Object> send(Packet packet) {
        return executorService.submit(() -> {
            channel.writeAndFlush(packet);

            countDownLatch.countDown();

            try {
                countDownLatch.await(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return responseObject.get();
        });
    }

    public <T> void setResponseObject(T responseObject) {
        this.responseObject.set(responseObject);
    }
}