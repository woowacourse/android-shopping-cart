package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.domain.model.toData
import woowacourse.shopping.domain.model.toDomain

class DefaultProductIdsCountRepository(
    private val productsIdsCountDataSource: ShoppingCartProductIdDataSource,
) : ProductIdsCountRepository {
    override fun findByProductId(productId: Long): ProductIdsCount =
        productsIdsCountDataSource.findByProductId(productId)?.toDomain() ?: throw NoSuchElementException()

    override fun loadAllProductIdsCounts(): List<ProductIdsCount> =
        productsIdsCountDataSource.loadAll().map {
            it.toDomain()
        }

    override fun addedProductsId(productIdsCount: ProductIdsCount): Long =
        productsIdsCountDataSource.addedNewProductsId(productIdsCount.toData())

    override fun removedProductsId(productId: Long): Long = productsIdsCountDataSource.removedProductsId(productId)

    override fun plusProductsIdCount(productId: Long) {
        val foundProductsIdCount =
            productsIdsCountDataSource.findByProductId(productId)?.toDomain() ?: throw NoSuchElementException()
        productsIdsCountDataSource.plusProductsIdCount(foundProductsIdCount.productId)
    }

    override fun minusProductsIdCount(productId: Long) {
        val foundProductsIdCount = findByProductId(productId)
        if (foundProductsIdCount.quantity == 1) {
            productsIdsCountDataSource.removedProductsId(foundProductsIdCount.productId)
            return
        }
        productsIdsCountDataSource.minusProductsIdCount(foundProductsIdCount.productId)
    }

    override fun clearAll() {
        productsIdsCountDataSource.clearAll()
    }
}
