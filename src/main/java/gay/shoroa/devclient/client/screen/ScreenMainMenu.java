package gay.shoroa.devclient.client.screen;

import gay.shoroa.devclient.client.ui.anim.Animation;
import gay.shoroa.devclient.client.ui.nvg.UI;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class ScreenMainMenu extends Screen {
    float a1 = 0;
    float a2 = 0;
    float a3 = 0;
    @Override
    public void init() {

    }

    @Override
    public void render(UI ui, float mx, float my, float delta) {
        if(!Float.isNaN(delta)) {
            a1 += (200-a1) * delta * 3;
            if(a1 > 60)
                a2 += (200-a2) * delta * 3;
            if(a2 > 60)
                a3 += (200-a3) * delta * 3;
        }
        if(Float.isInfinite(a1)) a1 = 0;
        if(Float.isInfinite(a2)) a2 = 0;
        if(Float.isInfinite(a3)) a3 = 0;
        ui.rect(0,0,width, height, new Color(0x5FBB79));
        ui.text(width/2f,height/2f,80,"bold", "devClient", new Color(0x81E39D), UI.Alignment.CENTER_MIDDLE);
        ui.text(width/2f,height/2f+30,16,"bold", "by shoroa", new Color(0x81E39D), UI.Alignment.CENTER_MIDDLE);

        Color c = new Color(47,47,47);
        ui.line(width/2f-50+400,height+20,width/2f+50+400,-20,200-a3,c);
        ui.line(width/2f-50+200,height+20,width/2f+50+200,-20,200-a2,c);
        ui.line(width/2f-50,height+20,width/2f+50,-20,200-a1,c);
        ui.line(width/2f-50-200,height+20,width/2f+50-200,-20,200-a2,c);
        ui.line(width/2f-50-400,height+20,width/2f+50-400,-20,200-a3,c);

        ui.image(Display.getWidth()/2f-50,Display.getHeight()/2f-80,100,100,"logo256",1f-a1/200f,0);
        ui.text(Display.getWidth()/2f,Display.getHeight()/2f+70,50,"regular", "devClient", new Color(1f,1f,1f,1f-a1/200f), UI.Alignment.CENTER_MIDDLE);
    }

    @Override
    public void click(float mx, float my, int button) {

    }
}
