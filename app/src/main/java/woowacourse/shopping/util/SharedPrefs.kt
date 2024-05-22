package woowacourse.shopping.util

import android.content.Context

class SharedPrefs(context: Context) {
    private val sharedPrefs =
        context.getSharedPreferences(
            RECENT_VIEWED_PRODUCT,
            Context.MODE_PRIVATE,
        )

    companion object {
        private const val RECENT_VIEWED_PRODUCT = "recent_viewed_product"
    }
}
