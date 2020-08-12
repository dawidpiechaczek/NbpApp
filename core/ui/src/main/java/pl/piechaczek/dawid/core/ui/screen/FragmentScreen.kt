package pl.piechaczek.dawid.core.ui.screen

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import timber.log.Timber

@Parcelize
data class FragmentScreen @JvmOverloads constructor(
    val fragmentClass: Class<out Fragment>,
    private val arguments: Bundle? = null,
    val tag: String? = null,
    val addToBackStack: Boolean = true
) : Parcelable {

    var animEnter = -1
    var animExit = -1
    var animPopEnter = -1
    var animPopExit = -1

    val hasAnimation
        get() = animEnter != -1 && animExit != -1

    /**
     * Creates new fragment with sent arguments
     */
    fun create(): Fragment {
        return try {
            fragmentClass.newInstance()
                .also {
                    if (arguments != null)
                        it.arguments = arguments
                }
        } catch (exception: InstantiationException) {
            Timber.d("FragmentScreen::create::InstantiationException ${exception.message}")
            throw exception
        } catch (exception: IllegalAccessException) {
            Timber.d("FragmentScreen::create::IllegalAccessException ${exception.message}")
            throw exception
        }
    }

    @JvmOverloads
    fun withAnimation(@AnimRes enter: Int, @AnimRes exit: Int, @AnimRes popEnter: Int = -1, @AnimRes popExit: Int = -1): FragmentScreen {
        animEnter = enter
        animExit = exit
        animPopEnter = popEnter
        animPopExit = popExit

        return this
    }
}