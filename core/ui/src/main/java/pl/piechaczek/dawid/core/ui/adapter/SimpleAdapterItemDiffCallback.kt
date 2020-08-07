package pl.piechaczek.dawid.core.ui.adapter

interface SimpleAdapterItemDiffCallback<TAdapterItem : SimpleAdapterItem<*>> {

    fun areItemsTheSame(oldItem: TAdapterItem, newItem: TAdapterItem): Boolean

    fun areContentTheSame(oldItem: TAdapterItem, newItem: TAdapterItem): Boolean
}