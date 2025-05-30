package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.CartProduct

interface CartProductRepository {
    fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<CartProduct>>) -> Unit,
    )

    fun getQuantityByProductId(
        productId: Long,
        onResult: (Result<Int?>) -> Unit,
    )

    fun getTotalQuantity(onResult: (Result<Int>) -> Unit)

    fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteByProductId(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )
}
