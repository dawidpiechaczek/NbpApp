package pl.piechaczek.dawid.table.ui.table

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.piechaczek.dawid.table.ui.model.map
import pl.piechaczek.dawid.table.data.usecase.TableUseCase
import javax.inject.Inject

internal abstract class TableViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<TableViewEffect>
    abstract fun observeState(): Observable<TableViewState>
    abstract fun onAction(action: TableViewAction): Completable
}

internal class DefaultTableViewModel @Inject constructor(
    private val tableUseCase: TableUseCase
) : TableViewModel() {

    private val effects = PublishSubject.create<TableViewEffect>()
    private val state = BehaviorSubject.create<TableViewState>()

    override fun observeEffects(): Observable<TableViewEffect> = effects.hide()

    override fun observeState(): Observable<TableViewState> = state.hide()

    override fun onAction(action: TableViewAction): Completable =
        when (action) {
            is TableViewAction.GetInfoForTable -> getInfoForTable(action.tableType)
            is TableViewAction.SegmentChanged -> changeSegment(action.newItemIndex)
        }

    private fun changeSegment(newSegmentIndex: Int): Completable = Completable.fromCallable {
        state.onNext(
            state.value?.copy(selectedSegment = newSegmentIndex) ?: TableViewState(
                newSegmentIndex
            )
        )
    }

    private fun getInfoForTable(tableType: Char): Completable =
        tableUseCase.getTable(tableType)
            .doOnSubscribe { effects.onNext(TableViewEffect.ShowProgress) }
            .flatMapCompletable {
                if (it.isNotEmpty()) {
                    val rates = it[0].map().rates
                    effects.onNext(
                        TableViewEffect.ShowTable(it[0].table, rates)
                    )
                } else {
                    effects.onNext(TableViewEffect.ShowToast("Tabela jest pusta"))
                }
                Completable.complete()
            }
            .doOnComplete { effects.onNext(TableViewEffect.HideProgress) }
}