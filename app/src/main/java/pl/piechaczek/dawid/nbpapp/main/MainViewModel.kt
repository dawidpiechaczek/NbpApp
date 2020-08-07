package pl.piechaczek.dawid.nbpapp.main

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import javax.inject.Inject

abstract class MainViewModel : ViewModel() {
    abstract fun onAction(action: MainViewAction): Completable
}

class DefaultMainViewModel @Inject constructor() : MainViewModel() {

    override fun onAction(action: MainViewAction): Completable =
        when (action) {
            is MainViewAction.Progress -> showProgress()
        }

    private fun showProgress(): Completable {
        return Completable.complete()
    }
}