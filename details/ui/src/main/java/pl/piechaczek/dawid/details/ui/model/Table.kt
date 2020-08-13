package pl.piechaczek.dawid.details.ui.model

import pl.piechaczek.dawid.core.data.model.DetailsTableModel

data class Table(
    val type: Char,
    val currency: String,
    val code: String,
    val rates: List<Rate>
)

fun DetailsTableModel.map() =
    Table(
        type = table,
        currency = currency,
        code = code,
        rates = rates.map { it.map() }
    )