package pl.piechaczek.dawid.core.ui.navigation

interface MainNavigator {
    fun navigateToTableView()
    fun navigateToDetailsView(tableType: Char, currencyName: String, currencyCode: String)
}