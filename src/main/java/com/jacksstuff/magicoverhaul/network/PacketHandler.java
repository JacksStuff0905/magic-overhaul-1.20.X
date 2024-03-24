package com.jacksstuff.magicoverhaul.network;

import com.jacksstuff.magicoverhaul.MagicOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(MagicOverhaul.MOD_ID, "main"))
            .serverAcceptedVersions(((status, version) -> true))
            .clientAcceptedVersions(((status, version) -> true))
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        //SPacketName -> packet is sent from client to server
        //CPacketName -> packet is sent from server to client

        INSTANCE.messageBuilder(SWandUseSpawnEntityPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SWandUseSpawnEntityPacket::encode)
                .decoder(SWandUseSpawnEntityPacket::new)
                .consumerMainThread(SWandUseSpawnEntityPacket::handle)
                .add();


    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }
}
