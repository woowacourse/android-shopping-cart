package woowacourse.shopping.data.repository

import android.util.Log
import woowacourse.shopping.data.CartProductDTO
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product

object CartDummyRepositoryImpl : CartRepository {
    private const val COLLECTION_POSITION_OFFSET: Int = 1
    private const val LOAD_ITEM_COUNT: Int = 5
    private const val PAGE_COUNT_OFFSET: Int = 1
    private val _cart: MutableList<CartProductDTO> =
        dummyProducts.map { product -> CartProductDTO(product, 1) }.slice(0..7).toMutableList()

    val cart: List<CartProductDTO>
        get() = _cart.toList()

    override fun fetchCartProducts(page: Int): List<CartProductDTO> =
        _cart
            .drop(((page - COLLECTION_POSITION_OFFSET) * LOAD_ITEM_COUNT).coerceAtLeast(0))
            .take(LOAD_ITEM_COUNT)

    override fun fetchMaxPageCount(): Int =
        if (_cart.size % LOAD_ITEM_COUNT == 0) {
            _cart.size / LOAD_ITEM_COUNT
        } else {
            (_cart.size / LOAD_ITEM_COUNT) + PAGE_COUNT_OFFSET
        }

    override fun fetchAllProduct(): List<CartProductDTO> = cart

    override fun removeCartProduct(id: Int) {
        _cart.removeIf { it.product.id == id }
    }

    override fun clearCart() {
        _cart.clear()
    }

    override fun upsertCartProduct(
        product: Product,
        count: Int,
    ) {
        val cartProduct = _cart.find { it.product.id == product.id }
        if (cartProduct == null) {
            _cart.add(CartProductDTO(product, count))
        } else {
            _cart.remove(cartProduct)
            val newCount = cartProduct.count + count
            if (newCount >= 1) _cart.add(CartProductDTO(cartProduct.product, newCount))
        }

        Log.d("test", _cart.toString())
    }
}
