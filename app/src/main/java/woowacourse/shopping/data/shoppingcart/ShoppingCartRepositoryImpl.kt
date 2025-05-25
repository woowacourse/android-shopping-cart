package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Page
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(private val cartItemDao: CartItemDao) : ShoppingCartRepository {
    override fun getOrNull(
        id: Int,
        onResult: (CartItem?) -> Unit,
    ) {
        thread {
            onResult(cartItemDao.getOrNull(id)?.toDomain())
        }
    }

    override fun getAll(onSuccess: (List<CartItem>) -> Unit) {
        thread {
            onSuccess(cartItemDao.getAll().map(CartItemEntity::toDomain))
        }
    }

    override fun getTotalCount(onResult: (Int) -> Unit) {
        thread {
            onResult(cartItemDao.getTotalCount())
        }
    }

    override fun getPage(
        pageSize: Int,
        requestedIndex: Int,
        onSuccess: (Page<CartItem>) -> Unit,
    ) {
        getAll { allItems ->
            val pageIndex = requestedIndex.coerceAtLeast(0)
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
        thread {
            cartItemDao.insert(cartItem.toEntity())
        }
    }

    override fun delete(cartItem: CartItem) {
        thread {
            cartItemDao.delete(cartItem.toEntity())
        }
    }
}
