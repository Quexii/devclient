package gay.shoroa.devclient.client.ui.nvg;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.function.Consumer;

import static gay.shoroa.devclient.client.ui.nvg.NVGHelper.*;
import static gay.shoroa.devclient.client.ui.nvg.NVGWrapper.*;
import static org.lwjgl.nanovg.NanoVG.*;

public class UI {
    private static UI instancce;
    private static boolean initialized = false;
    public static HashMap<String, Integer> imagesmap = new HashMap<>();
    private long context = -1;

    public static void init() {
        if(!initialized) {
            new UI();
            initialized = true;
        }
    }

    public static UI get() {
        return instancce;
    }

    private UI() {
        instancce = this;

        context = NanoVGGL2.nvgCreate(NanoVGGL2.NVG_ANTIALIAS);
        NVGWrapper.cx = context;
    }


    public void render(Consumer<UI> renderer, float width, float height, float dpi) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        NVGWrapper.beginFrame(width,height,dpi);
        renderer.accept(this);
        NVGWrapper.endFrame();
        GL11.glPopAttrib();
    }

    public void rect(float x, float y, float width, float height, Color color) {
        NVGColor col = nvgcolor(color);
        path(() -> {
            NVGWrapper.rect(x, y, width, height);
            fillColor(col);
        }, DRAW_FILL);
        col.free();
    }

    public void rrect(float x, float y, float width, float height, float radius, Color color) {
        NVGColor col = nvgcolor(color);
        path(() -> {
            NVGWrapper.rrect(x, y, width, height, radius);
            fillColor(col);
        }, DRAW_FILL);
        col.free();
    }

    public void rrect(float x, float y, float width, float height, float rTL, float rTR, float rBL, float rBR, Color color) {
        NVGColor col = nvgcolor(color);
        path(() -> {
            NVGWrapper.rrect(x, y, width, height, rTL,rTR,rBL,rBR);
            fillColor(col);
        }, DRAW_FILL);
        col.free();
    }

    public void text(float x, float y, float size, String font, String string, Color color, float blur, Alignment alignment) {
        NVGColor col = nvgcolor(color);
        path(() -> {
            fontBlur(blur);
            fontFace(font);
            fontSize(size);
            textAlign(alignment.alignment);
            fillColor(col);
            NVGWrapper.text(string,x,y);
        }, -1);
        col.free();
    }
    public void text(float x, float y, float size, String font, String string, Color color, Alignment alignment) {
        text(x,y,size,font,string,color,0,alignment);
    }
    public void text(float x, float y, float size, String font, String string, Color color) {
        text(x,y,size,font,string,color,0,Alignment.LEFT_TOP);
    }

    public void image(float x, float y, float w, float h, String id, float alpha, float rad) {
        NVGPaint paint = NVGPaint.calloc();
        nvgImageSize(context, imagesmap.get(id), new int[]{(int)w},new int[]{(int)h});

        path(() -> {
            imagePattern(x,y,w,h,0,imagesmap.get(id), alpha, paint);
            NVGWrapper.rrect(x,y,w,h,rad);
            fillPaint(paint);
        }, DRAW_FILL);

        paint.free();
    }

    public void line(float sx, float sy, float ex, float ey, float w, Color c1, Color c2) {
        NVGColor nc1 = nvgcolor(c1);
        NVGColor nc2 = nvgcolor(c2);
        NVGPaint paint = NVGPaint.create();
        linearGradient(sx,sy,ex,ey,nc1,nc2,paint);
        path(sx,sy, ()->{
            strokeWidth(w);
            strokePaint(paint);
            lineTo(ex,ey);
        }, DRAW_STROKE);

        nc1.free();
        nc2.free();
    }
    public void line(float sx, float sy, float ex, float ey, float w, Color color) {
        NVGColor nc1 = nvgcolor(color);
        path(sx,sy, ()->{
            strokeWidth(w);
            strokeColor(nc1);
            lineTo(ex,ey);
        }, DRAW_STROKE);

        nc1.free();
    }

    public enum Alignment {
        LEFT_TOP(NVG_ALIGN_LEFT | NVG_ALIGN_TOP),
        CENTER_TOP(NVG_ALIGN_CENTER | NVG_ALIGN_TOP),
        RIGHT_TOP(NVG_ALIGN_RIGHT | NVG_ALIGN_TOP),

        LEFT_MIDDLE(NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE),
        CENTER_MIDDLE(NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE),
        RIGHT_MIDDLE(NVG_ALIGN_RIGHT | NVG_ALIGN_MIDDLE),

        LEFT_BOTTOM(NVG_ALIGN_LEFT | NVG_ALIGN_BOTTOM),
        CENTER_BOTTOM(NVG_ALIGN_CENTER | NVG_ALIGN_BOTTOM),
        RIGHT_BOTTOM(NVG_ALIGN_RIGHT | NVG_ALIGN_BOTTOM);

        @Getter
        private final int alignment;

        Alignment(int alignment) {
            this.alignment = alignment;
        }
    }
}
