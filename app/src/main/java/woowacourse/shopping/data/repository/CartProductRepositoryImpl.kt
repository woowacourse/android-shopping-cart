package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.local.CartProductLocalDataSource
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class CartProductRepositoryImpl(
    private val localDataSource: CartProductLocalDataSource,
    private val productRepository: ProductRepository,
) : CartProductRepository {
    private var totalCount: Int = 0

    init {
        thread {
            localDataSource.getTotalCount { result ->
                result.onSuccess { totalCount = it }
            }
        }.join()
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<CartProduct>>) -> Unit,
    ) {
        if (offset >= totalCount) {
            onResult(Result.success(PagedResult(emptyList(), false)))
            return
        }

        val endIndex = (offset + limit).coerceAtMost(totalCount)
        thread {
            localDataSource.getPagedProducts(endIndex - offset, offset) { result ->
                result.onSuccess { entities ->
                    val productIds = entities.map { it.productId }
                    productRepository.getProductsByIds(productIds) { result ->
                        result.onSuccess { products ->
                            if (products.isEmpty()) {
                                onResult(Result.success(PagedResult(emptyList(), false)))
                                return@getProductsByIds
                            }
                            val productMap = products.associateBy { it.id }
                            val cartProducts =
                                entities.mapNotNull { entity ->
                                    productMap[entity.productId]?.let { product ->
                                        CartProduct(product, entity.quantity)
                                    }
                                }
                            val pagedResult = PagedResult(cartProducts, endIndex < totalCount)
                            onResult(Result.success(pagedResult))
                        }
                    }
                }
            }
        }
    }

    override fun getQuantityByProductId(
        productId: Long,
        onResult: (Result<Int?>) -> Unit,
    ) {
        thread {
            localDataSource.getQuantityByProductId(productId, onResult)
        }
    }

    override fun getTotalQuantity(onResult: (Result<Int>) -> Unit) {
        thread {
            localDataSource.getTotalQuantity(onResult)
        }
    }

    override fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        thread {
            when {
                currentQuantity == newQuantity -> onResult(Result.success(Unit))
                newQuantity == 0 -> deleteByProductId(productId, onResult)

                currentQuantity == 0 -> {
                    localDataSource.insert(CartProductEntity(productId, newQuantity)) { result ->
                        result.onSuccess {
                            totalCount++
                            onResult(Result.success(Unit))
                        }
                    }
                }

                else -> localDataSource.updateQuantity(productId, newQuantity, onResult)
            }
        }
    }

    override fun deleteByProductId(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        thread {
            localDataSource.deleteByProductId(productId) { result ->
                result.onSuccess {
                    totalCount--
                    onResult(Result.success(Unit))
                }
            }
        }
    }
}
