package pl.piechaczek.dawid.table.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.piechaczek.dawid.core.data.extension.subscribeTo
import pl.piechaczek.dawid.core.ui.screen.FragmentScreen
import pl.piechaczek.dawid.core.ui.base.BaseFragment
import pl.piechaczek.dawid.core.ui.widget.OnSegmentChangeListener
import pl.piechaczek.dawid.core.ui.widget.SegmentedButtonGroup
import pl.piechaczek.dawid.table.ui.*
import pl.piechaczek.dawid.table.ui.databinding.FragmentSegmentedTableBinding
import pl.piechaczek.dawid.table.ui.di.ComponentProvider
import pl.piechaczek.dawid.table.ui.table.TableFragment
import pl.piechaczek.dawid.table.ui.table.TableViewState
import timber.log.Timber

class SegmentedTableFragment :
    BaseFragment<SegmentedTableViewModel, FragmentSegmentedTableBinding>(
        SegmentedTableViewModel::class.java
    ),
    OnSegmentChangeListener {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSegmentedButtonGroup()

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

    private fun replaceFragment(screen: FragmentScreen) {
        val fragmentTransaction = childFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.table_fragment_container, screen.create(), screen.tag)

        if (screen.addToBackStack) {
            fragmentTransaction.addToBackStack(screen.tag ?: screen.fragmentClass.name)
        }

        fragmentTransaction.commit()
    }


    private fun executeState(state: SegmentedTableViewState) {
        binding.segmentedButtons.setCurrentItemIndex(state.selectedSegment)
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun initSegmentedButtonGroup() {
        binding.segmentedButtons.setSegmentChangeListener(this)
        binding.segmentedButtons.setSegmentedButtons(
            listOf(
                SegmentedButtonGroup.SegmentedButton(
                    text = "Tabela A",
                    color = R.color.colorAccent,
                    tableType = 'A'
                ),
                SegmentedButtonGroup.SegmentedButton(
                    text = "Tabela B",
                    color = R.color.colorPrimary,
                    tableType = 'B'
                ),
                SegmentedButtonGroup.SegmentedButton(
                    text = "Tabela C",
                    color = R.color.colorPrimaryDark,
                    tableType = 'C'
                )
            )
        )
    }

    private fun executeEffects(effect: SegmentedTableViewEffect) {
        when (effect) {
            is SegmentedTableViewEffect.ChangeTable -> changeTable(effect.tableType)
        }
    }

    private fun changeTable(tableType: Char) {
        replaceFragment(
            FragmentScreen(
                TableFragment::class.java,
                TableFragment.args(tableType),
                TableFragment::class.java.name
            )
        )
    }

    override fun onSegmentChange(previousItemIndex: Int, newItemIndex: Int) {
        val selectedSegment = binding.segmentedButtons.getItemAt(newItemIndex)

        viewModel.onAction(SegmentedTableViewAction.SegmentChanged(selectedSegment.tableType))
            .subscribeTo(compositeDisposable)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSegmentedTableBinding =
        FragmentSegmentedTableBinding.inflate(inflater, container, false)

    override fun handleInjection() {
        ComponentProvider.createComponent(this)
    }

    companion object {
        fun args(): Bundle = Bundle()
    }
}