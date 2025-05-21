package woowacourse.shopping.data.cart

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.CartProduct
import kotlin.concurrent.thread

class LocalCartProductRepository(
    private val dao: CartProductDao,
) : CartProductRepository {
    private var totalCount: Int = 0

    init {
        thread { totalCount = dao.count() }.join()
    }

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
        if (offset >= totalCount) return PagedResult(emptyList(), false)

        val endIndex = (offset + limit).coerceAtMost(totalCount)
        var items = listOf<CartProduct>()
        thread {
            items = dao.getPaged(endIndex - offset, offset).toDomain()
        }.join()

        val hasNext = endIndex < totalCount
        return PagedResult(items, hasNext)
    }

    override fun getQuantityByProductId(productId: Long): Int? {
        var result: Int? = null
        thread { result = dao.getQuantityByProductId(productId) }.join()
        return result
    }

    override fun updateQuantity(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            dao.updateQuantity(productId, quantity)
        }.join()
    }

    override fun insert(productId: Long) {
        thread {
            dao.insert(CartProductEntity(productId = productId, quantity = 1))
        }.join()
        totalCount++
    }

    override fun deleteByProductId(productId: Long) {
        thread {
            dao.deleteByProductId(productId)
        }.join()
        totalCount--
    }
}
