package pl.piechaczek.dawid.table.ui.table

internal sealed class TableViewAction {
    data class GetInfoForTable(val tableType: Char) : TableViewAction()
}
