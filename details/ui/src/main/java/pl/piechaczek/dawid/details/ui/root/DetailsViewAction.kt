package pl.piechaczek.dawid.details.ui.root

import org.threeten.bp.LocalDateTime

sealed class DetailsViewAction {
    data class StartDate(val startDate: LocalDateTime) : DetailsViewAction()
    data class EndDate(val endDate: LocalDateTime) : DetailsViewAction()
    data class OpenCalendarDialog(val requestId: Int) : DetailsViewAction()
}