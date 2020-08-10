package pl.piechaczek.dawid.details.ui.root

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

abstract class DetailsViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<DetailsViewEffect>
    abstract fun observeState(): Observable<DetailsViewState>
    abstract fun onAction(action: DetailsViewAction): Completable
}

internal class DefaultDetailsViewModel @Inject constructor() : DetailsViewModel() {

    private val effects = PublishSubject.create<DetailsViewEffect>()
    private val state = BehaviorSubject.create<DetailsViewState>()

    override fun observeEffects(): Observable<DetailsViewEffect> = effects.hide()

    override fun observeState(): Observable<DetailsViewState> = state.hide()

    override fun onAction(action: DetailsViewAction): Completable =
        when (action) {
            is DetailsViewAction.ChangeDates -> changeDates(action.startDate, action.endDate)
        }

    private fun changeDates(startDate: ZonedDateTime, endDate: ZonedDateTime): Completable = Completable.fromCallable {
        effects.onNext(DetailsViewEffect.ChangeDates(startDate.toString(), endDate.toString()))
    }
}