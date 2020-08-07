package pl.piechaczek.dawid.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(private val providers: ViewModelProviders) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var provider = providers[modelClass]
        if (provider == null) {
            for ((key, value) in providers) {
                if (modelClass.isAssignableFrom(key)) {
                    provider = value
                }
            }
        }
        @Suppress("UNCHECKED_CAST")
        return requireNotNull(provider).get() as T
    }
}

typealias ViewModelProviders =  Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
