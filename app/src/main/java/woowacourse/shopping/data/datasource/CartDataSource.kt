package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.Product

interface CartDataSource {
    fun getCartProductCount(onResult: (Result<Int?>) -> Unit)

    fun getCartProducts(onResult: (Result<List<CartEntity>>) -> Unit)

    fun getPagedCartProductIds(
        limit: Int,
        page: Int,
        onResult: (Result<List<Long>>) -> Unit,
    )

    fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    )
}
