package pl.piechaczek.dawid.nbpapp.main

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.piechaczek.dawid.nbpapp.TableUseCase
import javax.inject.Inject

abstract class MainViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<MainViewEffect>
    abstract fun observeState(): Observable<MainViewState>
    abstract fun onAction(action: MainViewAction): Completable
}

class DefaultMainViewModel @Inject constructor(
    private val tableUseCase: TableUseCase
) : MainViewModel() {

    private val effects = PublishSubject.create<MainViewEffect>()
    private val state = BehaviorSubject.create<MainViewState>()

    override fun observeEffects(): Observable<MainViewEffect> = effects.hide()

    override fun observeState(): Observable<MainViewState> = state.hide()

    override fun onAction(action: MainViewAction): Completable =
        when (action) {
            is MainViewAction.ShowToast -> showToast()
            is MainViewAction.SegmentChanged -> changeSegment(action.newItemIndex)
        }

    private fun changeSegment(newSegmentIndex: Int): Completable = Completable.fromCallable {
        state.onNext(MainViewState(newSegmentIndex))
    }

    private fun showToast(): Completable =
        tableUseCase.getTable('A')
            .flatMapCompletable {
                effects.onNext(MainViewEffect.Table(it))
                Completable.complete()
            }
}