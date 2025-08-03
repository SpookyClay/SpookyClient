package com.SpookyClientSide;

import com.SpookyClientSide.Commands.CommandManager;
import com.SpookyClientSide.Features.Autofisher;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import java.util.Random;

public class SpookyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Autofisher.init();

    ClientSendMessageEvents.ALLOW_CHAT.register(message -> {
        String trimmed = message.trim().toLowerCase();
        if (trimmed.startsWith(".")) {
            String command = trimmed.substring(1);
            if (CommandManager.executeIfExists(command)) {
                return false; 
            }
            return false; 
        }
        if (trimmed.startsWith(",")) {
            String command = trimmed.substring(1);
            if (CommandManager.executeIfExists(command)) {
                return false; 
            }
            return false; 
        }

        return true; 
    });

    }

    public static void sendChatMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.player != null) {
            client.player.sendMessage(Text.literal("[SpookyClient] " + message), false);
        }
    }
    
    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}