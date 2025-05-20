package woowacourse.shopping.view.core.ext

import android.content.Context

fun Context.showToast(
    message: String,
    duration: Int = android.widget.Toast.LENGTH_SHORT,
) {
    android.widget.Toast.makeText(this, message, duration).show()
}
