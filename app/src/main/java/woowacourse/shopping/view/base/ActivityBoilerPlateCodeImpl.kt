package woowacourse.shopping.view.base

import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class ActivityBoilerPlateCodeImpl<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : ActivityBoilerPlateCode<T> {
    override lateinit var binding: T

    override fun AppCompatActivity.initialize() {
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
        enableEdgeToEdge()
        setWindowInsets()
    }

    override fun AppCompatActivity.onUnexpectedError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun setWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
