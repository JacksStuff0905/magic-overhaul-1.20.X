package com.jacksstuff.magic_overhaul.anim.item.keyframe_data;

import com.jacksstuff.magic_overhaul.anim.item.Keyframe;
import com.jacksstuff.magic_overhaul.anim.item.KeyframeData;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.function.Function;

public class FPKeyframeData extends KeyframeData {
    public Vec3 position;
    public Quaternionf rotation = null;

    public Vec3 rotatePos;

    public FPKeyframeData(Vec3 position) {
        this.position = position;
    }

    public FPKeyframeData(Vec3 position, Quaternionf rotation, Vec3 rotatePos) {
        this.position = position;
        this.rotation = rotation;
        this.rotatePos = rotatePos;
    }


    public FPKeyframeData(double x, double y, double z) {
        this.position = new Vec3(x, y, z);
    }

    public FPKeyframeData(double x, double y, double z, double qx, double qy, double qz, double rx, double ry, double rz) {
        this.position = new Vec3(x, y, z);
        this.rotation = eulerToQuaternion(qx, qy, qz);
        this.rotatePos = new Vec3(rx , ry, rz);
    }


    public FPKeyframeData relativeToDefault() {
        this.position = getDefault().add(position);
        return this;
    }

    public static Vec3 getDefault() {
        return new Vec3(0.56F, -0.52F, -0.72F);
    }




    private Vec3 lerpPos(Vec3 a, Vec3 b, double value) {
        return new Vec3(a.x + (b.x - a.x) * value, a.y + (b.y - a.y) * value, a.z + (b.z - a.z) * value);
    }

    private Quaternionf lerpRot(Quaternionf a, Quaternionf b, double value) {
        return a.slerp(b, (float)value);
    }

    @Override
    public <T extends KeyframeData> Keyframe<T> interpolate(float currentTime, float totalTime, Keyframe<T> thisKeyframe, Keyframe<T> otherKeyframe, Function<Float, Float> smoothingFunction) {
        if (otherKeyframe.data instanceof FPKeyframeData otherData) {
            if (this.rotation == null || otherData.rotation == null) {
                return new Keyframe(new FPKeyframeData(this.interpolatePos(currentTime, totalTime, (Keyframe<FPKeyframeData>) thisKeyframe, (Keyframe<FPKeyframeData>) otherKeyframe, smoothingFunction)), 0);
            }
            return new Keyframe(new FPKeyframeData(this.interpolatePos(currentTime, totalTime, (Keyframe<FPKeyframeData>) thisKeyframe, (Keyframe<FPKeyframeData>) otherKeyframe, smoothingFunction), this.interpolateRot(currentTime, totalTime, (Keyframe<FPKeyframeData>) thisKeyframe, (Keyframe<FPKeyframeData>) otherKeyframe, smoothingFunction), this.interpolateRotAround(currentTime, totalTime, (Keyframe<FPKeyframeData>) thisKeyframe, (Keyframe<FPKeyframeData>) otherKeyframe, smoothingFunction)), 0);
        } else {
            try {
                throw new Exception("Invalid KeyframeData type passed to interpolate() function, passed: " + otherKeyframe.data.getClass() + ", expected: " + this.getClass());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private Vec3 interpolatePos(float currentTime, float totalTime, Keyframe<FPKeyframeData> thisKeyframe, Keyframe<FPKeyframeData> otherKeyframe, Function<Float, Float> smoothingFunction) {
        return lerpPos(thisKeyframe.data.position, otherKeyframe.data.position, smoothingFunction.apply((float)Keyframe.inverseLerp(thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime)));
    }

    private Quaternionf interpolateRot(float currentTime, float totalTime, Keyframe<FPKeyframeData> thisKeyframe, Keyframe<FPKeyframeData> otherKeyframe, Function<Float, Float> smoothingFunction) {
        return lerpRot(thisKeyframe.data.rotation, otherKeyframe.data.rotation, smoothingFunction.apply((float)Keyframe.inverseLerp(thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime)));
    }

    private Vec3 interpolateRotAround(float currentTime, float totalTime, Keyframe<FPKeyframeData> thisKeyframe, Keyframe<FPKeyframeData> otherKeyframe, Function<Float, Float> smoothingFunction) {
        return lerpPos(thisKeyframe.data.rotatePos, otherKeyframe.data.rotatePos, smoothingFunction.apply((float)Keyframe.inverseLerp(thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime)));
    }



    public Quaternionf eulerToQuaternion(double xin, double yin, double zin) {
        double x = Math.toRadians(xin);
        double y = Math.toRadians(yin);
        double z = Math.toRadians(zin);
        double c1 = Math.cos( x / 2 );
        double c2 = Math.cos( y / 2 );
        double c3 = Math.cos( z / 2 );

        double s1 = Math.sin( x / 2 );
        double s2 = Math.sin( y / 2 );
        double s3 = Math.sin( z / 2 );

        double qx = s1 * c2 * c3 + c1 * s2 * s3;
        double qy = c1 * s2 * c3 - s1 * c2 * s3;
        double qz = c1 * c2 * s3 + s1 * s2 * c3;
        double qw = c1 * c2 * c3 - s1 * s2 * s3;

        return new Quaternionf(qx, qy, qz, qw);
    }


}
