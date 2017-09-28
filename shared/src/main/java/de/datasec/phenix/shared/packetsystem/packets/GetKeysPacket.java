package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketId;
import io.netty.buffer.ByteBuf;

/**
 * Created by DataSec on 26.09.2017.
 */
@PacketId(5)
public class GetKeysPacket extends Packet {

    public GetKeysPacket() {
        // For protocol
    }

    @Override
    public void read(ByteBuf byteBuf) {}

    @Override
    public void write(ByteBuf byteBuf) {}
}