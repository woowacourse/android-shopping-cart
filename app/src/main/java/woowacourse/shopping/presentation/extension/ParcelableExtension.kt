package woowacourse.shopping.presentation.extension

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle?.getParcelableCompat(key: String): T =
    this.getParcelableCompatInternal(key) { k ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this?.getParcelable(k, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            this?.getParcelable(k) as? T
        }
    }

inline fun <T : Parcelable> Any?.getParcelableCompatInternal(
    key: String,
    getter: (String) -> T?,
): T {
    requireNotNull(this) { "Intent 또는 Bundle이 null입니다." }
    return requireNotNull(getter(key)) { "$key 키에 해당하는 Parcelable 값이 존재하지 않습니다." }
}
