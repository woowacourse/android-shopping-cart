package woowacourse.shopping.ui.repository

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import java.util.UUID

object CartRepository {
    private var cart = Cart()

    suspend fun getPartedItem(
        page: Int,
        pageSize: Int,
    ): Products {
        return cart.getPartedItem(page, pageSize)
    }

    fun addProduct(product: Product) {
        cart = cart.addProduct(product)
    }

    fun removeProduct(id: UUID){
        cart = cart.removeProduct(id)
    }

    fun size() = cart.size()
}

private suspend fun Cart.getPartedItem(
    page: Int,
    pageSize: Int,
): Products {
    return products.getPartedItem(page, pageSize)
}