package woowacourse.shopping.data.source

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.data.model.ProductIdsCountData

class DummyShoppingCartProductIdDataSource(
    private val pagingStrategy: NumberPagingStrategy<ProductIdsCountData> = NumberPagingStrategy(5),
) : ShoppingCartProductIdDataSource {
    override fun findByProductId(productId: Long): ProductIdsCountData? = productIdsCount.find { it.productId == productId }

    override fun loadAll(): List<ProductIdsCountData> = productIdsCount

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, productIdsCount)

    override fun loadPaged(page: Int): List<ProductIdsCountData> = pagingStrategy.loadPagedData(page, productIdsCount)

    override fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Long {
        productIdsCount.add(productIdsCountData)
        return productIdsCountData.productId
    }

    override fun removedProductsId(productId: Long): Long {
        val foundItem = productIdsCount.find { it.productId == productId } ?: throw NoSuchElementException()
        productIdsCount.remove(foundItem)
        return foundItem.productId
    }

    override fun plusProductsIdCount(productId: Long) {
        val oldItem = productIdsCount.find { it.productId == productId } ?: throw NoSuchElementException()
        productIdsCount.remove(oldItem)
        productIdsCount.add(oldItem.copy(quantity = oldItem.quantity + 1))
    }

    override fun minusProductsIdCount(productId: Long) {
        val oldItem = productIdsCount.find { it.productId == productId } ?: throw NoSuchElementException()
        productIdsCount.remove(oldItem)
        productIdsCount.add(oldItem.copy(quantity = oldItem.quantity - 1))
    }

    override fun clearAll() {
        productIdsCount.clear()
    }

    companion object {
        private val productIdsCount = mutableListOf<ProductIdsCountData>()
    }
}
