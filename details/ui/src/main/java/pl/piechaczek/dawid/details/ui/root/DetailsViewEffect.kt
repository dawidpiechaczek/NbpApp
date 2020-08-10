package pl.piechaczek.dawid.details.ui.root

sealed class DetailsViewEffect {
    data class ChangeDates(val startDate: String, val endDate: String) : DetailsViewEffect()
}