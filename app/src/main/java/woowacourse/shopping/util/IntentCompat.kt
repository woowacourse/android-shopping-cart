package woowacourse.shopping.util

import android.content.Intent
import android.os.Build
import android.os.Parcelable

@Suppress("DEPRECATION")
fun <T : Parcelable> Intent.intentParcelableExtra(
    key: String,
    clazz: Class<T>,
): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, clazz)
    } else {
        getParcelableExtra(key) as? T
    }
