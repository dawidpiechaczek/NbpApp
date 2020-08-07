package pl.piechaczek.dawid.core.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import io.reactivex.disposables.CompositeDisposable
import pl.piechaczek.dawid.core.ui.di.CoreComponentProvider
import javax.inject.Inject

abstract class BaseFragment<TViewModel : ViewModel, TBinding : ViewBinding> constructor(
    private val viewModelClass: Class<TViewModel>
) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val compositeDisposable: CompositeDisposable

    open val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }

    private var _binding: TBinding? = null
    val binding: TBinding get() = _binding!!

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): TBinding

    val coreComponent
        get() = CoreComponentProvider.getComponent()

    abstract fun handleInjection()

    override fun onAttach(context: Context) {
        handleInjection()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}