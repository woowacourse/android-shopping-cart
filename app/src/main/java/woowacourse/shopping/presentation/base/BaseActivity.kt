package woowacourse.shopping.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import woowacourse.shopping.R

abstract class BaseActivity<T : ViewDataBinding>(private val layoutResId: Int) : AppCompatActivity() {
    protected lateinit var binding: T
    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setTheme(R.style.Theme_Shopping)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
        onCreateSetup()
    }

    abstract fun onCreateSetup()

    protected fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}
