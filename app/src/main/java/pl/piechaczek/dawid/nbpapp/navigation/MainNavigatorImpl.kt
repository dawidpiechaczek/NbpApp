package pl.piechaczek.dawid.nbpapp.navigation

import pl.piechaczek.dawid.core.ui.navigation.MainNavigator
import pl.piechaczek.dawid.core.ui.screen.FragmentScreen
import pl.piechaczek.dawid.details.ui.root.DetailsFragment
import pl.piechaczek.dawid.nbpapp.R
import pl.piechaczek.dawid.nbpapp.main.MainActivity
import pl.piechaczek.dawid.table.ui.root.SegmentedTableFragment
import javax.inject.Inject

class MainNavigatorImpl @Inject constructor(
    private val activity: MainActivity
) : MainNavigator {

    private fun replaceFragment(screen: FragmentScreen) {
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()

        screen.run {
            if (hasAnimation) {
                fragmentTransaction.setCustomAnimations(
                    animEnter,
                    animExit,
                    animPopEnter,
                    animPopExit
                )
            }

            fragmentTransaction.replace(
                R.id.fragment_container,
                screen.create(),
                screen.tag
            )

            if (screen.addToBackStack) {
                fragmentTransaction.addToBackStack(
                    screen.tag
                        ?: screen.fragmentClass.name
                )
            }

            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    override fun navigateToTableView() {
        replaceFragment(
            FragmentScreen(
                SegmentedTableFragment::class.java,
                SegmentedTableFragment.args(),
                SegmentedTableFragment::class.java.name
            )
        )
    }

    override fun navigateToDetailsView(tableType: Char, currencyName: String, currencyCode: String) {
        replaceFragment(
            FragmentScreen(
                DetailsFragment::class.java,
                DetailsFragment.args(tableType, currencyName, currencyCode),
                DetailsFragment::class.java.name
            )
        )
    }
}