package pl.piechaczek.dawid.core.data.model

data class TableModel(
    val table: Char,
    val no: String,
    val effectiveDate: String,
    val rates: List<RateModel>
)