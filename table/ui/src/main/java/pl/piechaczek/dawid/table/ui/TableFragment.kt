package pl.piechaczek.dawid.table.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.piechaczek.dawid.core.data.extension.subscribeTo
import pl.piechaczek.dawid.core.ui.base.BaseFragment
import pl.piechaczek.dawid.core.ui.widget.OnSegmentChangeListener
import pl.piechaczek.dawid.core.ui.widget.SegmentedButtonGroup
import pl.piechaczek.dawid.table.ui.databinding.FragmentTableBinding
import pl.piechaczek.dawid.table.ui.di.ComponentProvider

class TableFragment : BaseFragment<TableViewModel, FragmentTableBinding>(TableViewModel::class.java), OnSegmentChangeListener {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var group: SegmentedButtonGroup? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSegmentedButtonGroup()

        viewModel.observeEffects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = this::executeEffects)
            .addTo(compositeDisposable)

        viewModel.observeState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = this::executeState)
            .addTo(compositeDisposable)

        viewModel.onAction(TableViewAction.ShowToast)
            .subscribeTo(compositeDisposable)
    }


    private fun executeState(state: TableViewState) {
        group?.setCurrentItemIndex(state.selectedSegment)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun initSegmentedButtonGroup() {
        group = SegmentedButtonGroup(
            listOf(
                SegmentedButtonGroup.SegmentedButton(
                    "Tabela A",
                    R.color.colorAccent
                ),
                SegmentedButtonGroup.SegmentedButton(
                    "Tabela B",
                    R.color.colorPrimary
                )
            ),
            this, requireContext()
        )
        binding.segmented.addView(group)
    }

    private fun executeEffects(effect: TableViewEffect) {
        when (effect) {
            is TableViewEffect.ShowTable -> Toast.makeText(
                requireContext(),
                effect.table.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onSegmentChange(previousItemIndex: Int, newItemIndex: Int) {
        Toast.makeText(
            requireContext(),
            "previous: $previousItemIndex, next: $newItemIndex",
            Toast.LENGTH_LONG
        ).show()
        viewModel.onAction(TableViewAction.SegmentChanged(newItemIndex))
            .subscribeTo(compositeDisposable)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTableBinding =
        FragmentTableBinding.inflate(inflater, container, false)

    override fun handleInjection() {
        ComponentProvider.createComponent(this)
    }
}