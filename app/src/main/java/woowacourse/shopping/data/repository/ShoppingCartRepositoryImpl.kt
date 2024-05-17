package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.detail.ProductDetailViewModel
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(context: Context) : ShoppingCartRepository {
    private val cartItemDao = CartItemDatabase.getInstance(context).cartItemDao()

    override fun addCartItem(product: Product)  {
        thread {
            val addedCartItemId = cartItemDao.saveCartItem(CartItemEntity.makeCartItemEntity(product))
            if (addedCartItemId == ProductDetailViewModel.ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        }
    }

    override fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem> {
        return cartItemDao.findPagingCartItem(offset, pagingSize).map { it.toCartItem() }
    }

    override fun deleteCartItem(itemId: Long) {
        cartItemDao.deleteCartItemById(itemId)
    }
}
