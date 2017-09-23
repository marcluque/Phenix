package de.datasec.phenix.client;

import de.datasec.phenix.client.listener.PhenixClientPacketListener;
import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.initializer.PhenixChannelInitializer;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.packets.GetPacket;
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

    private PhenixClientPacketListener packetListener;

    private ExecutorService executorService;

    private AtomicReference<Object> responseObject;

    private CountDownLatch countDownLatch;

    private Object responseValue;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        protocol = new Protocol();
        protocol.registerPacket((byte) 5, GetPacket.class);

        packetListener = new PhenixClientPacketListener();

        executorService = Executors.newSingleThreadExecutor();

        responseObject = new AtomicReference<>();
        countDownLatch = new CountDownLatch(1);
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
            responseObject.set(responseValue);
            countDownLatch.countDown();

            channel.writeAndFlush(packet);

            try {
                countDownLatch.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return responseObject.get();
        });
    }
}