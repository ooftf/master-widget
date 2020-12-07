package com.ooftf.master.widget.dialog.ui;

import android.app.Activity;

import com.ooftf.master.widget.dialog.delegate.ListDialogDelegate;
import com.ooftf.master.widget.dialog.inteface.DialogOnItemClickListener;
import com.ooftf.master.widget.dialog.inteface.ListDialogInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2018/10/17 0017
 */
public class ListDialog extends BottomDialog implements ListDialogInterface {
    ListDialogDelegate dialogDelegate;

    public ListDialog(@NotNull Activity activity) {
        super(activity);
        dialogDelegate = new ListDialogDelegate(this, activity);
    }

    @Override
    public ListDialogInterface setShowCancel(boolean showCancel) {
        return dialogDelegate.setShowCancel(showCancel);
    }

    @Override
    public ListDialogInterface setList(List<String> data) {
        return dialogDelegate.setList(data);
    }

    @Override
    public ListDialogInterface setOnItemClickListener(DialogOnItemClickListener listener) {
        return dialogDelegate.setOnItemClickListener(listener);
    }

    @Override
    public ListDialogInterface show_() {
        dialogDelegate.show_();
        return this;
    }
}
