package com.ooftf.master.widget.dialog.inteface;

import android.content.DialogInterface;

import java.util.List;

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
}
