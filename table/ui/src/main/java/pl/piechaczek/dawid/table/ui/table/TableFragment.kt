package pl.piechaczek.dawid.table.ui.table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.piechaczek.dawid.core.data.extension.subscribeTo
import pl.piechaczek.dawid.core.ui.navigation.MainNavigator
import pl.piechaczek.dawid.core.ui.adapter.BaseSimpleAdapter
import pl.piechaczek.dawid.core.ui.adapter.SimpleAdapterItem
import pl.piechaczek.dawid.core.ui.base.BaseFragment
import pl.piechaczek.dawid.core.ui.widget.OnSegmentChangeListener
import pl.piechaczek.dawid.table.ui.databinding.FragmentTableBinding
import pl.piechaczek.dawid.table.ui.databinding.ItemCurrencyBinding
import pl.piechaczek.dawid.table.ui.di.ComponentProvider
import pl.piechaczek.dawid.table.ui.model.Rate
import timber.log.Timber
import javax.inject.Inject

internal class TableFragment : BaseFragment<TableViewModel, FragmentTableBinding>(
    TableViewModel::class.java
), OnSegmentChangeListener {

    @Inject
    lateinit var mainNavigator: MainNavigator
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var adapter: BaseSimpleAdapter<ItemCurrencyBinding, SimpleAdapterItem<ItemCurrencyBinding>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEffectsAndState()
        initView()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun initView() {
        adapter = BaseSimpleAdapter()
        binding.currencies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.currencies.adapter = adapter

        val tableType = arguments?.getChar(TABLE_TYPE)
        tableType?.let {
            viewModel.onAction(TableViewAction.GetInfoForTable(it))
                .subscribeTo(compositeDisposable)
        }
    }

    private fun executeState(state: TableViewState) {

    }

    private fun executeEffects(effect: TableViewEffect) {
        when (effect) {
            is TableViewEffect.ShowTable -> showTable(effect.tableType, effect.rates)
            is TableViewEffect.ShowProgress -> showProgress()
            is TableViewEffect.HideProgress -> hideProgress()
            is TableViewEffect.ShowToast -> showToast(effect.message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun showTable(tableType: Char, rates: List<Rate>) {
        adapter.replace(rates.map {
            CurrencyTableItem(it) { mainNavigator.navigateToDetailsView(tableType, it.currencyName, it.code) }
        })
    }

    override fun onSegmentChange(previousItemIndex: Int, newItemIndex: Int) {
        viewModel.onAction(TableViewAction.SegmentChanged(newItemIndex))
            .subscribeTo(compositeDisposable)
    }

    private fun initEffectsAndState() {
        viewModel.observeEffects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = this::executeEffects, onError = Timber::e)
            .addTo(compositeDisposable)

        viewModel.observeState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = this::executeState, onError = Timber::e)
            .addTo(compositeDisposable)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTableBinding =
        FragmentTableBinding.inflate(inflater, container, false)

    override fun handleInjection() {
        ComponentProvider.getComponent().inject(this)
    }

    companion object {
        private const val TABLE_TYPE = "table_type"

        fun args(tableType: Char): Bundle = Bundle().apply {
            putChar(TABLE_TYPE, tableType)
        }
    }
}