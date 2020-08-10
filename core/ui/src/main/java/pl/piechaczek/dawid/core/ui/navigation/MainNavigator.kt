package pl.piechaczek.dawid.core.ui.navigation

interface MainNavigator {
    fun navigateToTableView()
    fun navigateToDetailsView(currencyName: String, currencyCode: String)
}