package woowacourse.shopping.utils

import android.content.Context
import android.widget.Toast

object ShoppingUtils {
    fun Context.makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }
}
