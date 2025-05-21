package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.Product

interface CartDataSource {
    fun getCartProductCount(onResult: (Result<Int?>) -> Unit)

    fun getCartProducts(onResult: (Result<List<CartEntity>>) -> Unit)

    fun getPagedCartProductIds(
        limit: Int,
        page: Int,
        onResult: (Result<List<Long>>) -> Unit,
    )

    fun increaseQuantity(productId: Long)

    fun decreaseQuantity(productId: Long)

    fun insertProduct(product: Product)

    fun deleteProduct(productId: Long)
}
