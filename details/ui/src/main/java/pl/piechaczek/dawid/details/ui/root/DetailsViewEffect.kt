package pl.piechaczek.dawid.details.ui.root

import org.threeten.bp.LocalDate
import pl.piechaczek.dawid.details.ui.model.Rate

sealed class DetailsViewEffect {
    object ShowProgress : DetailsViewEffect()
    object HideProgress : DetailsViewEffect()
    data class OpenCalendarDialog(val value: LocalDate?, val requestId: Int) : DetailsViewEffect()
    data class ShowToast(val message: String) : DetailsViewEffect()
    data class ShowRates(val rates: List<Rate>) : DetailsViewEffect()
}