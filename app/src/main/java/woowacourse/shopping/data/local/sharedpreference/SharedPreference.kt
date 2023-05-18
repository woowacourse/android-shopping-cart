package woowacourse.shopping.data.local.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences("shopping", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreference.getString(key, null)
    }

    fun putInt(key: String, value: Int) {
        sharedPreference.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreference.getInt(key, 0)
    }
}
