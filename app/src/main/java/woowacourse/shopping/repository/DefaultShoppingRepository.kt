package woowacourse.shopping.repository

import woowacourse.shopping.data.datasource.CartItemDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.entity.ShoppingCartItemEntity
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem

class DefaultShoppingRepository(
    private val cartItemDataSource: CartItemDataSource,
    private val productDataSource: ProductDataSource,
) : ShoppingRepository {
    override fun shoppingCart(): ShoppingCart =
        ShoppingCart(
            cartItemDataSource.shoppingCartItems()
                .map {
                    it.toShoppingCartItem(
                        productDataSource.productById(it.productId).toProduct(),
                    )
                },
        )

    override fun shoppingCartItems(
        page: Int,
        pageSize: Int,
    ): List<ShoppingCartItem> {
        val fromIndex = page * pageSize
        return cartItemDataSource.shoppingCartItems(pageSize, fromIndex)
            .map {
                it.toShoppingCartItem(
                    productDataSource.productById(it.productId).toProduct(),
                )
            }
    }

    override fun cartItemByProductId(productId: Long): ShoppingCartItem {
        val product = productDataSource.productById(productId).toProduct()
        val cartItem = cartItemDataSource.cartItemById(productId)

        return cartItem?.toShoppingCartItem(product) ?: error("$productId 에 해당하는 장바구니 아이템이 없습니다.")
    }

    override fun cartItemsByProductIds(productIds: List<Long>): List<ShoppingCartItem> {
        val cartItems = mutableListOf<ShoppingCartItem>()

        productIds.forEach { productId ->
            val cartItem = cartItemDataSource.cartItemById(productId) ?: error("$productId 에 해당하는 장바구니 아이템이 없습니다.")
            cartItems.add(
                cartItem.toShoppingCartItem(
                    productDataSource.productById(productId).toProduct(),
                ),
            )
        }
        return cartItems
    }

    override fun shoppingCartItemByPosition(
        currentPage: Int,
        pageSize: Int,
        position: Int,
    ): ShoppingCartItem {
        val items = shoppingCartItems(currentPage, pageSize)
        return items.elementAt(position)
    }

    override fun deleteShoppingCartItem(productId: Long) {
        cartItemDataSource.delete(productId)
    }

    override fun shoppingCartSize(): Int = cartItemDataSource.itemCount()

    override fun updateCartItem(updateCartItem: ShoppingCartItem) {
        cartItemDataSource.updateTotalQuantity(
            updateCartItem.product.id,
            updateCartItem.totalQuantity,
        )
    }

    override fun increasedCartItem(productId: Long): QuantityUpdate {
        val updateResult = cartItemByProductId(productId).increaseQuantity()
        if (updateResult is QuantityUpdate.Success) {
            cartItemDataSource.increaseCount(productId, ShoppingCartItem.COUNT_INTERVAL)
        }
        return updateResult
    }

    override fun decreasedCartItem(productId: Long): QuantityUpdate {
        val updateResult = cartItemByProductId(productId).decreaseQuantity()
        if (updateResult is QuantityUpdate.Success) {
            cartItemDataSource.decreaseCount(productId, ShoppingCartItem.COUNT_INTERVAL)
        }
        return updateResult
    }

    override fun addCartItem(shoppingCartItem: ShoppingCartItem) {
        val cartItem = cartItemDataSource.cartItemById(shoppingCartItem.product.id)
        if (cartItem == null) {
            cartItemDataSource.insert(
                ShoppingCartItemEntity(
                    shoppingCartItem.product.id,
                    shoppingCartItem.totalQuantity,
                ),
            )
        } else {
            cartItemDataSource.increaseCount(
                shoppingCartItem.product.id,
                shoppingCartItem.totalQuantity,
            )
        }
    }

    companion object {
        private var instance: DefaultShoppingRepository? = null

        fun initialize(
            cartItemDataSource: CartItemDataSource,
            productDataSource: ProductDataSource,
        ): DefaultShoppingRepository =
            instance ?: synchronized(this) {
                instance ?: DefaultShoppingRepository(
                    cartItemDataSource, productDataSource,
                ).also { instance = it }
            }

        fun instance(): DefaultShoppingRepository = instance ?: error("RoomRepo가 초기화되지 않았습니다.")
    }
}
