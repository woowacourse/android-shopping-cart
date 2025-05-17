package woowacourse.shopping.presentation

import android.content.Intent
import android.os.Build
import java.io.Serializable

inline fun <reified T : Serializable> Intent.getSerializableExtraCompat(key: String): T =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
            ?: error(ERROR_NO_DATA.format(key))
    } else {
        @Suppress("DEPRECATION")
        getSerializableExtra(key) as? T
            ?: error(ERROR_NO_DATA.format(key))
    }

const val ERROR_NO_DATA = "%s 데이터를 찾을 수 없습니다."
