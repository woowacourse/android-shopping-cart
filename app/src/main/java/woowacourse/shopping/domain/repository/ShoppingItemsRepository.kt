package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity

interface ShoppingItemsRepository {
    fun insertProducts(products: List<ProductEntity>)

    fun productWithQuantityItem(id: Long): ProductWithQuantity?

    fun findProductWithQuantityItemsByPage(
        page: Int,
        pageSize: Int,
    ): List<ProductWithQuantity>
}
