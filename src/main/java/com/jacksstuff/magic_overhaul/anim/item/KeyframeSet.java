package com.jacksstuff.magic_overhaul.anim.item;

import com.jacksstuff.magic_overhaul.anim.item.keyframe_data.FPKeyframeData;

import java.security.Key;
import java.util.ArrayList;

public class KeyframeSet<T extends KeyframeData> {
    private Keyframe<T>[] frames;

    public KeyframeSet(Keyframe<T>... frames) {
        frames = sortKeyframes(frames);
        Keyframe<T>[] newFrames = new Keyframe[frames.length];
        for (int i = 0; i < newFrames.length; i++) {
            newFrames[i] = new Keyframe(frames[i].data, Keyframe.inverseLerp(frames[0].startTime, frames[frames.length - 1].startTime, frames[i].startTime));
        }

        this.frames = newFrames;
    }


    public static KeyframeSet of(Keyframe[] frames) {
        return new KeyframeSet(frames);
    }

    public static KeyframeSet create(Keyframe[] frames) {
        return new KeyframeSet(frames);
    }

    private Keyframe<T>[] sortKeyframes(Keyframe<T>[] in) {
        ArrayList<Keyframe<T>> list = new ArrayList<>();
        OUTER_LOOP : for (Keyframe<T> frame : in) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).startTime > frame.startTime) {
                    list.add(i, frame);
                    continue OUTER_LOOP;
                }
            }
            list.add(frame);
        }

        return list.toArray(new Keyframe[]{});
    }


    public Keyframe[] get() {
        return sortKeyframes(frames);
    }
}
