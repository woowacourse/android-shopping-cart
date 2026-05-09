package woowacourse.shopping.data.cart

import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.repository.ProductRepository

class CartRepositoryRoomImpl(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository,
) : CartRepository {
    override suspend fun getCart(): Cart {
        val cartEntities = cartDao.getAll()
        val cartItems =
            cartEntities.mapNotNull { entity ->
                // map 대신 mapNotNull 사용
                val product =
                    try {
                        productRepository.getProduct(entity.productId)
                    } catch (e: Exception) {
                        null // 상품을 못 찾으면 null 반환
                    }

                if (product != null) {
                    CartItem(product, CartItemQuantity(entity.quantity))
                } else {
                    null // 상품 정보가 없으면 이 항목은 스킵
                }
            }
        return Cart(CartItems(cartItems))
    }

    override suspend fun getTotalCartCount(): Int = cartDao.getCartCount()

    override suspend fun getTotalCartItemCount(): Int = cartDao.getTotalQuantity() ?: 0

    override suspend fun getQuantity(cartItem: CartItem): Int = cartDao.getQuantity(cartItem.product.id) ?: 0

    override suspend fun addCartItem(
        cartItem: CartItem,
        targetQuantity: Int,
    ) {
        var currentQuantity = getQuantity(cartItem)
        val newQuantity = currentQuantity + targetQuantity
        cartDao.insert(
            cartItem
                .copy(
                    quantity = CartItemQuantity(newQuantity),
                ).toEntity(),
        )
    }

    override suspend fun minusCartItem(
        cartItem: CartItem,
        targetQuantity: Int,
    ) {
        var currentQuantity = getQuantity(cartItem)
        val newQuantity = currentQuantity - targetQuantity
        if (newQuantity <= 0) {
            removeCartItem(cartItem)
        } else {
            cartDao.updateQuantity(cartItem.product.id, newQuantity)
        }
    }

    override suspend fun removeCartItem(cartItem: CartItem) {
        cartDao.delete(cartItem.toEntity())
    }

    override suspend fun isCartItemExist(cartItem: CartItem): Boolean = cartDao.isExist(cartItem.product.id)
}

fun CartItem.toEntity(): CartEntity =
    CartEntity(
        productId = product.id,
        quantity = quantity.value,
    )
