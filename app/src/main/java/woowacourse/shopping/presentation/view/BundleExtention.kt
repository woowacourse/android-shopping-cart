package woowacourse.shopping.presentation.view

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

@Suppress("DEPRECATION")
internal inline fun <reified T : Parcelable> Bundle.getParcelableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        getParcelable(key) as? T
    }
}
