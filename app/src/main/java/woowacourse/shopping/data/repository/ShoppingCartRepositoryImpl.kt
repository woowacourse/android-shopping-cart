package woowacourse.shopping.data.repository

import android.content.Context
import android.util.Log
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(context: Context) : ShoppingCartRepository {
    private val cartItemDao = CartItemDatabase.getInstance(context).cartItemDao()

    override fun addCartItem(product: Product) {
        thread {
            val addedCartItemId =
                cartItemDao.saveCartItem(CartItemEntity.makeCartItemEntity(product))
            if (addedCartItemId == ERROR_DATA_ID) throw NoSuchDataException()
        }
    }

    override fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem> {
        var pagingData = emptyList<CartItem>()
        thread {
            pagingData = cartItemDao.findPagingCartItem(offset, pagingSize).map { it.toCartItem() }
        }.join()
        if (pagingData.isEmpty()) throw NoSuchDataException()
        return pagingData
    }

    override fun deleteCartItem(itemId: Long) {
        var deleteId = ERROR_DELETE_DATA_ID
        thread {
            deleteId = cartItemDao.deleteCartItemById(itemId)
        }.join()
        if (deleteId == ERROR_DELETE_DATA_ID) throw NoSuchDataException()
    }

    override fun getCartItemResultFromProductId(productId: Long): CartItemResult {
        var cartItem: CartItem? = CartItem.defaultCartItem
        thread {
            cartItem = cartItemDao.findCartItemByProductId(productId)?.toCartItem()
        }.join()
        return when(cartItem?.id) {
            null -> CartItemResult.NotExists
            ERROR_DATA_ID -> throw NoSuchDataException()
            else -> {
                val itemId = cartItem?.id ?: throw NoSuchDataException()
                val itemCounter = cartItem?.cartItemCounter ?: throw NoSuchDataException()
                CartItemResult.Exists(
                    cartItemId = itemId,
                    counter = itemCounter,
                )
            }
        }
    }

    override fun updateCartItem(itemId: Long, count: Int) {
        var updateDataId = ERROR_UPDATE_DATA_ID
        thread {
            updateDataId = cartItemDao.updateCartItemCount(itemId, count)
        }.join()
        if (updateDataId== ERROR_UPDATE_DATA_ID) throw NoSuchDataException()
    }

    companion object {
        const val CART_ITEM_LOAD_PAGING_SIZE = 5
        const val CART_ITEM_PAGE_SIZE = 3
        const val ERROR_DATA_ID = -1L
        const val ERROR_DELETE_DATA_ID = 0
        const val ERROR_UPDATE_DATA_ID = 0
        const val DEFAULT_ITEM_SIZE = 0
    }
}
