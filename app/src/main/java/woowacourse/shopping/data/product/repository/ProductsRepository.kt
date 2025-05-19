package woowacourse.shopping.data.product.repository

import woowacourse.shopping.domain.product.Product

interface ProductsRepository {
    val loadable: Boolean

    fun load(
        lastProductId: Long?,
        size: Int,
        result: (Result<List<Product>>) -> Unit,
    )
}
