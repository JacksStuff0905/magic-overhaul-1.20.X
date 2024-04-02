package com.jacksstuff.magic_overhaul.anim.item;

import net.minecraft.world.phys.Vec3;
import org.joml.AxisAngle4f;
import org.joml.QuaterniondInterpolator;
import org.joml.Quaternionf;

import java.util.function.Function;
import java.util.function.Supplier;

public class Keyframe<T extends KeyframeData> {

    public T data;

    public double startTime;

    public Keyframe(T data, double startTime) {
        this.data = data;
        this.startTime = startTime;
    }


    public static double inverseLerp(double a, double b, double result) {
        return (result - a) / (b - a);
    }







}
