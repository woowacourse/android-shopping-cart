package woowacourse.shopping.view

import android.content.Intent
import android.os.Build
import android.os.Parcelable

inline fun <reified T : Parcelable> Intent.getParcelableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        extras?.getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        extras?.getParcelable(key)
    }
}
