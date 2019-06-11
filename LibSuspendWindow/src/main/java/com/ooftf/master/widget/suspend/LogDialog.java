package com.ooftf.master.widget.suspend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ooftf.master.widget.dialog.ui.BaseDialog;

import java.util.List;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/11 0011
 */
class LogDialog extends BaseDialog {
    private RecyclerView recyclerView;
    private List<String> data;

    public LogDialog(@NonNull Context context) {
        super(context, R.style.master_DialogTheme_Transparent);
        setContentView(R.layout.suspend_dialog_show_log);
        setWidthPercent(1f);
        setHeightPercent(1f);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter<TheViewHolder>() {
            @NonNull
            @Override
            public TheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                TheViewHolder theViewHolder = new TheViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.suspend_item_show_log, parent, false));
                theViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        CopyUtil.copy(v.getContext(), theViewHolder.textView.getText().toString());
                        Toast.makeText(Suspend.application, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                return theViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull TheViewHolder holder, int position) {
                holder.textView.setText(data.get(position));
            }

            @Override
            public int getItemCount() {
                if (data == null) {
                    return 0;
                }
                return data.size();
            }
        });
        //.setOnClickListener(v -> baseDialog.dismiss());
        /*findViewById(R.id.copy).setOnClickListener(v -> {
            CopyUtil.copy(v.getContext(), ((TextView) baseDialog.findViewById(R.id.text)).getText().toString());
            Toast.makeText(application, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
        });*/
        //((TextView) baseDialog.findViewById(R.id.text)).setText(TosStringUtils.linkedListToString(queue));
    }

    class TheViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TheViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    public LogDialog setData(List<String> data) {
        this.data = data;
        recyclerView.getAdapter().notifyDataSetChanged();
        return this;
    }
}
