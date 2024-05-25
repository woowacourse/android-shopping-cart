package woowacourse.shopping.data.source

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.data.model.ProductIdsCountData

class DummyShoppingCartProductIdDataSource(
    private val pagingStrategy: NumberPagingStrategy<ProductIdsCountData> = NumberPagingStrategy(5),
) : ShoppingCartProductIdDataSource {
    override fun findByProductId(productId: Int): ProductIdsCountData? = productIdsCount.find { it.productId == productId }

    override fun loadAll(): List<ProductIdsCountData> = productIdsCount

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, productIdsCount)

    override fun loadPaged(page: Int): List<ProductIdsCountData> = pagingStrategy.loadPagedData(page, productIdsCount)

    override fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Int {
        productIdsCount.add(productIdsCountData)
        return productIdsCountData.productId
    }

    override fun removedProductsId(productId: Int): Int {
        val foundItem = productIdsCount.find { it.productId == productId } ?: throw NoSuchElementException()
        productIdsCount.remove(foundItem)
        return foundItem.productId
    }

    override fun plusProductsIdCount(productId: Int) {
        val oldItem = productIdsCount.find { it.productId == productId } ?: throw NoSuchElementException()
        productIdsCount.remove(oldItem)
        productIdsCount.add(oldItem.copy(quantity = oldItem.quantity + 1))
    }

    override fun minusProductsIdCount(productId: Int) {
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
