package woowacourse.shopping.presentation.ui.productlist

import android.content.Context
import woowacourse.shopping.R
import woowacourse.shopping.presentation.base.MessageType

sealed interface ProductListMessage : MessageType {
    data object DefaultErrorMessage : ProductListMessage {
        fun toString(context: Context): String = context.getString(R.string.unforeseen_error_message)
    }
}
