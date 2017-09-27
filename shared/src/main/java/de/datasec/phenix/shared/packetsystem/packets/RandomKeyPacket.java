package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.buffer.ByteBuf;

/**
 * Created by DataSec on 26.09.2017.
 */
public class RandomKeyPacket extends Packet {

    public RandomKeyPacket() {
        id = 6;
        // For protocol
    }

    @Override
    public void read(ByteBuf byteBuf) {}

    @Override
    public void write(ByteBuf byteBuf) {}
}
