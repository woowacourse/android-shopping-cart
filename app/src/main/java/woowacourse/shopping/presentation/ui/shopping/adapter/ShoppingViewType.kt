package woowacourse.shopping.presentation.ui.shopping.adapter

enum class ShoppingViewType(val value: Int, val span: Int) {
    RecentProduct(0, 2),
    Product(1, 1),
    LoadMore(2, 2),
    ;

    companion object {
        fun of(value: Int): ShoppingViewType =
            when (value) {
                0 -> RecentProduct
                1 -> Product
                2 -> LoadMore
                else -> throw IllegalArgumentException("잘못된 viewType 입니다.")
            }
    }
}
