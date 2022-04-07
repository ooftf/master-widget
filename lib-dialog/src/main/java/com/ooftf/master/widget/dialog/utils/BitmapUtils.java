package com.ooftf.master.widget.dialog.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.Nullable;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/6 0006
 */
public class BitmapUtils {
    /**
     * 高斯模糊
     *
     * @param context
     * @param source
     * @param radius
     * @return
     */
    public static @Nullable Bitmap rsBlur(Context context, Bitmap source, double radius) {
        Bitmap inputBmp;
        if (radius > 25) {
            double scale = radius / 25;
            radius = 25;
            int scaleWith = Math.max((int) (source.getWidth() / scale), 1);
            int scaleHeight = Math.max((int) (source.getHeight() / scale), 1);
            inputBmp = Bitmap.createScaledBitmap(source, scaleWith, scaleHeight, false);
        } else {
            inputBmp = source;
        }
        Bitmap outBitmap = null;
        Allocation input = null;
        Allocation output = null;
        RenderScript renderScript = null;
        ScriptIntrinsicBlur scriptIntrinsicBlur = null;
        try {
            renderScript = RenderScript.create(context);
            input = Allocation.createFromBitmap(renderScript, inputBmp);
            output = Allocation.createTyped(renderScript, input.getType());
            scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            scriptIntrinsicBlur.setInput(input);
            scriptIntrinsicBlur.setRadius((float) radius);
            scriptIntrinsicBlur.forEach(output);
            if (inputBmp == source) {
                outBitmap = Bitmap.createBitmap(inputBmp.getWidth(), inputBmp.getHeight(), inputBmp.getConfig());
            } else {
                outBitmap = inputBmp;
            }

            output.copyTo(outBitmap);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (renderScript != null) {
                renderScript.destroy();
            }
            if (output != null) {
                output.destroy();
            }
            if (input != null) {
                input.destroy();
            }
            if (scriptIntrinsicBlur != null) {
                scriptIntrinsicBlur.destroy();
            }
        }
        return outBitmap;
    }
}
