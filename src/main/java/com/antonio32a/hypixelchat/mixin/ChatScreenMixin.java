package com.antonio32a.hypixelchat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {
    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Shadow protected TextFieldWidget chatField;

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/ChatScreen;fill(Lnet/minecraft/client/util/math/MatrixStack;IIIII)V"
        ),
        method = "render"
    )
    public void renderOutline(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (chatField.getText().startsWith("hy ")) {
            int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
            int a = 2;
            int b = this.height - 14;
            int c = this.width - 2;

            // this is extremely, extremely scuffed, but it's currently 1am
            // TODO fix this
            drawVerticalLine(matrices, width - a, height - a, b - 1, 0x99FFFF55);
            drawVerticalLine(matrices, 1, height - a, b - 1, 0x99FFFF55);
            drawHorizontalLine(matrices, a - 1, c, b - 1, 0x99FFFF55);
            drawHorizontalLine(matrices, a - 1, c, height - a, 0x99FFFF55);
        }
    }

    protected void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int color) {
        if (x2 < x1) {
            int i = x1;
            x1 = x2;
            x2 = i;
        }
        DrawableHelper.fill(matrices, x1, y, x2 + 1, y + 1, color);
    }

    protected void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
        if (y2 < y1) {
            int i = y1;
            y1 = y2;
            y2 = i;
        }
        DrawableHelper.fill(matrices, x, y1 + 1, x + 1, y2, color);
    }
}
