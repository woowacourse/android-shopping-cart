package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.CartEntity
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> = cartDao.getAllCartItems().map { items ->
        items.map { it.toDomain() }
    }

    override fun getCartItem(productId: String): Flow<CartItem?> =
        cartDao.getCartItem(productId).map { it?.toDomain() }

    override suspend fun updateCart(cartItem: CartItem) {
        cartDao.upsert(cartItem.toEntity())
    }

    override suspend fun deleteCartItem(productId: String) {
        cartDao.deleteCartItem(productId)
    }

    override suspend fun increaseCartItemQuantity(productId: String) {
        val cartItem = getCartItem(productId).first() ?: return
        updateCart(cartItem.copy(quantity = Quantity(cartItem.quantity.value + 1)))
    }

    override suspend fun decreaseCartItemQuantity(productId: String) {
        val cartItem = getCartItem(productId).first() ?: return
        if (cartItem.quantity.value > 1) {
            updateCart(cartItem.copy(quantity = Quantity(cartItem.quantity.value - 1)))
        }
    }

    override suspend fun getCartItemCount(): Int = cartDao.getCartItemCount()

    override suspend fun getPagingCartItems(page: Int, pageSize: Int): List<CartItem> {
        val offset = page * pageSize
        return cartDao.getPagingCartItems(pageSize, offset).map { it.toDomain() }
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
