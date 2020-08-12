package pl.piechaczek.dawid.details.ui.root

import org.threeten.bp.LocalDateTime

sealed class DetailsViewEffect {
    data class OpenCalendarDialog(val value: LocalDateTime?, val requestId: Int) : DetailsViewEffect()
}