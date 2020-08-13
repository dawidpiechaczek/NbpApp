package pl.piechaczek.dawid.details.ui.root

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import pl.piechaczek.dawid.details.data.usecases.DetailsUseCase
import pl.piechaczek.dawid.details.ui.model.map
import javax.inject.Inject

const val dateFormat = "yyyy-MM-dd"

abstract class DetailsViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<DetailsViewEffect>
    abstract fun observeState(): Observable<DetailsViewState>
    abstract fun onAction(action: DetailsViewAction): Completable
}

internal class DefaultDetailsViewModel @Inject constructor(
    private val detailsUseCase: DetailsUseCase
) : DetailsViewModel() {

    private val effects = PublishSubject.create<DetailsViewEffect>()
    private val state = BehaviorSubject.createDefault(DetailsViewState())

    override fun observeEffects(): Observable<DetailsViewEffect> = effects.hide()

    override fun observeState(): Observable<DetailsViewState> = state.hide()

    override fun onAction(action: DetailsViewAction): Completable =
        when (action) {
            is DetailsViewAction.StartDate -> changeStartDate(
                action.tableType, action.code, action.startDate
            )
            is DetailsViewAction.EndDate -> changeEndDate(
                action.tableType, action.code, action.endDate
            )
            is DetailsViewAction.OpenCalendarDialog -> openCalendarDialog(
                state.value, action.requestId
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

    private fun changeStartDate(
        tableType: Char,
        code: String,
        startDate: LocalDate
    ): Completable = Completable.fromAction {
        //TODO check startDate earlier then endDate
        //TODO check 367 days between dates
        //TODO check endDate is max today
        val newState = state.value?.copy(startDate = startDate)
            ?: DetailsViewState(startDate = startDate)
        state.onNext(newState)
    }.andThen(loadRatesIfBothDatesAdded(tableType, code, startDate, state.value?.endDate))


    private fun changeEndDate(tableType: Char, code: String, endDate: LocalDate): Completable =
        Completable.fromAction {
            val newState = state.value?.copy(endDate = endDate)
                ?: DetailsViewState(endDate = endDate)
            state.onNext(newState)
        }.andThen(loadRatesIfBothDatesAdded(tableType, code, state.value?.startDate, endDate))

    private fun loadRatesIfBothDatesAdded(
        tableType: Char,
        code: String,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): Completable {
        return if (startDate != null && endDate != null) {
            loadRates(
                tableType,
                code,
                convertDateToString(startDate),
                convertDateToString(endDate)
            )
        } else {
            Completable.fromCallable {
                effects.onNext(DetailsViewEffect.ShowToast("Wprowadź obie daty, aby pobrać tabelę kursów"))
            }
        }
    }

    private fun convertDateToString(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern(dateFormat))
    }

    private fun loadRates(
        tableType: Char,
        code: String,
        startDate: String,
        endDate: String
    ): Completable =
        detailsUseCase.getRatesForDates(tableType, code, startDate, endDate)
            .doOnSubscribe { effects.onNext(DetailsViewEffect.ShowProgress) }
            .flatMapCompletable {
                val rates = it.map().rates
                effects.onNext(
                    DetailsViewEffect.ShowRates(rates)
                )
                Completable.complete()
            }
            .doOnComplete { effects.onNext(DetailsViewEffect.HideProgress) }
            .doOnError {
                effects.onNext(DetailsViewEffect.HideProgress)
                effects.onNext(DetailsViewEffect.ShowToast("Wystąpił błąd, proszę spróbuj ponownie, błąd: ${it.localizedMessage}"))
            }
}