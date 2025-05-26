package woowacourse.shopping.data.cart

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.cart.local.CartProductLocalDataSource
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class CartProductRepositoryImpl(
    private val localDataSource: CartProductLocalDataSource,
    private val productRepository: ProductRepository,
) : CartProductRepository {
    private var totalCount: Int = 0

    init {
        thread { totalCount = localDataSource.getTotalCount() }.join()
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (PagedResult<CartProduct>) -> Unit,
    ) {
        if (offset >= totalCount) {
            onSuccess(PagedResult(emptyList(), false))
            return
        }

        val endIndex = (offset + limit).coerceAtMost(totalCount)
        thread {
            val entities = localDataSource.getPagedProducts(endIndex - offset, offset)
            val latch = CountDownLatch(entities.size)

            val cartProducts = mutableListOf<CartProduct>()
            entities.forEach { entity ->
                productRepository.getProductById(entity.productId) { product ->
                    product?.let {
                        val cartProduct = CartProduct(it, entity.quantity)
                        cartProducts.add(cartProduct)
                    }
                    latch.countDown()
                }
            }

            latch.await()
            val hasNext = endIndex < totalCount
            onSuccess(PagedResult(cartProducts, hasNext))
        }
    }

    override fun getQuantityByProductId(
        productId: Long,
        onSuccess: (Int?) -> Unit,
    ) {
        thread {
            val result = localDataSource.getQuantityByProductId(productId)
            onSuccess(result)
        }
    }

    override fun getTotalQuantity(onSuccess: (Int) -> Unit) {
        thread {
            val result = localDataSource.getTotalQuantity()
            onSuccess(result)
        }
    }

    override fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
        onSuccess: () -> Unit,
    ) {
        thread {
            when {
                currentQuantity == newQuantity -> onSuccess()
                newQuantity == 0 -> {
                    deleteByProductId(productId) { onSuccess() }
                }

                currentQuantity == 0 -> {
                    localDataSource.insert(CartProductEntity(productId, newQuantity))
                    totalCount++
                    onSuccess()
                }

                else -> {
                    localDataSource.updateQuantity(productId, newQuantity)
                    onSuccess()
                }
            }
        }
    }

    override fun deleteByProductId(
        productId: Long,
        onSuccess: () -> Unit,
    ) {
        thread {
            localDataSource.deleteByProductId(productId)
            totalCount--
            onSuccess()
        }
    }
}
