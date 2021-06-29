package com.antonio32a.hypixelchat;

import com.antonio32a.hypixelchat.handlers.SocketHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class HypixelChat implements ModInitializer {
	public static HypixelChat instance;
	private static KeyBinding quickHypixelChatKeybind;
	public SocketHandler socketHandler;

	public HypixelChat() {
		this.socketHandler = new SocketHandler();
	}

	@Override
	public void onInitialize() {
		instance = this;
		registerQuickHypixelChatKeybind();
		System.out.println("Loaded HypixelChat.");
	}

	private static void registerQuickHypixelChatKeybind() {
		quickHypixelChatKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.hypixelchat.quickhypixelchat",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_R,
			"category.hypixelchat.main"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (quickHypixelChatKeybind.wasPressed())
				if (client.player != null)
					client.openScreen(new ChatScreen("hy "));
		});
	}

	public static HypixelChat getInstance() {
		return instance;
	}
}
