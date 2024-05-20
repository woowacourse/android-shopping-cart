package woowacourse.shopping.presentation.base

import android.content.Context
import woowacourse.shopping.R

interface MessageProvider {
    data object DefaultErrorMessage : MessageProvider {
        fun getMessage(context: Context): String = context.getString(R.string.unforeseen_error_message)
    }
}
