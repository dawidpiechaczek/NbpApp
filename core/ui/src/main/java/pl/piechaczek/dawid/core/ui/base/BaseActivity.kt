package pl.piechaczek.dawid.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity<TViewModel : ViewModel, TBinding : ViewBinding> constructor(
    private val viewModelClass: Class<TViewModel>
): DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val compositeDisposable: CompositeDisposable

    val viewModel by lazy{
        ViewModelProvider(this,  viewModelFactory).get(viewModelClass)
    }

    private var _binding: TBinding? = null
    val binding: TBinding get() = _binding!!

    abstract fun inflateBinding(inflater: LayoutInflater): TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = inflateBinding(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}