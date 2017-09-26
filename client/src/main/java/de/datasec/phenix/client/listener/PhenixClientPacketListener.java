package de.datasec.phenix.client.listener;

import de.datasec.phenix.client.Client;
import de.datasec.phenix.shared.PacketListener;
import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.packets.GetPacket;

/**
 * Created by DataSec on 21.09.2017.
 */
public class PhenixClientPacketListener implements PacketListener {

    private Client client;

    public PhenixClientPacketListener(Client client) {
        this.client = client;
    }

    @Override
    public void onPacket(Packet packet) {
        if (packet.getId() == 0) {
            client.setResponseObject(((GetPacket) packet).getObject());
        } else {
            throw new IllegalArgumentException(String.format("packet with id %d is not registered", packet.getId()));
        }
    }
}