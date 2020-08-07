package pl.piechaczek.dawid.table.ui

sealed class TableViewAction {
    data class SegmentChanged(val newItemIndex: Int) : TableViewAction()
    object ShowToast : TableViewAction()
}
