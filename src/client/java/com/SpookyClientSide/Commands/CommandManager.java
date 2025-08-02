package com.SpookyClientSide.Commands;

import com.SpookyClientSide.SpookyClient;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Map<String, Runnable> commandMap = new HashMap<>();

    public static void register(String name, Runnable action) {
        commandMap.put(name.toLowerCase(), action);
    }

    public static boolean hasCommand(String name) {
        return commandMap.containsKey(name.toLowerCase());
    }

    public static boolean executeIfExists(String name) {
        Runnable command = commandMap.get(name.toLowerCase());
        if (command != null) {
            command.run();
            return true;
        } else {
            SpookyClient.sendChatMessage("Command not found: " + name);
            return false;
        }
    }
}