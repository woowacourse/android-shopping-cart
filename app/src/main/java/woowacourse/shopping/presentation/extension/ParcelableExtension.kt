package woowacourse.shopping.presentation.extension

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle?.getParcelableCompat(key: String): T {
    requireNotNull(this) { "Bundle이 null입니다." }

    val value =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            this.getParcelable(key) as? T
        }

    return requireNotNull(value) { "$key 키에 해당하는 ${T::class.java.simpleName} 값이 존재하지 않습니다." }
}
