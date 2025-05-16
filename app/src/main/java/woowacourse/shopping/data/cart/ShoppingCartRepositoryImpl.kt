package woowacourse.shopping.data.cart

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.CartProduct
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(
    private val dao: ShoppingCartDao,
) : ShoppingCartRepository {
    override fun getAll(): List<CartProduct> {
        var result = listOf<CartProduct>()
        thread {
            result = dao.getAll().toDomain()
        }.join()
        return result
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<CartProduct> {
        var total = 0
        thread {
            total = dao.count()
        }.join()

        if (offset >= total) return PagedResult(emptyList(), false)

        val endIndex = (offset + limit).coerceAtMost(total)
        var items = listOf<CartProduct>()
        thread {
            items = dao.getPaged(endIndex - offset, offset).toDomain()
        }.join()

        val hasNext = endIndex < total
        return PagedResult(items, hasNext)
    }

    override fun insert(productId: Long) {
        thread {
            dao.insert(ShoppingCartEntity(productId = productId))
        }.join()
    }

    override fun delete(shoppingCartId: Long) {
        thread {
            dao.delete(shoppingCartId)
        }.join()
    }
}
