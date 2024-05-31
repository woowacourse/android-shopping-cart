package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.domain.model.toDomain

class DefaultProductIdsCountRepository(
    private val productsIdsCountDataSource: ShoppingCartProductIdDataSource,
) : ProductIdsCountRepository {
    override fun findByProductId(productId: Long): ProductIdsCount =
        productsIdsCountDataSource.findByProductId(productId)?.toDomain() ?: throw NoSuchElementException()

    override fun findByProductIdAsync(
        productId: Long,
        callback: (ProductIdsCount) -> Unit,
    ) {
        productsIdsCountDataSource.findByProductIdAsync(productId) {
            callback(it?.toDomain() ?: throw NoSuchElementException())
        }
    }
}
