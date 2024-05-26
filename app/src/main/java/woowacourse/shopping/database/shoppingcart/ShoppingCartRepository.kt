package woowacourse.shopping.database.shoppingcart

import woowacourse.shopping.ShoppingCartRepositoryInterface
import woowacourse.shopping.database.toShoppingCartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productlist.UpdatedQuantity
import kotlin.concurrent.thread

class ShoppingCartRepository(private val dao: ShoppingCartDao) : ShoppingCartRepositoryInterface {
    override fun shoppingCart(): ShoppingCart {
        var itemEntities = listOf<ShoppingCartItemEntity>()
        thread { itemEntities = dao.getAllItems().orEmpty() }.join()
        val items = itemEntities.map { it.toShoppingCartItem() }
        return ShoppingCart(items)
    }

    override fun shoppingCartItems(
        page: Int,
        pageSize: Int,
    ): List<ShoppingCartItem> {
        var cartSize = 0
        thread { cartSize = shoppingCartSize() }.join()
        val offset = page * pageSize - 1
        var itemEntities: List<ShoppingCartItemEntity> = listOf()
        thread { itemEntities = dao.getItemsInRange(offset, pageSize).orEmpty() }.join()
        return itemEntities.map { it.toShoppingCartItem() }
    }

    override fun addShoppingCartItem(
        product: Product,
        quantity: Int,
    ) {
        var existItemEntity: ShoppingCartItemEntity? = null
        thread {
            existItemEntity = dao.findItemById(product.id)
        }.join()
        if (existItemEntity == null) {
            val entity =
                ShoppingCartItemEntity(
                    product.id,
                    product.name,
                    product.price.value,
                    product.imageUrl.url,
                    quantity,
                )
            thread { dao.addItem(entity) }.join()
        } else {
            thread {
                dao.updateItemQuantity(
                    product.id,
                    quantity + existItemEntity!!.quantity,
                )
            }.join()
        }
    }

    override fun deleteShoppingCartItem(productId: Long) {
        thread { dao.deleteItemById(productId) }.join()
    }

    override fun plusCartItemQuantity(productId: Long): ShoppingCartItem {
        var shoppingCartItems: List<ShoppingCartItem> = listOf()
        thread {
            shoppingCartItems = dao.getAllItems().orEmpty().map { it.toShoppingCartItem() }
        }.join()
        val cartItem = shoppingCartItems.first { it.product.id == productId }
        val result = cartItem.increaseQuantity()
        if (result is QuantityUpdate.Success) {
            thread { dao.updateItemQuantity(productId, result.value.quantity) }.join()
            return result.value
        } else {
            error("주문 가능한 최대 수량을 초과했습니다.")
        }
    }

    override fun minusCartItemQuantity(productId: Long): ShoppingCartItem {
        var shoppingCartItems: List<ShoppingCartItem> = listOf()
        thread {
            shoppingCartItems = dao.getAllItems().orEmpty().map { it.toShoppingCartItem() }
        }.join()
        val cartItem = shoppingCartItems.first { it.product.id == productId }
        val result = cartItem.decreaseQuantity()
        if (result is QuantityUpdate.Success) {
            thread { dao.updateItemQuantity(productId, result.value.quantity) }.join()
            return result.value
        } else {
            error("주문 가능한 최소 개수는 1개 입니다.")
        }
    }

    override fun shoppingCartSize(): Int {
        var cartSize = 0
        thread { cartSize = dao.getAllItems().orEmpty().size }.join()
        return cartSize
    }

    override fun cartTotalItemQuantity(): Int {
        var totalQuantity = 0
        thread { totalQuantity = dao.getTotalItemQuantity() }.join()
        return totalQuantity
    }

    override fun cartItemQuantity(): List<UpdatedQuantity> {
        var cartItems: List<ShoppingCartItem> = listOf()
        thread { cartItems = dao.getAllItems().orEmpty().map { it.toShoppingCartItem() } }.join()
        return cartItems.map { item ->
            UpdatedQuantity(item.product.id, item.quantity)
        }
    }

    override fun cartItemQuantity(productIds: Set<Long>): List<UpdatedQuantity> {
        var cartItems: List<ShoppingCartItem> = listOf()
        thread { cartItems = dao.getAllItems().orEmpty().map { it.toShoppingCartItem() } }.join()
        val cartItemMap = cartItems.associateBy { it.product.id }
        return productIds.map { id ->
            val quantity = cartItemMap[id]?.quantity ?: 0
            UpdatedQuantity(id, quantity)
        }
    }
}
