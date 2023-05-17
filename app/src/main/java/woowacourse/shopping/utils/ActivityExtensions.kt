package woowacourse.shopping.utils

import android.app.Activity
import android.util.Log

fun Activity.keyError(key: String) {
    Log.e("Intent", "Intent에 $key 키가 없습니다.")
    finish()
}
