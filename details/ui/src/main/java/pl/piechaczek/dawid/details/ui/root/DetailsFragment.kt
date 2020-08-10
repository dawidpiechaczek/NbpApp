package pl.piechaczek.dawid.details.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.piechaczek.dawid.core.ui.base.BaseFragment
import pl.piechaczek.dawid.details.ui.databinding.FragmentDetailsBinding
import pl.piechaczek.dawid.details.ui.di.ComponentProvider
import timber.log.Timber

class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>
    (DetailsViewModel::class.java) {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStateAndEffects()
        initView()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }


    private fun initView() {
        val currencyName = arguments?.getString(CURRENCY_NAME)
        val currencyCode = arguments?.getString(CURRENCY_CODE)

        Toast.makeText(context, "Nazwa: $currencyName i kod: $currencyCode", Toast.LENGTH_LONG)
            .show()
    }

    private fun executeState(state: DetailsViewState) {

    }

    private fun executeEffects(effect: DetailsViewEffect) {
        when (effect) {
            is DetailsViewEffect.ChangeDates -> changeDates(effect.startDate)
        }
    }

    private fun changeDates(startDate: String) {

    }

    private fun initStateAndEffects() {
        viewModel.observeEffects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = this::executeEffects, onError = Timber::e)
            .addTo(compositeDisposable)

        viewModel.observeState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = this::executeState, onError = Timber::e)
            .addTo(compositeDisposable)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(inflater, container, false)

    override fun handleInjection() {
        ComponentProvider.createComponent(this)
    }

    companion object {
        private const val CURRENCY_NAME = "currency_name"
        private const val CURRENCY_CODE = "currency_code"

        fun args(name: String, code: String): Bundle = Bundle().apply {
            putString(CURRENCY_NAME, name)
            putString(CURRENCY_CODE, code)
        }
    }
}
