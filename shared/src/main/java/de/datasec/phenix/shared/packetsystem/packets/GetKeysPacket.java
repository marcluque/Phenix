package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.buffer.ByteBuf;

/**
 * Created by DataSec on 26.09.2017.
 */
public class GetKeysPacket extends Packet {

    public GetKeysPacket() {
        id = 5;
        // For protocol
    }

    @Override
    public void read(ByteBuf byteBuf) {}

    @Override
    public void write(ByteBuf byteBuf) {}
}
