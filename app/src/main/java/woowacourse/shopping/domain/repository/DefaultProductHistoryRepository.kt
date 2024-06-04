package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.domain.model.Product
import java.util.Collections.synchronizedList
import java.util.concurrent.atomic.AtomicInteger

class DefaultProductHistoryRepository(
    private val productHistoryDataSource: ProductHistoryDataSource,
    private val productDataSource: ProductDataSource,
) : ProductHistoryRepository {
    override fun saveProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        productHistoryDataSource.saveProductHistoryAsyncResult(productId, callback)
    }

    override fun loadAllProductHistoryAsyncResult(callback: (Result<List<Product>>) -> Unit) {
        productHistoryDataSource.loadAllProductHistoryAsyncResult { resultLoadAllIds ->
            resultLoadAllIds.onSuccess { ids ->
                val products = synchronizedList(List(ids.size) { Product.NULL })
                val remaining = AtomicInteger(ids.size)

                ids.forEachIndexed { index, id ->
                    productDataSource.findByIdAsyncResult(id) { resultIdProduct ->
                        resultIdProduct.onSuccess { productData ->
                            products[index] = productData.toDomain(quantity = 0)
                            if (remaining.decrementAndGet() == 0) {
                                callback(Result.success(products.toList()))
                            }
                        }
                        resultIdProduct.onFailure {
                            callback(Result.failure(it))
                        }
                    }
                }
            }
                .onFailure {
                    callback(Result.failure(it))
                }
        }
    }

    override fun loadLatestProductIdAsyncResult(callback: (Result<Long>) -> Unit) {
        productHistoryDataSource.loadLatestProductAsyncResult(callback)
    }
}
