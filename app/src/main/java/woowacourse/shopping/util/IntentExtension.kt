package woowacourse.shopping.util

import android.content.Intent
import android.os.Build
import java.io.Serializable

inline fun <reified T : Serializable> Intent.getSerializableCompat(key: String): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java) ?: throw IllegalArgumentException(ERROR_NO_EXTRA_DATA.format(key))
    } else {
        val value = getSerializableExtra(key) as? T
        if (value is T) {
            value
        } else {
            throw IllegalArgumentException(ERROR_NO_EXTRA_DATA.format(key))
        }
    }
}

const val ERROR_NO_EXTRA_DATA = "[Key : %s] 부가 데이터를 찾을 수 없습니다"
