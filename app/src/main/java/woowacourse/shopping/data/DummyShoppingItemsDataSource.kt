package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product

class DummyShoppingItemsDataSource : ShoppingItemsDataSource {
    private val items: List<Product> = DummyShoppingItems.items

    override fun getProductItem(id: Long): Product {
        return items.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("데이터가 존재하지 않습니다.")
    }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = kotlin.math.min(fromIndex + pageSize, items.size)
        return if (fromIndex < items.size) {
            items.subList(fromIndex, toIndex)
        } else {
            emptyList()
        }
    }

    companion object {
        private var instance: ShoppingItemsDataSource? = null

        fun getInstance(): ShoppingItemsDataSource {
            return instance ?: synchronized(this) {
                DummyShoppingItemsDataSource().also { instance = it }
            }
        }
    }
}
