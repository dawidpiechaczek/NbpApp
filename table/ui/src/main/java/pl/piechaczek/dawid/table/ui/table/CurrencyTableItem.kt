package pl.piechaczek.dawid.table.ui.table

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.piechaczek.dawid.core.ui.adapter.SimpleAdapterItem
import pl.piechaczek.dawid.table.ui.R
import pl.piechaczek.dawid.table.ui.databinding.ItemCurrencyBinding
import pl.piechaczek.dawid.table.ui.model.Rate

internal typealias OnRateItemClickListener = () -> Unit

data class CurrencyTableItem(
    val rate: Rate,
    val onClickListener: OnRateItemClickListener? = null
) : SimpleAdapterItem<ItemCurrencyBinding> {

    override val type: Int
        get() = R.layout.item_currency

    override fun bindView(binding: ItemCurrencyBinding) {

        binding.date.text = rate.date
        binding.title.text = rate.currencyName
        binding.rate.text = rate.rate.toString()
        binding.code.text = rate.code

        binding.root.setOnClickListener {
            onClickListener?.invoke()
        }
    }

    override fun createBinding(parent: ViewGroup): ItemCurrencyBinding {
        return ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
}