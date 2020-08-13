package pl.piechaczek.dawid.table.ui.model

import pl.piechaczek.dawid.core.data.model.TableModel

data class Table(
    val type: Char,
    val number: String,
    val effectiveDate: String,
    val rates: List<Rate>
)

fun TableModel.map() =
    Table(
        type = table,
        number = no,
        effectiveDate = effectiveDate,
        rates = rates.map { it.map(effectiveDate) }
    )