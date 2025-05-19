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

    override fun getAllSize(): Int {
        var result = 0
        thread {
            result = dao.count()
        }.join()
        return result
    }

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct> {
        var items = listOf<ShoppingProduct>()

        thread {
            items = dao.getPaged(limit, offset).toDomain()
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
