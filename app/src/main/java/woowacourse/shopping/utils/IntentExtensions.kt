package woowacourse.shopping.utils

import android.content.Intent
import android.os.Build
import java.io.Serializable

fun <T : Serializable> Intent.intentSerializable(
    key: String,
    clazz: Class<T>,
): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, clazz)
    } else {
        @Suppress("DEPRECATION")
        getSerializableExtra(key) as T?
    }
