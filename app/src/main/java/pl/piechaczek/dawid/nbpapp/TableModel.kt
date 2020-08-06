package pl.piechaczek.dawid.nbpapp

data class TableModel(
    val table: Char,
    val no: String,
    val effectiveDate: String,
    val rates: List<RatesModel>
)