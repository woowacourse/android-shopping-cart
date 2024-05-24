package woowacourse.shopping.presentation.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(
    @StringRes messageRes: Int,
) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    if (!isAdded) return
    requireContext().showToast(message)
}

fun Fragment.showToast(
    @StringRes messageRes: Int,
) {
    if (!isAdded) return
    requireContext().showToast(getString(messageRes))
}
