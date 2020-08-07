package pl.piechaczek.dawid.core.ui.adapter

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface SimpleAdapterItem<TBinding : ViewBinding> {

    val type: Int

    fun bindView(binding: TBinding)

    fun createBinding(parent: ViewGroup): TBinding
}