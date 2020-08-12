package pl.piechaczek.dawid.details.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import pl.piechaczek.dawid.core.data.extension.subscribeTo
import pl.piechaczek.dawid.core.ui.base.BaseFragment
import pl.piechaczek.dawid.core.ui.dialog.CalendarDialog
import pl.piechaczek.dawid.core.ui.dialog.CalendarDialogCallback
import pl.piechaczek.dawid.details.ui.databinding.FragmentDetailsBinding
import pl.piechaczek.dawid.details.ui.di.ComponentProvider
import timber.log.Timber

const val CALENDAR_START_DATE = 1111
const val CALENDAR_END_DATE = 2222

class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>
    (DetailsViewModel::class.java), CalendarDialogCallback {

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

        binding.currencyName.text = currencyName
        binding.currencyCode.text = currencyCode
        binding.startDate.setOnClickListener {
            viewModel.onAction(DetailsViewAction.OpenCalendarDialog(CALENDAR_START_DATE))
                .subscribeTo(compositeDisposable)
        }
        binding.endDate.setOnClickListener {
            viewModel.onAction(DetailsViewAction.OpenCalendarDialog(CALENDAR_END_DATE))
                .subscribeTo(compositeDisposable)
        }
    }

    private fun openCalendarDialog(date: LocalDateTime?, requestId: Int) {
        val dialog = CalendarDialog.newInstance(date.toString(), requestId)
        dialog.setSafeTargetFragment(this)
        dialog.show(requireFragmentManager(), CalendarDialog::class.java.name)
    }

    override fun onDateSelected(requestId: Int, date: LocalDateTime?) {
        if (date == null) {
            return
        }

        when (requestId) {
            CALENDAR_START_DATE -> viewModel.onAction(DetailsViewAction.StartDate(date))
                .subscribeTo(compositeDisposable)
            CALENDAR_END_DATE -> viewModel.onAction(DetailsViewAction.EndDate(date))
                .subscribeTo(compositeDisposable)
        }
    }

    private fun executeState(state: DetailsViewState) {
        val startDate = state.startDate
        if (startDate == null) {
            binding.startDate.setText("")
        } else {
            binding.startDate.setText("${startDate.dayOfMonth}-${startDate.monthValue}-${startDate.year}")
        }

        val endDate = state.endDate
        if (endDate == null) {
            binding.endDate.setText("")
        } else {
            binding.endDate.setText("${endDate.dayOfMonth}-${endDate.monthValue}-${endDate.year}")
        }
    }

    private fun executeEffects(effect: DetailsViewEffect) {
        when (effect) {
            is DetailsViewEffect.OpenCalendarDialog -> openCalendarDialog(
                effect.value,
                effect.requestId
            )
        }
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
