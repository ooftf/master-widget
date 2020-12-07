package com.ooftf.master.widget.dialog.inteface;

import android.content.DialogInterface;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/6 0006
 */
public interface ListDialogInterface{
    ListDialogInterface setShowCancel(boolean showCancel);

    /**
     * @param data
     * @return
     */
    ListDialogInterface setList(List<String> data);

    /**
     * @param listener
     * @return
     */
    ListDialogInterface setOnItemClickListener(DialogOnItemClickListener listener);

    ListDialogInterface setItemViewAdapter(@NotNull Function2<? super TextView, ? super Integer, Unit> adapter);
}
