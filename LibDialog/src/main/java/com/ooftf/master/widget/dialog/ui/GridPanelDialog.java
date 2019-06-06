package com.ooftf.master.widget.dialog.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ooftf.master.widget.dialog.R;
import com.ooftf.service.widget.dialog.BottomDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GridPanelDialog<T> extends BottomDialog {
    private TheAdapter adapter;
    private DialogItemAdapter<T> itemAdapter;
    private RecyclerView recyclerView;

    public GridPanelDialog(@NotNull Activity activity) {
        super(activity);
        getLayoutInflater().inflate(R.layout.master_dialog_grid_panel, (ViewGroup) getWindow().getDecorView());
        recyclerView = findViewById(R.id.recycler_view);
        setWidthPercent(1);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapter = new TheAdapter();
        recyclerView.setAdapter(adapter);
    }

    public GridPanelDialog<T> setItemAdapter(DialogItemAdapter<T> adapter) {
        this.itemAdapter = adapter;
        return this;
    }

    public GridPanelDialog<T> setList(List<T> data) {
        adapter.data = data;
        adapter.notifyDataSetChanged();
        return this;
    }

    private class TheAdapter extends RecyclerView.Adapter<TheViewHolder> {
        List<T> data;

        @NonNull
        @Override
        public TheViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new TheViewHolder(R.layout.master_item_dialog_grid_panel, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull TheViewHolder holder, int position) {
            if (itemAdapter != null) {
                itemAdapter.onBindView(data.get(position), position, holder.imageView, holder.textView, holder.itemView, GridPanelDialog.this);
            }
        }

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            }
            return data.size();
        }

    }

    private static class TheViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public TheViewHolder(int layoutId, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
        }

    }

    public interface DialogItemAdapter<T> {
        void onBindView(T item, int position, ImageView icon, TextView textView, View itemRoot, GridPanelDialog dialog);
    }

}
