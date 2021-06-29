package com.antonio32a.hypixelchat.handlers;

import io.socket.client.IO;
import io.socket.client.Socket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

import java.net.URI;

public class SocketHandler {
    public Socket socket;

    public SocketHandler() {
        socket = setupSocket();
    }

    public void sendChatMessage(String message) {
        socket.emit("chat", message);
    }

    public Socket setupSocket() {
        URI uri = URI.create("http://localhost:8936/");
        Socket socket = IO.socket(uri);

        socket.connect();
        socket.on("alert", args -> {
            if (MinecraftClient.getInstance().inGameHud != null)
                MinecraftClient.getInstance().inGameHud.addChatMessage(
                    MessageType.CHAT,
                    new LiteralText("§a§lHypixelChat §r§b> §d" + args[0]),
                    Util.NIL_UUID
                );
        });

        socket.on("chat", args -> {
            if (MinecraftClient.getInstance().inGameHud != null)
                MinecraftClient.getInstance().inGameHud.addChatMessage(
                    MessageType.CHAT,
                    new LiteralText((String) args[0]),
                    Util.NIL_UUID
                );
        });
        return socket;
    }
}
