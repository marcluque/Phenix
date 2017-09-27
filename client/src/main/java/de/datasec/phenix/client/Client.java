package de.datasec.phenix.client;

import de.datasec.phenix.client.listener.PhenixClientPacketListener;
import de.datasec.phenix.shared.Protocol;
import de.datasec.phenix.shared.initializer.PhenixChannelInitializer;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.packets.*;
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

    private AtomicReference<Object> responseObject;

    private CountDownLatch countDownLatch;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        protocol = new Protocol();
        protocol.registerPacket((byte) 0, GetPacket.class);
        protocol.registerPacket((byte) 1, PutPacket.class);
        protocol.registerPacket((byte) 2, ContainsPacket.class);
        protocol.registerPacket((byte) 3, RemovePacket.class);
        protocol.registerPacket((byte) 4, TypeOfPacket.class);
        protocol.registerPacket((byte) 5, GetKeysPacket.class);
        protocol.registerPacket((byte) 6, RandomKeyPacket.class);
        protocol.registerPacket((byte) 7, RenamePacket.class);

        packetListener = new PhenixClientPacketListener(this);

        System.out.printf("Client started and connected on: %s:%d%n", host, port);
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

    public void send(Packet packet) {
        channel.writeAndFlush(packet);
    }

    public Future<Object> sendWithFuture(Packet packet) {
        responseObject = new AtomicReference<>();
        countDownLatch = new CountDownLatch(2);

        return executorService.submit(() -> {
            channel.writeAndFlush(packet).addListener(channelFutureListener  -> countDownLatch.countDown());

            try {
                countDownLatch.await(1, TimeUnit.SECONDS);
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