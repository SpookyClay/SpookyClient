    package com.SpookyClientSide.Features;

    import com.SpookyClientSide.SpookyClient;
import com.SpookyClientSide.Commands.CommandManager;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

    public class Autofisher {
        private static boolean enabled = false;
        private static boolean isFishing = false;
        private static int tickCounter = 0;

        public static void enable() {
            enabled = !enabled;
            SpookyClient.sendChatMessage("Autofisher " + (enabled ? "enabled" : "disabled"));
        }

        public static void init() {
            CommandManager.register("autofish", Autofisher::enable);

            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if (!enabled || client.player == null || client.world == null) return;

                if (client.player.getMainHandStack().getItem() == Items.FISHING_ROD) {
                    tickCounter++;
                    if (!isFishing) {
                        isFishing = true;
                        client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                        SpookyClient.sendChatMessage("Casting fishing rod...");
                        if (tickCounter < 16) return;
                        FishingBobberEntity bobber = client.player.fishHook; //Get the fishing bobber entity thingy

                        if (bobber != null) {
                            double velocityY = bobber.getVelocity().y;
                            SpookyClient.sendChatMessage(String.format("[DEBUG] Bobber velocityY: %.3f", velocityY));
                            if (velocityY < -0.15) {
                                SpookyClient.sendChatMessage("[DEBUG] Bobber sinking, reeling in...");
                                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                                isFishing = false;
                                tickCounter = 0;
                            }
                        }
                        else {
                            SpookyClient.sendChatMessage("[DEBUG] No bobber found, waiting...");
                        }
                    }
                }
            });
        }
    }