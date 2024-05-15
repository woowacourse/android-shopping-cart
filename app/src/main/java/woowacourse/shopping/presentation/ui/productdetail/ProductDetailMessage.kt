package woowacourse.shopping.presentation.ui.productdetail

import android.content.Context
import woowacourse.shopping.R
import woowacourse.shopping.presentation.base.MessageType

sealed interface ProductDetailMessage : MessageType {
    data object DefaultErrorMessage : ProductDetailMessage {
        fun toString(context: Context): String = context.getString(R.string.unforeseen_error_message)
    }

    data object NoSuchElementErrorMessage : ProductDetailMessage {
        fun toString(context: Context): String = context.getString(R.string.no_such_element_exception_message)
    }

    data object AddToCartSuccessMessage : ProductDetailMessage {
        fun toString(context: Context): String = context.getString(R.string.add_to_cart_success_message)
    }
}
