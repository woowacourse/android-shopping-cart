package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.CartProductEntity

class FakeCartProductRepositoryImpl : CartProductRepository {
    val cartProducts: MutableList<CartProductEntity> = mutableListOf()

    override fun insertCartProduct(cartProduct: CartProductEntity) {
        cartProducts.add(cartProduct)
    }

    override fun deleteCartProduct(cartProduct: CartProductEntity) {
        cartProducts.remove(cartProduct)
    }

    override fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
        callback: (List<CartProductEntity>) -> Unit,
    ) {
        val safeEndIndex = minOf(endIndex, cartProducts.size)
        val safeStartIndex = minOf(startIndex, safeEndIndex)

        callback(cartProducts.subList(safeStartIndex, safeEndIndex))
    }

    override fun updateProduct(
        cartProduct: CartProductEntity,
        diff: Int,
        callback: (CartProductEntity?) -> Unit,
    ) {
        val index: Int = cartProducts.indexOf(cartProduct)
        val product: CartProductEntity = cartProducts[index]
        cartProducts[index] = product.copy(quantity = product.quantity + diff)
        callback(cartProducts[index])
    }

    override fun getProductQuantity(
        id: Int,
        callback: (Int?) -> Unit,
    ) {
        val index: Int = cartProducts.indexOfFirst { it.uid == id }
        val quantity: Int = cartProducts[index].quantity
        callback(quantity)
    }

    override fun getAllProductsSize(callback: (Int) -> Unit) {
        callback(cartProducts.size)
    }

    override fun getCartItemSize(callback: (Int) -> Unit) {
        val cartItemSize = cartProducts.sumOf { it.quantity }
        callback(cartItemSize)
    }
}
