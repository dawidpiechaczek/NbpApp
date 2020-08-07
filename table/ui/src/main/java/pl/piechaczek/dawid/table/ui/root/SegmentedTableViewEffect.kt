package pl.piechaczek.dawid.table.ui.root

sealed class SegmentedTableViewEffect {
    data class ChangeTable(val tableType: Char) : SegmentedTableViewEffect()
}