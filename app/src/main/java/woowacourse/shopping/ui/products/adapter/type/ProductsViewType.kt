package woowacourse.shopping.ui.products.adapter.type

import java.lang.IllegalArgumentException

enum class ProductsViewType(val type: Int, val span: Int) {
    RECENT_PRODUCTS(0, 2),
    PRODUCTS_UI_MODEL(1, 1),
    ;

    companion object {
        private const val INVALID_VIEW_TYPE_MESSAGE = "일치하는 뷰 타입이 존재하지 않습니다."

        fun from(viewType: Int): ProductsViewType {
            return ProductsViewType.entries.find { it.type == viewType }
                ?: throw IllegalArgumentException(INVALID_VIEW_TYPE_MESSAGE)
        }
    }
}
