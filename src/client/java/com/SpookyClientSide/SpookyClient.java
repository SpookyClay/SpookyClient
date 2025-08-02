package com.SpookyClientSide;

import com.SpookyClientSide.Commands.CommandManager;
import com.SpookyClientSide.Features.Autofisher;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class SpookyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Autofisher.init();

        ClientSendMessageEvents.ALLOW_CHAT.register(message -> {
            String trimmed = message.trim().toLowerCase();

            // ✅ Strip leading slash if present
            if (trimmed.startsWith("/")) {
                trimmed = trimmed.substring(1);
            }

            // ✅ Execute command if registered
            if (CommandManager.executeIfExists(trimmed)) {
                return false; // Cancel message
            }

            return true; // Let message go through
        });
    }

    public static void sendChatMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.player != null) {
            client.player.sendMessage(Text.literal("[SpookyClient] " + message), false);
        }
    }
}