package com.ooftf.databinding.extensions;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
public class DataBindingAdapter {


    @BindingAdapter(value = "exUrl", requireAll = false)
    public static void setUrl(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView).load(url).into(imageView);
        }
    }

    @BindingAdapter({"exSrc"})
    public static void setImageViewResource(ImageView imageView, Drawable resource) {
        imageView.setImageDrawable(resource);
    }

    @BindingAdapter(value = "exBackgroundTintColor", requireAll = false)
    public static void setBackgroundTintColor(View view, int color) {
        if (color != 0 && view.getBackground() != null) {
            DrawableCompat.setTint(view.getBackground().mutate(), color);
        }

    }

    @BindingAdapter(value = "exMaxInt", requireAll = false)
    public static void setMaxInt(final TextView textView, final int max) {
        textView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                int anInt = getInt(s1);
                if (anInt > max) {
                    textView.setText(String.valueOf(max));
                }
            }
        });


    }

    static int getInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            Log.e("stringUtil", value + "--> Integer is error");
            return 0;
        }
    }
}
