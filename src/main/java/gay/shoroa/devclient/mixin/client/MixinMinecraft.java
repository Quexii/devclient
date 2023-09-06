package gay.shoroa.devclient.mixin.client;

import gay.shoroa.devclient.Mod;
import gay.shoroa.devclient.client.Client;
import gay.shoroa.devclient.client.event.impl.EventRenderSplashScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow @Final private static Logger logger;

    @Shadow protected abstract ByteBuffer readImageToBuffer(InputStream p_152340_0_) throws IOException;

    @Shadow public abstract void shutdownMinecraftApplet();

    @Shadow public abstract void displayCrashReport(CrashReport p_71377_0_);

    @Shadow public abstract void freeMemory();

    @Shadow public abstract CrashReport addGraphicsAndWorldToCrashReport(CrashReport p_71396_0_);

    @Shadow private CrashReport crashReporter;

    @Shadow public abstract void displayGuiScreen(GuiScreen p_147108_0_);

    @Shadow private boolean hasCrashed;

    @Shadow protected abstract void runGameLoop();

    @Shadow private volatile boolean running;

    @Shadow protected abstract void startGame() throws LWJGLException, IOException;

    @ModifyArg(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private String  modarg1$setWindowIcon(String title){
        return "devclient";
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void setWindowIcon() {
        Util.EnumOS enumOS = Util.getOSType();
        if (enumOS != Util.EnumOS.OSX) {
            InputStream inputStream = null;
            InputStream inputStream2 = null;

            try {
                inputStream = Mod.class.getResourceAsStream("/assets/devclient/img/icon_32.png");
                inputStream2 = Mod.class.getResourceAsStream("/assets/devclient/img/icon_256.png");
                if (inputStream != null && inputStream2 != null) {
                    Display.setIcon(new ByteBuffer[]{this.readImageToBuffer(inputStream), this.readImageToBuffer(inputStream2)});
                }
            } catch (IOException var8) {
                logger.error("Couldn't set icon", var8);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(inputStream2);
            }
        }

    }
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;<init>(Lnet/minecraft/client/Minecraft;)V", shift = At.Shift.AFTER))
    private void inject$initguiingame$startGame(CallbackInfo ci) {
        Client.get().gameInit();
    }
//    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;drawSplashScreen(Lnet/minecraft/client/renderer/texture/TextureManager;)V", shift = At.Shift.AFTER))
//    private void inject$drawsplashscreen$startGame(CallbackInfo ci) {
//        Client.get().uiInit();
//    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void drawSplashScreen(TextureManager tm) {
        Client.get().uiInit();
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void inject$shutdown(CallbackInfo ci) {
        Client.get().gameShutdown();
    }
}
