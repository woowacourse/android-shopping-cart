package woowacourse.shopping.app

import android.content.Context
import woowacourse.shopping.R
import woowacourse.shopping.presentation.ui.error.ErrorActivity
import woowacourse.shopping.remote.api.ErrorListener

object AppErrorHandler {
    fun provideErrorListener(context: Context): ErrorListener =
        object : ErrorListener {
            override fun handleNetworkError() {
                val intent =
                    ErrorActivity.getIntent(
                        context,
                        context.getString(R.string.network_error_title),
                        context.getString(R.string.network_error_description),
                    )
                context.startActivity(intent)
            }
        }
}
