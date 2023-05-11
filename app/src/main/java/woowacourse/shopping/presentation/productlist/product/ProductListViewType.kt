package woowacourse.shopping.presentation.productlist.product

enum class ProductListViewType(val number: Int) {
    RECENT_PRODUCT(0),
    PRODUCT(1),
    MORE_ITEM(2),
    ;

    companion object {
        fun find(number: Int): ProductListViewType {
            return values().find { it.number == number } ?: throw IllegalArgumentException(
                VIEW_TYPE_ERROR,
            )
        }

        private const val VIEW_TYPE_ERROR = "잘못된 viewtype 입니다."
    }
}
