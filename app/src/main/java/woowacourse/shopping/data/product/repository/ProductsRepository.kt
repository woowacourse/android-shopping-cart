package woowacourse.shopping.data.product.repository

import woowacourse.shopping.domain.product.Product

interface ProductsRepository {
    fun load(
        lastProductId: Long?,
        size: Int,
        result: (Result<List<Product>>) -> Unit,
    )
}
