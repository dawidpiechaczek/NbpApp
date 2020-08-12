package pl.piechaczek.dawid.details.ui.root

import org.threeten.bp.LocalDateTime

data class DetailsViewState(val startDate: LocalDateTime? = null, val endDate: LocalDateTime? = null)