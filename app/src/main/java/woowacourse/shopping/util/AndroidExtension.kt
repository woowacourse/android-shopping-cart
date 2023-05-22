package woowacourse.shopping.util

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.widget.Toast

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.getParcelableExtraCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        getParcelableExtra(key) as? T
    }
}

fun Activity.executeExceptionHandler(errorMessage: String) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    finish()
}
