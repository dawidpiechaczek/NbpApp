package pl.piechaczek.dawid.nbpapp.main

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

abstract class MainViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<MainViewEffect>
    abstract fun onAction(action: MainViewAction): Completable
}

class DefaultMainViewModel @Inject constructor() : MainViewModel() {

    private val effects = PublishSubject.create<MainViewEffect>()

    override fun observeEffects(): Observable<MainViewEffect> = effects.hide()

    override fun onAction(action: MainViewAction): Completable =
        when (action) {
            is MainViewAction.ShowToast -> showToast()
        }

    private fun showToast(): Completable {
        return Completable.fromCallable {
            effects.onNext(MainViewEffect.Progress)
        }
    }
}