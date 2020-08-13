package pl.piechaczek.dawid.core.data.model

data class DetailsTableModel(
    val table: Char,
    val currency: String,
    val code: String,
    val rates: List<DetailsRateModel>
)