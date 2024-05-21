package woowacourse.shopping.presentation.ui.productdetail

import android.content.Context
import woowacourse.shopping.R
import woowacourse.shopping.presentation.base.MessageProvider

sealed interface ProductDetailMessage : MessageProvider {
    data object NoSuchElementErrorMessage : ProductDetailMessage {
        fun getMessage(context: Context): String = context.getString(R.string.no_such_element_exception_message)
    }

    data object AddToCartSuccessMessage : ProductDetailMessage {
        fun getMessage(context: Context): String = context.getString(R.string.add_to_cart_success_message)
    }
}
