package woowacourse.shopping.data.cart

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.cart.local.CartProductLocalDataSource
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartProductRepository
import kotlin.concurrent.thread

class CartProductRepositoryImpl(
    private val localDataSource: CartProductLocalDataSource,
) : CartProductRepository {
    private var totalCount: Int = 0

    init {
        thread { totalCount = localDataSource.count() }.join()
    }

    override fun insert(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            localDataSource.insert(CartProductEntity(productId = productId, quantity = quantity))
        }.join()
        totalCount++
    }

    override fun getAll(): List<CartProduct> {
        var result = listOf<CartProduct>()
        thread { result = localDataSource.getAll().map { it.toDomain() } }.join()
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
            items = localDataSource.getPaged(endIndex - offset, offset).map { it.toDomain() }
        }.join()

        val hasNext = endIndex < totalCount
        return PagedResult(items, hasNext)
    }

    override fun getQuantityByProductId(productId: Long): Int? {
        var result: Int? = null
        thread { result = localDataSource.getQuantityByProductId(productId) }.join()
        return result
    }

    override fun getTotalQuantity(): Int {
        var result = 0
        thread { result = localDataSource.getTotalQuantity() }.join()
        return result
    }

    override fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
    ) {
        if (currentQuantity == newQuantity) return
        if (newQuantity == 0) {
            deleteByProductId(productId)
            return
        }
        if (currentQuantity == 0) {
            insert(productId, newQuantity)
            return
        }
        thread { localDataSource.updateQuantity(productId, newQuantity) }.join()
    }

    override fun deleteByProductId(productId: Long) {
        thread { localDataSource.deleteByProductId(productId) }.join()
        totalCount--
    }
}
