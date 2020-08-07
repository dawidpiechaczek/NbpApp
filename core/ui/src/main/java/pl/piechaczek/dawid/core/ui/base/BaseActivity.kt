package pl.piechaczek.dawid.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import io.reactivex.disposables.CompositeDisposable
import pl.piechaczek.dawid.core.ui.di.CoreComponentProvider
import javax.inject.Inject

abstract class BaseActivity<TViewModel : ViewModel, TBinding : ViewBinding> constructor(
    private val viewModelClass: Class<TViewModel>
) : FragmentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val compositeDisposable: CompositeDisposable

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }

    abstract fun handleInjection()

    val coreComponent
        get() = CoreComponentProvider.getComponent()

    private var _binding: TBinding? = null
    val binding: TBinding get() = _binding!!

    abstract fun inflateBinding(inflater: LayoutInflater): TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        handleInjection()
        super.onCreate(savedInstanceState)

        _binding = inflateBinding(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}