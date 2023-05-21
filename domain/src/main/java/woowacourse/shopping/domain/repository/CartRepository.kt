package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

interface CartRepository {
    fun addCartProduct(cartProduct: CartProduct)

    fun getAll(): Cart

    fun getAllCount(): Int

    fun getPage(page: Int, sizePerPage: Int): Cart

    fun deleteCartProduct(cartProduct: CartProduct)

    fun getTotalAmount(): Int

    fun getCartProductByProduct(product: Product): CartProduct?

    fun modifyCartProduct(cartProduct: CartProduct)

    fun getTotalPrice(): Int

    fun replaceCartProduct(prev: CartProduct, new: CartProduct)

    fun isAllCheckedInPage(page: Int, sizePerPage: Int): Boolean
}
