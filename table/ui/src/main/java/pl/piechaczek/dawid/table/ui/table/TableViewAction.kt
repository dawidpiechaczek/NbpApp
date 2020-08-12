package pl.piechaczek.dawid.table.ui.table

internal sealed class TableViewAction {
    data class SegmentChanged(val newItemIndex: Int) : TableViewAction()
    data class GetInfoForTable(val tableType: Char) : TableViewAction()
}
