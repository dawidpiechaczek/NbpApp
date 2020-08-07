package pl.piechaczek.dawid.table.ui.root

sealed class SegmentedTableViewAction {
    data class SegmentChanged(val tableType: Char) : SegmentedTableViewAction()
}