package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.data.ShoppingItemsDataSource
import woowacourse.shopping.domain.model.Product

class FakeShoppingItemsDataSource(private val products: List<Product>?, private val throwError: Boolean = false) :
    ShoppingItemsDataSource {
    override fun getProductItem(id: Long): Product? {
        if (throwError) throw IllegalArgumentException("Error")
        return products?.firstOrNull { it.id == id }
    }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        if (throwError) throw IllegalArgumentException("Error")
        if (products == null) return emptyList()
        val fromIndex = page * pageSize
        val toIndex = kotlin.math.min(fromIndex + pageSize, products.size)
        return if (fromIndex < products.size) {
            products.subList(fromIndex, toIndex)
        } else {
            emptyList()
        }
    }
}
