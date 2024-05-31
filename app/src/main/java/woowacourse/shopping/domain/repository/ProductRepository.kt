package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity

interface ProductRepository {
    fun insertProducts(products: List<ProductEntity>)

    fun getProductWithQuantity(productId: Long): Result<ProductWithQuantity>

    fun getProductsByPage(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductWithQuantity>>
}
