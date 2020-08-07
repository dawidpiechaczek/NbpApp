package pl.piechaczek.dawid.core.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseSimpleAdapter<TItemBinding : ViewBinding, TItem : SimpleAdapterItem<TItemBinding>>(
    data: List<TItem> = emptyList()
) : RecyclerView.Adapter<BaseSimpleAdapter.SimpleViewHolder<TItemBinding>>() {

    protected val data: ArrayList<TItem> = ArrayList(data)

    override fun getItemViewType(position: Int): Int = data[position].type

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SimpleViewHolder<TItemBinding>, position: Int) {
        data[position].bindView(holder.viewBinding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<TItemBinding> {
        return SimpleViewHolder(
            data[0].createBinding(parent)
        )
    }

    fun replace(items: List<TItem>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun replace(items: List<TItem>, callback: SimpleAdapterItemDiffCallback<TItem>) {
        val calculatedDiff = DiffUtil.calculateDiff(SimpleAdapterItemDiff(data, items, callback))
        data.clear()
        data.addAll(items)
        calculatedDiff.dispatchUpdatesTo(this)
    }

    fun find(predicate: (TItem) -> Boolean): TItem? {
        return data.find(predicate)
    }

    fun addItem(item: TItem, position: Int = -1) {
        if (position < 0) {
            data.add(item)
            notifyItemInserted(data.lastIndex)
        } else {
            data.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun removeItem(item: TItem, performFiding: Boolean = false) {
        if (performFiding) {
            val itemToRemove = data.find { it == item }
            itemToRemove?.let {
                removeItemInternally(item)
            }
        } else {
            removeItemInternally(item)
        }
    }

    private fun removeItemInternally(item: TItem) {
        val itemPosition = data.indexOf(item)
        data.remove(item)
        notifyItemRemoved(itemPosition)
    }

    class SimpleViewHolder<TBinding : ViewBinding>(val viewBinding: TBinding) : RecyclerView.ViewHolder(viewBinding.root)

    private inner class SimpleAdapterItemDiff(
        private val oldList: List<TItem>,
        private val newList: List<TItem>,
        private val callback: SimpleAdapterItemDiffCallback<TItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return callback.areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return callback.areContentTheSame(oldItem, newItem)
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}