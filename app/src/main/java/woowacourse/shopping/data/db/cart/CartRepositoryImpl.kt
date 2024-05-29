package woowacourse.shopping.data.db.cart

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.toCartItemEntity
import kotlin.concurrent.thread

class CartRepositoryImpl(database: CartDatabase) : CartRepository {
    private val dao = database.cartDao()

    override fun save(
        product: Product,
        quantity: Int,
    ) {
        if (findOrNullByProductId(product.id) != null) {
            update(product.id, quantity)
        } else {
            thread {
                dao.save(product.toCartItemEntity(quantity))
            }.join()
        }
    }

    override fun update(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            dao.update(productId, quantity)
        }.join()
    }

    override fun cartItemSize(): Int {
        var itemSize = 0
        thread {
            itemSize = dao.cartItemSize()
        }.join()
        return itemSize
    }

    override fun productQuantity(productId: Long): Int {
        var productQuantity = 0
        thread {
            productQuantity = dao.productQuantity(productId)
        }.join()
        return productQuantity
    }

    override fun findOrNullByProductId(productId: Long): CartItem? {
        var cartItemEntity: CartItemEntity? = null
        thread {
            cartItemEntity = dao.findByProductId(productId)
        }.join()

        return cartItemEntity?.toCartItem()
    }

    override fun find(cartItemId: Long): CartItem {
        var cartItemEntity: CartItemEntity? = null
        thread {
            cartItemEntity = dao.find(cartItemId)
        }.join()

        return cartItemEntity?.toCartItem() ?: throw IllegalArgumentException("데이터가 존재하지 않습니다.")
    }

    override fun findAll(): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        thread {
            cartItems = dao.findAll().map { it.toCartItem() }
        }.join()
        return cartItems
    }

    override fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        val offset = page * pageSize

        thread {
            cartItems =
                dao.findAllPaged(offset = offset, limit = pageSize)
                    .map { it.toCartItem() }
        }.join()

        return cartItems
    }

    override fun delete(cartItemId: Long) {
        thread {
            dao.delete(cartItemId)
        }.join()
    }

    override fun deleteAll() {
        thread {
            dao.deleteAll()
        }.join()
    }
}
