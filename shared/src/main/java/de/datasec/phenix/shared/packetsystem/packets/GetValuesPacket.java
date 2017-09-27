package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketId;
import io.netty.buffer.ByteBuf;

/**
 * Created by DataSec on 27.09.2017.
 */
@PacketId(9)
public class GetValuesPacket extends Packet {

    public GetValuesPacket() {
        // For protocol
    }

    @Override
    public void read(ByteBuf byteBuf) {}

    @Override
    public void write(ByteBuf byteBuf) {}
}
