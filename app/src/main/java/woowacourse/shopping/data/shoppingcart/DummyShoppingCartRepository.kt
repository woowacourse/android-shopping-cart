package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Page

class DummyShoppingCartRepository(val dummyCartProducts: List<CartProduct>) : ShoppingCartRepository {
    override fun getOrNull(
        id: Int,
        onResult: (CartProduct?) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun getAll(onSuccess: (List<CartProduct>) -> Unit) {
        onSuccess(dummyCartProducts)
    }

    override fun getTotalCount(onResult: (Int) -> Unit) {
        onResult(dummyCartProducts.size)
    }

    override fun getPage(
        pageSize: Int,
        requestedIndex: Int,
        onSuccess: (Page<CartProduct>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun insert(cartProduct: CartProduct) {
        TODO("Not yet implemented")
    }

    override fun delete(cartProduct: CartProduct) {
        TODO("Not yet implemented")
    }
}
