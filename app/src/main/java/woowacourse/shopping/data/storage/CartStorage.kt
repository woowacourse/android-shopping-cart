package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.Product
import woowacourse.shopping.uimodel.CartItem

interface CartStorage {
    fun getAllProductsSize(onResult: (Int) -> Unit)

    fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<CartItem>) -> Unit,
    )

    fun addProduct(product: Product)

    fun deleteProduct(cartItemId: Long)
}
