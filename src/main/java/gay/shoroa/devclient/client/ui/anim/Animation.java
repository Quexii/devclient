package gay.shoroa.devclient.client.ui.anim;

import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class Animation {
    private float toSmooth = 0, original = 0, speed = 0;
    public Animation(Supplier<Float> originalSupplier, Supplier<Float> speedSupplier) {
        this.original = originalSupplier.get();
        this.speed = speedSupplier.get();
    }

    public float get() {
        float delta = 1f/Minecraft.getDebugFPS();
        float diff = (toSmooth < original) ? original - toSmooth : toSmooth - original;
        if(toSmooth > original) toSmooth -= delta * speed * diff;
        if(toSmooth < original) toSmooth += delta * speed * diff;
        return toSmooth;
    }

    public void setOriginal(float original) {
        this.original = original;
    }
}
