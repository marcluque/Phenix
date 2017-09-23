package de.datasec.phenix.client.listener;

import de.datasec.phenix.shared.PacketListener;
import de.datasec.phenix.shared.packetsystem.packets.GetPacket;

/**
 * Created by DataSec on 21.09.2017.
 */
public class PhenixClientPacketListener implements PacketListener {

    private Object value;

    public void onGetPacket(GetPacket packet) {
        System.out.println("Received packet from server: " + packet.getValue());
    }
}