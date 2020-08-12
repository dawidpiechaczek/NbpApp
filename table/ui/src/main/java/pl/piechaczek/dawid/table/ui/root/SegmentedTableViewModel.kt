package pl.piechaczek.dawid.table.ui.root

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

abstract class SegmentedTableViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<SegmentedTableViewEffect>
    abstract fun observeState(): Observable<SegmentedTableViewState>
    abstract fun onAction(action: SegmentedTableViewAction): Completable
}

internal class DefaultSegmentedViewModel @Inject constructor() : SegmentedTableViewModel() {

    private val effects = PublishSubject.create<SegmentedTableViewEffect>()
    private val state = BehaviorSubject.create<SegmentedTableViewState>()

    override fun observeEffects(): Observable<SegmentedTableViewEffect> = effects.hide()

    override fun observeState(): Observable<SegmentedTableViewState> = state.hide()

    override fun onAction(action: SegmentedTableViewAction): Completable =
        when (action) {
            is SegmentedTableViewAction.SegmentChanged -> changeSegment(action.tableType)
        }

    private fun changeSegment(tableType: Char): Completable = Completable.fromCallable {
        effects.onNext(SegmentedTableViewEffect.ChangeTable(tableType))
    }
}