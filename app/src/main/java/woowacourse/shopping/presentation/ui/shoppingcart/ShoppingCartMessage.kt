package woowacourse.shopping.presentation.ui.shoppingcart

import android.content.Context
import woowacourse.shopping.R
import woowacourse.shopping.presentation.base.MessageType

sealed interface ShoppingCartMessage : MessageType {
    data object DefaultErrorMessage : ShoppingCartMessage {
        fun toString(context: Context): String = context.getString(R.string.unforeseen_error_message)
    }
}
