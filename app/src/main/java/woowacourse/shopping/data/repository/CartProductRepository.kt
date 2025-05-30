package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.product.catalog.ProductUiModel

interface CartProductRepository {
    fun insertCartProduct(cartProduct: CartProductEntity)

    fun deleteCartProduct(productId: Int)

    fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
        callback: (List<CartProductEntity>) -> Unit,
    )

    fun updateProductQuantity(
        productId: Int,
        diff: Int,
    )

    fun getProduct(
        id: Int,
        callback: (CartProductEntity) -> Unit
    )

    fun getProductQuantity(
        id: Int,
        callback: (Int?) -> Unit,
    )

    fun getAllProductsSize(callback: (Int) -> Unit)

    fun getCartItemSize(callback: (Int) -> Unit)
}
