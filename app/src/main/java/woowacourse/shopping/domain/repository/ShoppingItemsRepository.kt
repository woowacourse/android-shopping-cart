package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity

interface ShoppingItemsRepository {
    fun insertProducts(products: List<ProductEntity>)

    fun productWithQuantityItem(productId: Long): Result<ProductWithQuantity>

    fun findProductWithQuantityItemsByPage(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductWithQuantity>>
}
