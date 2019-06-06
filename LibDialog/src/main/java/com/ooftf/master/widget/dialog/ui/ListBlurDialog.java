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
public class ListBlurDialog extends BlurDialog implements ListDialogInterface {
    ListDialogDelegate delegate;

    public ListBlurDialog(@NotNull Activity activity) {
        super(activity);
        delegate = new ListDialogDelegate(this, activity);
    }


    @Override
    public ListDialogInterface setShowCancel(boolean showCancel) {
        return delegate.setShowCancel(showCancel);
    }

    @Override
    public ListDialogInterface setList(List<String> data) {
        return delegate.setList(data);
    }

    @Override
    public ListDialogInterface setOnItemClickListener(DialogOnItemClickListener listener) {
        return delegate.setOnItemClickListener(listener);
    }
}
