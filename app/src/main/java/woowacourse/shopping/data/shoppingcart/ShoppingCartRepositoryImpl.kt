package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.ShoppingProduct
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(
    private val dao: ShoppingCartDao,
) : ShoppingCartRepository {
    override fun getAll(): List<ShoppingProduct> {
        var result = listOf<ShoppingProduct>()
        thread {
            result = dao.getAll().toDomain()
        }.join()
        return result
    }

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct> {
        var total = 0

        thread {
            total = dao.count()
        }.join()

        val endIndex = (offset + limit).coerceAtMost(total)
        var items = listOf<ShoppingProduct>()

        thread {
            items = dao.getPaged(endIndex - offset, offset).toDomain()
        }.join()

        return items
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
