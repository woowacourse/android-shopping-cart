package woowacourse.shopping.ui.pagination

import woowacourse.shopping.model.Product

class ProductPageStateHolder(
    products: List<Product>,
) : PageStateHolder<Product>(products) {
    override val pageSize: Int = 20

    override fun getPageRange(): IntRange {
        val exclusiveEndPage = currentPage + 1
        return initialPage..exclusiveEndPage
    }

    fun nextPage() {
        updateCurrentPage(currentPage + 1)
    }

    fun canMoveToNextPage(): Boolean = isInPageRange(currentPage + 1)
}
