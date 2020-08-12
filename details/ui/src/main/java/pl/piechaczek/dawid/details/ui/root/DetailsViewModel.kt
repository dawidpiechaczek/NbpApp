package pl.piechaczek.dawid.details.ui.root

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

abstract class DetailsViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<DetailsViewEffect>
    abstract fun observeState(): Observable<DetailsViewState>
    abstract fun onAction(action: DetailsViewAction): Completable
}

internal class DefaultDetailsViewModel @Inject constructor() : DetailsViewModel() {

    private val effects = PublishSubject.create<DetailsViewEffect>()
    private val state = BehaviorSubject.createDefault(DetailsViewState())

    override fun observeEffects(): Observable<DetailsViewEffect> = effects.hide()

    override fun observeState(): Observable<DetailsViewState> = state.hide()

    override fun onAction(action: DetailsViewAction): Completable =
        when (action) {
            is DetailsViewAction.StartDate -> changeStartDate(action.startDate)
            is DetailsViewAction.EndDate -> changeEndDate(action.endDate)
            is DetailsViewAction.OpenCalendarDialog -> openCalendarDialog(
                state.value,
                action.requestId
            )
        }

    private fun openCalendarDialog(value: DetailsViewState?, requestId: Int): Completable =
        Completable.fromCallable {
            val date = if (requestId == CALENDAR_START_DATE) {
                value?.startDate
            } else {
                value?.endDate
            }
            effects.onNext(
                DetailsViewEffect.OpenCalendarDialog(date, requestId)
            )
        }

    private fun changeStartDate(startDate: LocalDateTime): Completable = Completable.fromCallable {
        state.onNext(
            state.value?.copy(startDate = startDate) ?: DetailsViewState(startDate = startDate)
        )
    }

    private fun changeEndDate(endDate: LocalDateTime): Completable = Completable.fromCallable {
        state.onNext(state.value?.copy(endDate = endDate) ?: DetailsViewState(endDate = endDate))
    }
}