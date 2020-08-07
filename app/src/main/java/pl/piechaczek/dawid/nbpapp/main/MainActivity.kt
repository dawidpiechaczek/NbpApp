package pl.piechaczek.dawid.nbpapp.main

import android.os.Bundle
import android.view.LayoutInflater
import io.reactivex.disposables.CompositeDisposable
import pl.piechaczek.dawid.core.ui.base.BaseActivity
import pl.piechaczek.dawid.nbpapp.R
import pl.piechaczek.dawid.nbpapp.databinding.ActivityMainBinding
import pl.piechaczek.dawid.nbpapp.di.ComponentProvider

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java) {

    override val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun inflateBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    override fun handleInjection() {
        ComponentProvider.createComponent(this)
    }
}