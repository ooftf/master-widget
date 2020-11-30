package com.ooftf.master.widget.dialog.delegate;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ooftf.master.widget.dialog.R;
import com.ooftf.master.widget.dialog.inteface.DialogOnItemClickListener;
import com.ooftf.master.widget.dialog.inteface.ListDialogInterface;
import com.ooftf.master.widget.dialog.ui.BaseDialog;

import java.util.List;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/6 0006
 */
public class ListDialogDelegate implements ListDialogInterface {
    BaseDialog dialog;
    Activity activity;
    TextView cancel;
    RecyclerView recyclerView;
    View cancelGroup;
    TheAdapter adapter;
    DialogOnItemClickListener listener;

    public ListDialogDelegate(BaseDialog dialog, Activity activity) {
        this.dialog = dialog;
        this.activity = activity;
        dialog.setContentView(R.layout.master_dialog_list_selector);
        cancel = dialog.findViewById(R.id.cancel);
        recyclerView = dialog.findViewById(R.id.recycler_view);
        cancelGroup = dialog.findViewById(R.id.cancel_group);
        dialog.setGravity(Gravity.BOTTOM);
        cancel.setOnClickListener(v -> dismiss());
        adapter = new TheAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void cancel() {
        dialog.cancel();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public ListDialogInterface setShowCancel(boolean showCancel) {
        if (showCancel) {
            cancelGroup.setVisibility(View.VISIBLE);
        } else {
            cancelGroup.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public ListDialogInterface setList(List<String> data) {
        adapter.data = data;
        adapter.notifyDataSetChanged();
        return this;
    }

    @Override
    public ListDialogInterface setOnItemClickListener(DialogOnItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ListDialogInterface show_() {
        dialog.show();
        return this;
    }

    private class TheAdapter extends RecyclerView.Adapter<TheViewHolder> {

        List<String> data;

        /*@NonNull
        @Override
        public BaseViewHolder<TextView> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new BaseViewHolder<>(R.layout.master_item_list_selector_dialog, viewGroup);
        }*/

        @NonNull
        @Override
        public TheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TheViewHolder holder;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_item_list_selector_dialog, parent, false);
            holder = new TheViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(ListDialogDelegate.this, holder.position, data.get(holder.position));
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull TheViewHolder holder, int position) {
            holder.textView.setText(data.get(position));
            holder.position = position;
        }

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            } else {
                return data.size();
            }
        }


    }


    private static class TheViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        int position;

        public TheViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
