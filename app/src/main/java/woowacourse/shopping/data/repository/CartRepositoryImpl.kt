package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.CartEntity
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.cart.CartItems
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {

    override fun getCartItems(): Flow<CartItems> = cartDao.getAllCartItems().map { items ->
        CartItems(items.map { it.toDomain() })
    }

    override fun getCartItem(productId: String): Flow<CartItem?> =
        cartDao.getCartItem(productId).map { it?.toDomain() }

    override fun updateCart(cartItem: CartItem) {
        cartDao.upsert(cartItem.toEntity())
    }

    override fun deleteCartItem(productId: String) {
        cartDao.deleteCartItem(productId)
    }

    override fun increaseCartItemQuantity(productId: String) {
        val cartItem = runBlocking { getCartItem(productId).first() } ?: return
        updateCart(cartItem.copy(quantity = Quantity(cartItem.quantity.value + 1)))
    }

    override fun decreaseCartItemQuantity(productId: String) {
        val cartItem = runBlocking { getCartItem(productId).first() } ?: return
        if (cartItem.quantity.value > 1) {
            updateCart(cartItem.copy(quantity = Quantity(cartItem.quantity.value - 1)))
        }
    }

    override fun getCartItemCount(): Int = cartDao.getCartItemCount()

    override fun getPagingCartItems(page: Int, pageSize: Int): CartItems {
        val offset = page * pageSize
        return CartItems(
            cartDao.getPagingCartItems(pageSize, offset).map { it.toDomain() }
        )
    }

    override fun saveCartItems(cartItems: CartItems) {
        cartItems.items.forEach { updateCart(it) }
    }

    private fun CartEntity.toDomain(): CartItem = CartItem(
        product = Product(
            id = productId,
            productTitle = ProductTitle(productName),
            imageUrl = productImageUrl,
            price = Price(price)
        ),
        quantity = Quantity(quantity)
    )

    private fun CartItem.toEntity(): CartEntity = CartEntity(
        productId = product.id,
        productName = product.productTitle.value,
        productImageUrl = product.imageUrl,
        price = product.price.value,
        quantity = quantity.value
    )
}
