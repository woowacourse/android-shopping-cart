package woowacourse.shopping.data.remote

import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class RemoteShoppingItemsRepository(private val productApiService: ProductApiService) :
    ShoppingItemsRepository {
    override fun insertProducts(products: List<ProductEntity>) {
        // No-op
    }

    override fun productWithQuantityItem(id: Long): ProductWithQuantity? {
        return runCatching {
            val product = productApiService.loadById(id)
            ProductWithQuantity(product.toProductEntity(), 1)
        }.getOrNull()
    }

    override fun findProductWithQuantityItemsByPage(
        page: Int,
        pageSize: Int,
    ): List<ProductWithQuantity> {
        return runCatching {
            productApiService.load(page, pageSize).map { ProductWithQuantity(it.toProductEntity(), 1) }
        }.getOrElse { emptyList() }
    }
}
