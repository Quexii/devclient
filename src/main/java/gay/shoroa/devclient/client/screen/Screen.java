package gay.shoroa.devclient.client.screen;

import gay.shoroa.devclient.client.ui.nvg.UI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class Screen extends GuiScreen {
    public abstract void init();
    public abstract void render(UI ui, float mx, float my, float delta);
    public abstract void click(float mx, float my, int button);

    protected int width = Display.getWidth();
    protected int height = Display.getHeight();

    @Override
    public void initGui() {
        init();
        super.initGui();
    }

    @Override
    public void drawScreen(int p_73863_0_, int mouseX, float mouseY) {
        UI.get().render(nvg->{
            render(nvg, Mouse.getX(), Display.getHeight()-Mouse.getY(), Math.min(1f / Minecraft.getDebugFPS(), 0.07f));
        }, Display.getWidth(), Display.getHeight(), 1);
        super.drawScreen(p_73863_0_, mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int p_73864_0_, int mouseX, int mouseY) {
        click(Mouse.getX(), Display.getHeight()-Mouse.getY(),mouseY);
        super.mouseClicked(p_73864_0_, mouseX, mouseY);
    }
}
