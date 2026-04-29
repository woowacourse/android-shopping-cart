package woowacourse.shopping.ui.pagination

import woowacourse.shopping.model.Product

class ProductPaginationStateHolder(
    products: List<Product>,
) : PaginationStateHolder<Product>(products) {
    override val pageSize: Int = 3

    override fun getPageRange(): IntRange {
        val exclusiveEndPage = currentPage + 1
        return initialPage..exclusiveEndPage
    }

    fun nextPage() {
        updateCurrentPage(currentPage + 1)
    }
}
