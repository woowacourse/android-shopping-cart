package woowacourse.shopping.ui.pagination

import woowacourse.shopping.model.Product

class ProductPageStateHolder(
    products: List<Product>,
    initialPage: Int = 0,
) : PageStateHolder<Product>(products) {
    init {
        restoreCurrentPage(initialPage)
    }

    override val pageItemSize: Int = 20

    override fun getPageRange(): IntRange {
        return initialPage..getExclusiveEndPage()
    }

    fun nextPage() {
        updateCurrentPage(currentPage + 1)
    }

    fun restoreCurrentPage(page: Int) {
        updateCurrentPage(page)
    }
}
