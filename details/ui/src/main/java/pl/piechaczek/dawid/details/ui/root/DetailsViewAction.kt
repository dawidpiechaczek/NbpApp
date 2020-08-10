package pl.piechaczek.dawid.details.ui.root

import org.threeten.bp.ZonedDateTime

sealed class DetailsViewAction {
    data class ChangeDates(val startDate: ZonedDateTime, val endDate: ZonedDateTime) : DetailsViewAction()
}