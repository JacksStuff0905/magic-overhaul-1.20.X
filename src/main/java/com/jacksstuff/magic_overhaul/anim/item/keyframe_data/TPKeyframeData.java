package com.jacksstuff.magic_overhaul.anim.item.keyframe_data;

import com.jacksstuff.magic_overhaul.anim.item.Keyframe;
import com.jacksstuff.magic_overhaul.anim.item.KeyframeData;
import com.mojang.datafixers.types.Func;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;



public class TPKeyframeData extends KeyframeData {

    public HumanoidModel.ArmPose pose;

    private ArrayList<HumanoidModel.ArmPose> interpolated = new ArrayList<>();

    public TPKeyframeData(HumanoidModel.ArmPose pose) {
        this.pose = pose;

    }

    @Override
    public <T extends KeyframeData> Keyframe<T> interpolate(float currentTime, float totalTime, Keyframe<T> thisKeyframe, Keyframe<T> otherKeyframe, Function<Float, Float> smoothingFunction) {
        if (otherKeyframe.data instanceof TPKeyframeData otherData) {
            return new Keyframe(new TPKeyframeData(this.interpolatePose(currentTime, totalTime, (Keyframe<TPKeyframeData>) thisKeyframe, (Keyframe<TPKeyframeData>) otherKeyframe, smoothingFunction)), 0);
        } else {
            try {
                throw new Exception("Invalid KeyframeData type passed to interpolate() function, passed: " + otherKeyframe.data.getClass() + ", expected: " + this.getClass());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    private HumanoidModel.ArmPose interpolatePose(float currentTime, float totalTime, Keyframe<TPKeyframeData> thisKeyframe, Keyframe<TPKeyframeData> otherKeyframe, Function<Float, Float> smoothingFunction) {

        for (HumanoidModel.ArmPose pose : interpolated) {
            if (pose.name().equals("Interpolated: " + currentTime / totalTime + ", " + otherKeyframe)) {
                return null;
            }
        }

        HumanoidModel.ArmPose newPos = HumanoidModel.ArmPose.create("Interpolated: " + currentTime / totalTime + ", " + otherKeyframe,
                thisKeyframe.data.pose.isTwoHanded(),
                (model, entity, arm) -> {

                }
        );


        return null;
    }


    private ModelPart interpolateModelPart(ModelPart a, ModelPart b, Keyframe<TPKeyframeData> thisKeyframe, Keyframe<TPKeyframeData> otherKeyframe, float currentTime, float totalTime, Function<Float, Float> smoothingFunction) {
        ModelPart part = a;

        //Lerp all parameters
        part.xScale = lerpParameter(a.xScale, b.xScale, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);
        part.yScale = lerpParameter(a.yScale, b.yScale, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);
        part.zScale = lerpParameter(a.zScale, b.zScale, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);

        part.xRot = lerpParameter(a.xRot, b.xRot, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);
        part.yRot = lerpParameter(a.yRot, b.yRot, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);
        part.zRot = lerpParameter(a.zRot, b.zRot, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);

        part.x = lerpParameter(a.x, b.x, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);
        part.y = lerpParameter(a.y, b.y, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);
        part.z = lerpParameter(a.z, b.z, thisKeyframe.startTime, otherKeyframe.startTime, currentTime / totalTime, smoothingFunction);

        return part;
    }


    private float lerpParameter(float parA, float parB, double thisTime, double otherTime, float weight, Function<Float, Float> smoothingFunction) {
        return Mth.lerp((float) smoothingFunction.apply((float) Keyframe.inverseLerp(thisTime, otherTime, weight)),
                parA, parB);
    }
}
