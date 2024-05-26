package woowacourse.shopping.presentation.ui.shopping.adapter

enum class ShoppingViewType(val value: Int, val span: Int) {
    Product(0, 1),
    LoadMore(1, 2)
    ;

    companion object {
        fun of(value: Int): ShoppingViewType =
            when (value) {
                0 -> Product
                1 -> LoadMore
                else -> throw IllegalArgumentException("잘못된 viewType입니다.")
            }
    }
}
