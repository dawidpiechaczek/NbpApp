package pl.piechaczek.dawid.table.ui.table

internal sealed class TableViewAction {
    data class SegmentChanged(val newItemIndex: Int) : TableViewAction()
    data class ShowToast(val tableType: Char) : TableViewAction()
}
