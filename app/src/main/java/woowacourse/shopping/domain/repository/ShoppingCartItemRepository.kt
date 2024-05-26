package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductData

interface ShoppingCartItemRepository {
    fun addCartItem(product: ProductData): Long

    fun findById(id: Long): ProductData

    fun loadPagedCartItems(page: Int): List<ProductData>

    fun removeCartItem(productId: Long): ProductData

    fun clearAllCartItems()

    fun isFinalPage(page: Int): Boolean
}
