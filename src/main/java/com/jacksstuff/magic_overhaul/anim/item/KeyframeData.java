package com.jacksstuff.magic_overhaul.anim.item;

import java.util.function.Function;

public abstract class KeyframeData {
    public abstract <T extends KeyframeData> Keyframe<T> interpolate(float currentTime, float totalTime, Keyframe<T> thisKeyframe, Keyframe<T> otherKeyframe, Function<Float, Float> smoothingFunction);
}
