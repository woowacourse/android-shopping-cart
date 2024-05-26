package woowacourse.shopping.source

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource

class FakeShoppingCartProductIdDataSource(
    private val data: MutableList<ProductIdsCountData> = mutableListOf(),
) : ShoppingCartProductIdDataSource {
    override fun findByProductId(productId: Long): ProductIdsCountData? = data.find { it.productId == productId }

    override fun loadPaged(page: Int): List<ProductIdsCountData> {
        TODO("Not yet implemented")
    }

    override fun loadAll(): List<ProductIdsCountData> = data

    override fun isFinalPage(page: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Long {
        data.add(productIdsCountData)
        return productIdsCountData.productId
    }

    override fun removedProductsId(productId: Long): Long {
        val foundItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(foundItem)
        return foundItem.productId
    }

    override fun plusProductsIdCount(productId: Long) {
        val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(oldItem)
        data.add(oldItem.copy(quantity = oldItem.quantity + 1))
    }

    override fun minusProductsIdCount(productId: Long) {
        val oldItem = data.find { it.productId == productId } ?: throw NoSuchElementException()
        data.remove(oldItem)
        data.add(oldItem.copy(quantity = oldItem.quantity - 1))
    }

    override fun clearAll() {
        data.clear()
    }
}
