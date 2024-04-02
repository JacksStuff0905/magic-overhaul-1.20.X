package com.jacksstuff.magic_overhaul.anim.item;

import com.jacksstuff.magic_overhaul.anim.item.keyframe_data.FPKeyframeData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.data.PackOutput;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class KeyframedItemAnim implements IClientItemExtensions {

    private int lap = 0;
    private float currentTime = 0;

    private Boolean overrideHand = null;
    private RepeatMode repeatMode = RepeatMode.RESTART;
    private AnimState animState = AnimState.HOLD;

    public enum RepeatMode {
        RESTART,
        INVERT,
        STOP
    }

    public enum AnimState {
        USING,
        HOLD,
        STOP
    }

    public KeyframedItemAnim() {
    }


    private Keyframe getCurrentPosition(Keyframe[] f) {
        for (int i = 0; i < f.length; i++) {
            if (f[i].startTime >= currentTime / getTotalTime()) {
                if (f.length == 1) {
                    return f[0];
                }
                if (i == 0) {
                    return f[0].data.interpolate(currentTime, getTotalTime(), f[1], f[0], getSmoothingFunction());
                }
                return f[i - 1].data.interpolate(currentTime, getTotalTime(), f[i], f[i - 1], getSmoothingFunction());
            }
        }
        if (f.length > 0)
            return f[0];
        return null;
    }


    //First person
    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

        if (player.getUseItem() == itemInHand && ((animState.equals(AnimState.HOLD) && player.isUsingItem()) || (animState.equals(AnimState.USING) && !player.isUsingItem()))) {
            currentTime = 0;
            animState = player.isUsingItem() ? AnimState.USING : AnimState.HOLD;
        }
        if (animState != AnimState.STOP || !player.isUsingItem())
            animState = player.isUsingItem() ? AnimState.USING : AnimState.HOLD;


        currentTime += partialTick;
        boolean isNextLap = currentTime > getTotalTime();
        int offset = arm == HumanoidArm.RIGHT ? 1 : -1;
        if (overrideHand != null) {
            if (overrideHand.equals(Boolean.TRUE)) {
                offset = 1;
            } else {
                offset = -1;
            }
        }
        Keyframe<FPKeyframeData> current = null;
        if (player.getUseItem() == itemInHand && player.isUsingItem()) {
            current = selectFP(
                    () -> getCurrentPosition(getFPUseFrames().get()),
                    () -> getCurrentPosition(lap % 2 == 0 ? getFPUseFrames().get() : reverse(getFPUseFrames().get())),
                    () -> getCurrentPosition(getFPStopFrames().get()),
                    getCurrentPosition(getFPUseFrames().get()),
                    isNextLap
            );
        } else {
            current = selectFP(
                    () -> getCurrentPosition(getFPHoldFrames().get()),
                    () -> getCurrentPosition(lap % 2 == 0 ? getFPHoldFrames().get() : reverse(getFPHoldFrames().get())),
                    () -> getCurrentPosition(getFPHoldFrames().get()),
                    getCurrentPosition(getFPHoldFrames().get()),
                    isNextLap
            );
        }


        if (current == null) {
            currentTime %= getTotalTime();
            return true;
        }
        poseStack.translate(current.data.position.x * offset, current.data.position.y, current.data.position.z);

        if (current.data.rotation != null) {
            poseStack.rotateAround(current.data.rotation, (float)current.data.rotatePos.x * offset, (float)current.data.rotatePos.y, (float)current.data.rotatePos.z);
        }

        if (isNextLap) {
            lap++;
        }
        currentTime %= getTotalTime();

        return true;
    }

    private Keyframe<FPKeyframeData> selectFP(Supplier<Keyframe> caseRestart, Supplier<Keyframe> caseInvert, Supplier<Keyframe> caseStop, Keyframe defaultKeyframe, boolean isNextLap) {
        if (isNextLap) {
            switch (repeatMode) {
                case RESTART -> {
                    return caseRestart.get();
                }
                case INVERT -> {
                    return caseInvert.get();
                }
                case STOP -> {
                    currentTime = 0;
                    animState = AnimState.STOP;
                }
            }
        }

        if (animState.equals(AnimState.STOP)) {
            return caseStop.get();
        }

        return defaultKeyframe;
    }

    public KeyframeSet getFPUseFrames() {
        return new KeyframeSet(getFPDefaultFrame());
    }
    public KeyframeSet getFPStopFrames() { return new KeyframeSet(getFPUseFrames().get()[getFPUseFrames().get().length - 1]);}
    public KeyframeSet getFPHoldFrames() { return new KeyframeSet(getFPDefaultFrame());}
    public abstract Keyframe getFPDefaultFrame();

    //Third person
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                return getUseArmPose();
            }
        }
        return getHoldArmPose();
    }

    public abstract HumanoidModel.ArmPose getHoldArmPose();
    public abstract HumanoidModel.ArmPose getUseArmPose();

    //Values
    private float getTotalTime() {
        return switch (animState) {
            case HOLD -> getHoldTotalTime() * 2;
            case USING -> getUseTotalTime() * 2;
            case STOP -> getStopTotalTime() * 2;
        };
    }


    public float getUseTotalTime() {
        return 1;
    }
    public float getStopTotalTime() {
        return getUseTotalTime();
    }
    public float getHoldTotalTime() {
        return getUseTotalTime();
    }

    public Function<Float, Float> getSmoothingFunction() {
        return value -> value;
    }



    public KeyframedItemAnim setOverrideHand(boolean overrideHand) {
        this.overrideHand = Boolean.valueOf(overrideHand);
        return this;
    }
    public KeyframedItemAnim setRepeatMode(RepeatMode repeatMode) {
        this.repeatMode = repeatMode;
        return this;
    }




    //Helper
    static <T> T[] reverse(T[] array) {
        int i, k;
        int n = array.length;
        T t;
        for (i = 0; i < n / 2; i++) {
            t = array[i];
            array[i] = array[n - i - 1];
            array[n - i - 1] = t;
        }

        return array;
    }
}
