package gay.shoroa.devclient.mixin.client;

import gay.shoroa.devclient.client.Client;
import gay.shoroa.devclient.client.screen.ScreenMainMenu;
import gay.shoroa.devclient.client.ui.nvg.UI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {
    @Inject(method = "initGui", at = @At("HEAD"))
    public void injetc$init(CallbackInfo ci) {
        Minecraft.getMinecraft().displayGuiScreen(new ScreenMainMenu());
    }
}
