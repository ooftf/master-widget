package com.ooftf.master.widget.dialog.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

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
    public static Bitmap rsBlur(Context context, Bitmap source, double radius) {
        Bitmap inputBmp;
        if(radius >25){
            double scale = radius/25.0;
            radius = 25;
            int scaleWith = Math.max((int)(source.getWidth()/scale),1);
            int scaleHeight = Math.max((int)(source.getHeight()/scale),1);
            inputBmp = Bitmap.createScaledBitmap(source,scaleWith,scaleHeight,false);
        }else {
            inputBmp = source;
        }

        //(1)
        RenderScript renderScript = RenderScript.create(context);

        // Allocate memory for Renderscript to work with
        //(2)
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        //(3)
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        //(4)
        scriptIntrinsicBlur.setInput(input);
        //(5)
        // Set the blur radius
        scriptIntrinsicBlur.setRadius((float) radius);
        //(6)
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        //(7)
        // Copy the output to the blurred bitmap
        Bitmap outBitmap;
        if(inputBmp == source){
            outBitmap = Bitmap.createBitmap(inputBmp.getWidth(),inputBmp.getHeight(),inputBmp.getConfig());
        }else{
            outBitmap = inputBmp;
        }

        output.copyTo(outBitmap);

        //(8)
        renderScript.destroy();
        return outBitmap;
    }
}
