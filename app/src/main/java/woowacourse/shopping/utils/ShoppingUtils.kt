package woowacourse.shopping.utils

import android.content.Context
import android.widget.Toast

object ShoppingUtils {
    fun makeToast(
        context: Context,
        message: String,
    ) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }
}
