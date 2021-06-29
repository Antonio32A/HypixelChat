package com.antonio32a.hypixelchat;

import com.antonio32a.hypixelchat.handlers.SocketHandler;
import net.fabricmc.api.ModInitializer;

public class HypixelChat implements ModInitializer {
	public static HypixelChat instance;
	public SocketHandler socketHandler;

	public HypixelChat() {
		this.socketHandler = new SocketHandler();
	}

	@Override
	public void onInitialize() {
		instance = this;
		System.out.println("Loaded HypixelChat.");
	}

	public static HypixelChat getInstance() {
		return instance;
	}
}
