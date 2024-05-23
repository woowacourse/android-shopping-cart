package woowacourse.shopping.data.shopping.product

import woowacourse.shopping.domain.entity.Product

interface ProductDataSource {
    fun products(
        currentPage: Int,
        size: Int,
    ): Result<ProductPageData>

    fun productById(id: Long): Result<Product>

    fun canLoadMore(
        page: Int,
        size: Int,
    ): Result<Boolean>
}
