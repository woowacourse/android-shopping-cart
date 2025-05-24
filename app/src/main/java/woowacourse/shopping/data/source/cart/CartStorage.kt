package woowacourse.shopping.data.source.cart

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.TableCartItem

interface CartStorage {
    fun getAllProductsSize(onResult: (Int) -> Unit)

    fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<TableCartItem>) -> Unit,
    )

    fun addProduct(
        product: Product,
        count: Int,
    )

    fun deleteProduct(cartItemId: Long)
}
