package pl.piechaczek.dawid.nbpapp.main

import android.os.Bundle
import android.view.LayoutInflater
import io.reactivex.disposables.CompositeDisposable
import pl.piechaczek.dawid.core.ui.base.BaseActivity
import pl.piechaczek.dawid.core.ui.navigation.MainNavigator
import pl.piechaczek.dawid.core.ui.NbpApp
import pl.piechaczek.dawid.nbpapp.navigation.MainNavigatorImpl
import pl.piechaczek.dawid.nbpapp.R
import pl.piechaczek.dawid.nbpapp.databinding.ActivityMainBinding
import pl.piechaczek.dawid.nbpapp.di.ComponentProvider
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    @Inject lateinit var mainNavigator: MainNavigator

    override val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainNavigator.navigateToTableView()
    }

    override fun inflateBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    override fun handleInjection() {
        mainNavigator =
            MainNavigatorImpl(this)
        (application as NbpApp).provideMainNavigator(mainNavigator)

        ComponentProvider.createComponent(this)
    }
}