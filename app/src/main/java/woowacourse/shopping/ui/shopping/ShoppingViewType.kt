package woowacourse.shopping.ui.shopping

import woowacourse.shopping.R

enum class ShoppingViewType(val value: Int) {
    RECENT_PRODUCTS(R.layout.item_recent_product_wrapper),
    PRODUCT(R.layout.item_product),
    MORE_BUTTON(R.layout.item_button_more);

    companion object {
        private const val WRONG_KEY_ERROR = "검색한 뷰타입이 존재하지 않습니다."

        fun of(value: Int): ShoppingViewType =
            requireNotNull(values().find { it.value == value }) { WRONG_KEY_ERROR }
    }
}
