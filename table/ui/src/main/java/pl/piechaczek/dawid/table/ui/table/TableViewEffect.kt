package pl.piechaczek.dawid.table.ui.table

import pl.piechaczek.dawid.table.ui.model.Rate

internal sealed class TableViewEffect {
    data class ShowTable(val rates: List<Rate>) : TableViewEffect()
}