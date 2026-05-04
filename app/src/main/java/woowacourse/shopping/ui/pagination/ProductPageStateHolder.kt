package woowacourse.shopping.ui.pagination

import woowacourse.shopping.model.Product

class ProductPageStateHolder(
    products: List<Product>,
) : PageStateHolder<Product>(products) {
    override val pageSize: Int = 20

    override fun getPageRange(): IntRange {
        return initialPage..getExclusiveEndPage()
    }

    fun nextPage() {
        updateCurrentPage(currentPage + 1)
    }
}
