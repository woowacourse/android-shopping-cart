package woowacourse.shopping.source

import woowacourse.shopping.data.ProductIdsCountData

class DummyProductIdsCountDataSource : ProductIdsCountDataSource {
    override fun findByProductId(productId: Int): ProductIdsCountData =
        productIdsCount.find { it.productId == productId }
            ?: throw NoSuchElementException("there is no productId: $productId in DummyProductIdsCountDataSource")

    override fun loadAll(): List<ProductIdsCountData> = productIdsCount

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
