package woowacourse.shopping.ui.shopping

import woowacourse.shopping.R

enum class ShoppingViewType(val value: Int) {
    RECENT_PRODUCTS(R.layout.item_recent_product_wrapper),
    PRODUCT(R.layout.item_product),
    MORE_BUTTON(R.layout.item_load_more);

    companion object {
        private const val INVALID_VIEW_TYPE_ERROR_MESSAGE = "올바르지 않은 뷰 타입입니다."

        fun of(value: Int): ShoppingViewType =
            requireNotNull(values().find { it.value == value }) { INVALID_VIEW_TYPE_ERROR_MESSAGE }
    }
}
