package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.domain.model.toData
import woowacourse.shopping.domain.model.toDomain

class DefaultProductIdsCountRepository(
    private val productsIdsCountDataSource: ShoppingCartProductIdDataSource,
) : ProductIdsCountRepository {
    override fun findByProductId(productId: Int): ProductIdsCount =
        productsIdsCountDataSource.findByProductId(productId)?.toDomain() ?: throw NoSuchElementException(
            "no such product id $productId in shopping cart repository",
        )

    override fun loadAllProductIdsCounts(): List<ProductIdsCount> =
        productsIdsCountDataSource.loadAll().map {
            it.toDomain()
        }

    override fun addedProductsId(productIdsCount: ProductIdsCount): Int =
        productsIdsCountDataSource.addedNewProductsId(productIdsCount.toData())

    override fun removedProductsId(productId: Int): Int = productsIdsCountDataSource.removedProductsId(productId)

    override fun plusProductsIdCount(productId: Int) {
        val foundProductsIdCount =
            productsIdsCountDataSource.findByProductId(productId)?.toDomain() ?: throw NoSuchElementException(
                "no such product id $productId in shopping cart repository",
            )
        productsIdsCountDataSource.plusProductsIdCount(foundProductsIdCount.productId)
    }

    override fun minusProductsIdCount(productId: Int) {
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
