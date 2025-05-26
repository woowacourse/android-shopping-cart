package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Page

interface ShoppingCartRepository {
    fun getOrNull(
        id: Int,
        onResult: (CartProduct?) -> Unit,
    )

    fun getAll(onSuccess: (List<CartProduct>) -> Unit)

    fun getTotalCount(onResult: (Int) -> Unit)

    fun getPage(
        pageSize: Int,
        requestedIndex: Int,
        onSuccess: (Page<CartProduct>) -> Unit,
    )

    fun insert(cartProduct: CartProduct)

    fun delete(cartProduct: CartProduct)

    fun clear()
}
