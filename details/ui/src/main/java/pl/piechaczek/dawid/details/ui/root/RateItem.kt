package pl.piechaczek.dawid.details.ui.root

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.piechaczek.dawid.core.ui.adapter.SimpleAdapterItem
import pl.piechaczek.dawid.details.ui.R
import pl.piechaczek.dawid.details.ui.databinding.ItemRateBinding
import pl.piechaczek.dawid.details.ui.model.Rate

data class RateItem(
    val rate: Rate
) : SimpleAdapterItem<ItemRateBinding> {

    override val type: Int
        get() = R.layout.item_rate

    override fun bindView(binding: ItemRateBinding) {
        binding.serialCode.text = rate.serialCode
        binding.rate.text = rate.rate.toString()
        binding.date.text = rate.date
    }

    override fun createBinding(parent: ViewGroup): ItemRateBinding {
        return ItemRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
}