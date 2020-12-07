package com.ooftf.master.widget.dialog.delegate

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.master.widget.dialog.R
import com.ooftf.master.widget.dialog.inteface.DialogOnItemClickListener
import com.ooftf.master.widget.dialog.inteface.ListDialogInterface
import com.ooftf.master.widget.dialog.ui.BaseDialog

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/6 0006
 */
class ListDialogDelegate(var dialog: BaseDialog, var activity: Activity) : ListDialogInterface {
    var cancel: TextView
    var recyclerView: RecyclerView
    var cancelGroup: View
    var adapter: TheAdapter
    var listener: DialogOnItemClickListener? = null

    override fun setShowCancel(showCancel: Boolean): ListDialogInterface {
        if (showCancel) {
            cancelGroup.visibility = View.VISIBLE
        } else {
            cancelGroup.visibility = View.GONE
        }
        return this
    }

    override fun setList(data: List<String>): ListDialogInterface {
        adapter.data = data
        adapter.notifyDataSetChanged()
        return this
    }

    override fun setOnItemClickListener(listener: DialogOnItemClickListener): ListDialogInterface {
        this.listener = listener
        return this
    }

    override fun setItemViewAdapter(adapter: (TextView, Int) -> Unit): ListDialogInterface {
        viewAdapter = adapter
        return this
    }

    inner class TheAdapter : RecyclerView.Adapter<TheViewHolder>() {
        var data: List<String>? = null

        /*@NonNull
        @Override
        public BaseViewHolder<TextView> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new BaseViewHolder<>(R.layout.master_item_list_selector_dialog, viewGroup);
        }*/
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
            val holder: TheViewHolder
            val view = LayoutInflater.from(parent.context).inflate(R.layout.master_item_list_selector_dialog, parent, false)
            holder = TheViewHolder(view)
            view.setOnClickListener { v: View? ->
                data?.getOrNull(holder.adapterPosition)?.let { item ->
                    listener?.onItemClick(dialog, holder.adapterPosition, item)
                }

            }
            return holder
        }

        override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
            data?.getOrNull(position)?.let {
                holder.textView.text = it
                viewAdapter?.invoke(holder.textView, position)
            }

        }

        override fun getItemCount(): Int {
            return data?.size ?: 0
        }
    }

    var viewAdapter: ((textView: TextView, position: Int) -> Unit)? = null

    class TheViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView as TextView

    }

    init {
        dialog.setContentView(R.layout.master_dialog_list_selector)
        cancel = dialog.findViewById(R.id.cancel)
        recyclerView = dialog.findViewById(R.id.recycler_view)
        cancelGroup = dialog.findViewById(R.id.cancel_group)
        dialog.setGravity(Gravity.BOTTOM)
        cancel.setOnClickListener { v: View? -> dialog.dismiss() }
        adapter = TheAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }
}