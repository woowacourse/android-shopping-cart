package woowacourse.shopping.util

import android.app.Activity
import android.util.Log

fun Activity.keyError(key: String) {
    Log.d("hash", "$key is not exist...")
    finish()
}
