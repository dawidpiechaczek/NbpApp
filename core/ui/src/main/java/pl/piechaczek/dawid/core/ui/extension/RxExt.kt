package pl.piechaczek.dawid.core.ui.extension

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber

fun Completable.subscribeTo(compositeDisposable: CompositeDisposable) {
    this.onErrorComplete()
        .subscribeOn(Schedulers.io())
        .subscribeBy()
        .addTo(compositeDisposable)
}

fun <T> Response<T>.toBodyOrError(): T {
    return if (isSuccessful) {
        body()!!
    } else {
        Timber.e(this.toString())
        throw HttpRequestException(code())
    }
}

data class HttpRequestException(val errorCode: Int) : RuntimeException()