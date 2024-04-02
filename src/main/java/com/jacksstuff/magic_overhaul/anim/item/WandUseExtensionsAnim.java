package com.jacksstuff.magic_overhaul.anim.item;

import com.jacksstuff.magic_overhaul.anim.item.keyframe_data.FPKeyframeData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class WandUseExtensionsAnim extends KeyframedItemAnim {

    private static final HumanoidModel.ArmPose USE_POSE = HumanoidModel.ArmPose.create("USE", false, WandUseExtensionsAnim::useAnimation);;
    private static final HumanoidModel.ArmPose HOLD_POSE = HumanoidModel.ArmPose.EMPTY;


    private Supplier<Float> totalTimeSup;

    private static final float useAngle = 60;

    public WandUseExtensionsAnim(Supplier<Float> totalTimeSup) {
        this.totalTimeSup = totalTimeSup;
        this.setRepeatMode(RepeatMode.STOP);
    }

    @Override
    public KeyframeSet getFPUseFrames() {
        return new KeyframeSet (
                new Keyframe(new FPKeyframeData(0, 0, 0, 0, 0, 0, 0, 0, 0).relativeToDefault(), 0),
                new Keyframe(new FPKeyframeData(0, 0, -0.5, -(useAngle), 0, 0, 0, 0, 0).relativeToDefault(), 1)
        );
    }


    @Override
    public KeyframeSet getFPStopFrames() {
        int turnMagnitude = 10;
        return new KeyframeSet(
                new Keyframe(new FPKeyframeData(0, 0, -0.5, -(useAngle), 0, 0, 0, 0, 0).relativeToDefault(), 0),
                new Keyframe(new FPKeyframeData(0, 0, -0.5, -(useAngle + turnMagnitude), 0, turnMagnitude, 0, 0, 0).relativeToDefault(), 1),
                new Keyframe(new FPKeyframeData(0, 0, -0.5, -(useAngle + turnMagnitude * 2), 0, 0, 0, 0, 0).relativeToDefault(), 2),
                new Keyframe(new FPKeyframeData(0, 0, -0.5, -(useAngle + turnMagnitude), 0,  -(turnMagnitude), 0, 0, 0).relativeToDefault(), 3),
                new Keyframe(new FPKeyframeData(0, 0, -0.5, -(useAngle), 0, 0, 0, 0, 0).relativeToDefault(), 4)
        );
    }

    @Override
    public Keyframe getFPDefaultFrame() {
        return new Keyframe(new FPKeyframeData(0, 0, 0).relativeToDefault(), 0);
    }

    @Override
    public float getUseTotalTime() {
        //System.out.println("total time: " + totalTimeSup.get());
        return totalTimeSup.get();
    }


    @Override
    public float getStopTotalTime() {
        return 100;
    }



    //Third person
    @Override
    public HumanoidModel.ArmPose getHoldArmPose() {
        return HOLD_POSE;
    }

    @Override
    public HumanoidModel.ArmPose getUseArmPose() {
        System.out.println("use anim");
        return USE_POSE;
    }

    private static void useAnimation(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
        float angle = -60;
        if (arm == HumanoidArm.RIGHT) {
            model.rightArm.xRot = (float) Math.toRadians(angle);
        } else {
            model.leftArm.xRot = (float) Math.toRadians(angle);
        }
    }

    @Override
    public Function<Float, Float> getSmoothingFunction() {
        // Ease in out cubic function:
        // https://easings.net/#easeInOutCubic
        //
        return value -> Float.valueOf((float)(value < 0.5 ? 2 * value * value : 1 - Math.pow(-2 * value + 2, 2) / 2));
    }



}
