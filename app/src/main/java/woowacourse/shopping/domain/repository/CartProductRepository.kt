package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.CartProduct

interface CartProductRepository {
    fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (PagedResult<CartProduct>) -> Unit,
    )

    fun getQuantityByProductId(
        productId: Long,
        onSuccess: (Int?) -> Unit,
    )

    fun getTotalQuantity(onSuccess: (Int) -> Unit)

    fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
        onSuccess: () -> Unit,
    )

    fun deleteByProductId(
        productId: Long,
        onSuccess: () -> Unit,
    )
}
