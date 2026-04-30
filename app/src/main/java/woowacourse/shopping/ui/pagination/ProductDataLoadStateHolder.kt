package woowacourse.shopping.ui.pagination

import woowacourse.shopping.model.Product

class ProductDataLoadStateHolder(
    products: List<Product>,
) : DataLoadStateHolder<Product>(products) {
    override val pageSize: Int = 20

    override fun getPageRange(): IntRange {
        val exclusiveEndPage = currentPage + 1
        return initialPage..exclusiveEndPage
    }

    fun nextPage() {
        updateCurrentPage(currentPage + 1)
    }
}
