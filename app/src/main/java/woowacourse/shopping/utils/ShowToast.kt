package woowacourse.shopping.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(
    @StringRes stringRes: Int,
) {
    showToast(getString(stringRes))
}
