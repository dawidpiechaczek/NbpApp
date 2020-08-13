package pl.piechaczek.dawid.details.ui.root

import org.threeten.bp.LocalDate

sealed class DetailsViewAction {
    data class StartDate(val tableType: Char, val code: String, val startDate: LocalDate) : DetailsViewAction()
    data class EndDate(val tableType: Char, val code: String, val endDate: LocalDate) : DetailsViewAction()
    data class OpenCalendarDialog(val requestId: Int) : DetailsViewAction()
}