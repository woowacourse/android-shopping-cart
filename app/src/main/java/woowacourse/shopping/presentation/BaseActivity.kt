package woowacourse.shopping.presentation

import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity : AppCompatActivity() {
    protected inline fun <reified T : ViewDataBinding> bind(
        @LayoutRes resId: Int,
    ): Lazy<T> = lazy { DataBindingUtil.setContentView(this, resId) }

    protected fun setUpScreen(view: View) {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
