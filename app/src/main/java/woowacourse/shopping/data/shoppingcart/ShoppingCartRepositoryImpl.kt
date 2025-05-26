package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Page
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(private val cartItemDao: CartItemDao) : ShoppingCartRepository {
    override fun getOrNull(
        id: Int,
        onResult: (CartProduct?) -> Unit,
    ) {
        thread {
            onResult(cartItemDao.getOrNull(id)?.toDomain())
        }
    }

    override fun getAll(onSuccess: (List<CartProduct>) -> Unit) {
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
        onSuccess: (Page<CartProduct>) -> Unit,
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

    override fun insert(cartProduct: CartProduct) {
        thread {
            cartItemDao.insert(cartProduct.toEntity())
        }
    }

    override fun delete(cartProduct: CartProduct) {
        thread {
            cartItemDao.delete(cartProduct.toEntity())
        }
    }

    override fun clear() {
        thread {
            cartItemDao.clear()
        }
    }
}
