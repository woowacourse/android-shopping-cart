package woowacourse.shopping.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    protected fun showToast(
        @StringRes messageResId: Int,
    ) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
