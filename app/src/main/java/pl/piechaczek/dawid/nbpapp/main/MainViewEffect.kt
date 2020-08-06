package pl.piechaczek.dawid.nbpapp.main

import pl.piechaczek.dawid.nbpapp.TableModel

sealed class MainViewEffect {
    data class Table(val table: List<TableModel>) : MainViewEffect()
}
