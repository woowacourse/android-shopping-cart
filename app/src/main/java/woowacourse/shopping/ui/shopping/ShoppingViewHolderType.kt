package woowacourse.shopping.ui.shopping

import woowacourse.shopping.R

enum class ShoppingViewHolderType(val value: Int) {
    RECENT_PRODUCTS(R.layout.item_recent_products),
    PRODUCT(R.layout.item_shopping);

    companion object {
        private const val INVALID_VIEW_HOLDER_TYPE_ERROR = "뷰타입이 존재하지 않습니다"

        fun getName(value: Int): ShoppingViewHolderType =
            ShoppingViewHolderType.values().find { it.value == value }
                ?: throw IllegalArgumentException(INVALID_VIEW_HOLDER_TYPE_ERROR)
    }
}
