package de.datasec.phenix.shared;

import de.datasec.phenix.shared.packetsystem.Packet;

/**
 * Created by DataSec on 21.09.2017.
 */
public interface PacketListener {

    void onPacket(Packet packet);
}
