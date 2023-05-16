package woowacourse.shopping.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun setLastProductId(productId: Int) {
        prefs.edit().putInt(KEY_LAST_PRODUCT, productId).apply()
    }

    fun getLastProductId(): Int {
        return prefs.getInt(KEY_LAST_PRODUCT, -1)
    }

    companion object {
        private const val PREFS_FILENAME = "prefs"
        private const val KEY_LAST_PRODUCT = "KEY_LAST_PRODUCT"
    }
}
