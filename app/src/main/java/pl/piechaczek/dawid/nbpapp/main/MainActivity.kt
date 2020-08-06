package pl.piechaczek.dawid.nbpapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.piechaczek.dawid.core.ui.base.BaseActivity
import pl.piechaczek.dawid.core.ui.extension.subscribeTo
import pl.piechaczek.dawid.core.ui.widget.OnSegmentChangeListener
import pl.piechaczek.dawid.core.ui.widget.SegmentedButtonGroup
import pl.piechaczek.dawid.nbpapp.R
import pl.piechaczek.dawid.nbpapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java), OnSegmentChangeListener {

    override val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSegmentedButtonGroup()

        viewModel.observeEffects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = this::executeEffects)
            .addTo(compositeDisposable)

        viewModel.onAction(MainViewAction.ShowToast)
            .subscribeTo(compositeDisposable)
    }

    private fun initSegmentedButtonGroup() {
        val group = SegmentedButtonGroup(
            listOf(
                SegmentedButtonGroup.SegmentedButton(
                    "Tabela A",
                    R.color.colorAccent
                ),
                SegmentedButtonGroup.SegmentedButton(
                    "Tabela B",
                    R.color.colorPrimary
                )
            ),
            this, this
        )
        findViewById<LinearLayout>(R.id.segmented).addView(group)
    }

    private fun executeEffects(effect: MainViewEffect) {
        Toast.makeText(applicationContext, "Blabla", Toast.LENGTH_LONG).show()
    }

    override fun onSegmentChange(previousItemIndex: Int, newItemIndex: Int) {
        Toast.makeText(
            applicationContext,
            "previous: $previousItemIndex, next: $newItemIndex",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun inflateBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)
}