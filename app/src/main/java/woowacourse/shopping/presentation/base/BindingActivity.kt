package woowacourse.shopping.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutResourceId: Int
    private var _binding: T? = null
    val binding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResourceId)
        initStartView()
    }

    abstract fun initStartView()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
