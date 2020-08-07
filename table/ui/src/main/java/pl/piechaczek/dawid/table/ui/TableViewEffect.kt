package pl.piechaczek.dawid.table.ui

import pl.piechaczek.dawid.table.ui.model.Table

sealed class TableViewEffect {
    data class ShowTable(val table: List<Table>) : TableViewEffect()
}
