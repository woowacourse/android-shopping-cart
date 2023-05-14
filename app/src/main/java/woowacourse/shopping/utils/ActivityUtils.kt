package woowacourse.shopping.utils

import android.app.Activity
import android.util.Log

object ActivityUtils {
    fun keyError(Activity: Activity, key: String) {
        Log.e("Intent", "Intent에 $key 키가 없습니다.")
        Activity.finish()
    }
}
