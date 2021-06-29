package com.antonio32a.hypixelchat.mixin;

import com.antonio32a.hypixelchat.HypixelChat;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
	@Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
	private void sendChatMessage(String message, CallbackInfo ci) {
		if (message.startsWith("hy ")) {
			HypixelChat.getInstance().socketHandler.sendChatMessage(message.substring(3));
			ci.cancel();
		}
	}
}
