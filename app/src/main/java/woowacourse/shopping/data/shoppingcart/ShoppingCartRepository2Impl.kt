package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Page
import kotlin.concurrent.thread

class ShoppingCartRepository2Impl(private val cartItemDao: CartItemDao) : ShoppingCartRepository2 {
    override fun getOrNull(
        id: Int,
        onResult: (CartItem?) -> Unit,
    ) {
        thread {
            val cartItem = cartItemDao.getOrNull(id)?.toDomain()
            onResult(cartItem)
        }
    }

    override fun getAll(onSuccess: (List<CartItem>) -> Unit) {
        thread {
            val result = cartItemDao.getAll().map(CartItemEntity::toDomain)
            onSuccess(result)
        }
    }

    override fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<CartItem>) -> Unit,
    ) {
        getAll { allItems ->
            val from = pageSize * pageIndex
            val to = (from + pageSize).coerceAtMost(allItems.size)
            val items = allItems.subList(from, to)
            val hasPrevious = pageIndex > 0
            val hasNext = to < allItems.size
            val page =
                Page(
                    items,
                    hasPrevious,
                    hasNext,
                    pageIndex,
                )
            onSuccess(page)
        }
    }

    override fun insert(cartItem: CartItem) {
        getOrNull(cartItem.id) { retrievedItem ->
            if (retrievedItem == null) {
                cartItemDao.insert(cartItem.copy(quantity = 1).toEntity())
            } else {
                cartItemDao.insert(cartItem.toEntity())
            }
        }
    }

    override fun delete(cartItem: CartItem) {
        thread {
            cartItemDao.delete(cartItem.toEntity())
        }
    }
}
