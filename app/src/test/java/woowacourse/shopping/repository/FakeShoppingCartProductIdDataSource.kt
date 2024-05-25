package woowacourse.shopping.repository

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource

class FakeShoppingCartProductIdDataSource(
    private val data: MutableList<ProductIdsCountData> = mutableListOf(),
) : ShoppingCartProductIdDataSource {
    override fun findByProductId(productId: Int): ProductIdsCountData? = data.find { it.productId == productId }

    override fun loadAll(): List<ProductIdsCountData> = data

    override fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Int {
        data.add(productIdsCountData)
        return productIdsCountData.productId
    }

    override fun removedProductsId(productId: Int): Int {
        val foundItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(foundItem)
        return foundItem.productId
    }

    override fun plusProductsIdCount(productId: Int) {
        val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(oldItem)
        data.add(oldItem.copy(quantity = oldItem.quantity + 1))
    }

    override fun minusProductsIdCount(productId: Int) {
        val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(oldItem)
        data.add(oldItem.copy(quantity = oldItem.quantity - 1))
    }

    override fun clearAll() {
        data.clear()
    }
}
