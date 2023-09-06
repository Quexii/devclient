package gay.shoroa.devclient.client;

import gay.shoroa.devclient.client.event.Event;
import gay.shoroa.devclient.client.event.impl.EventRenderSplashScreen;
import gay.shoroa.devclient.client.ui.anim.Animation;
import gay.shoroa.devclient.client.ui.nvg.NVGHelper;
import gay.shoroa.devclient.client.ui.nvg.NVGWrapper;
import gay.shoroa.devclient.client.ui.nvg.UI;
import gay.shoroa.devclient.client.util.LinkedStorage;
import io.github.nevalackin.homoBus.Listener;
import io.github.nevalackin.homoBus.annotations.EventLink;
import io.github.nevalackin.homoBus.bus.impl.EventBus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class Client implements ClientModInitializer {
    private static Client instance;
    private final Minecraft mc = Minecraft.getMinecraft();
    public final EventBus<Event> bus = new EventBus<>();

    public static Client get() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        instance = this;
        bus.subscribe(this);
    }

    public void gameInit() {

    }
    public void uiInit() {
        UI.init();
        String[] fonts = {"black", "bold", "extrabold", "regular", "thin"};
        for (String font : fonts)
            NVGHelper.initFont(font,font+".otf");
        NVGHelper.initImage("logo256", "icon_256.png");
//        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
//        int i = scaledResolution.getScaleFactor();
//        Framebuffer loadingFBO = new Framebuffer(scaledResolution.getScaledWidth() * i, scaledResolution.getScaledHeight() * i, true);
//        loadingFBO.bindFramebuffer(false);
        UI.get().render(nvg -> {
            nvg.rect(0,0,Display.getWidth(),Display.getHeight(), new Color(0x2F2F2F));
            nvg.image(Display.getWidth()/2f-50,Display.getHeight()/2f-80,100,100,"logo256",1f,0);
            nvg.text(Display.getWidth()/2f,Display.getHeight()/2f+70,50,"regular", "devClient", Color.WHITE, UI.Alignment.CENTER_MIDDLE);
        }, Display.getWidth(), Display.getHeight(), 1);
//        loadingFBO.unbindFramebuffer();
//        loadingFBO.framebufferRender(scaledResolution.getScaledWidth() * i, scaledResolution.getScaledHeight() * i);
        Minecraft.getMinecraft().updateDisplay();
    }

    public void gameShutdown() {
        bus.unsubscribe(this);
        LinkedStorage.free();
    }
}
