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
    override fun saveProductHistory(productId: Long) {
        productHistoryDataSource.saveProductHistory(productId)
    }

    override fun loadAllProductHistory(): List<Product> {
        val productIds = productHistoryDataSource.loadAllProductHistory()
        return productIds.map {
            productDataSource.findById(it).toDomain(quantity = 0)
        }
    }

    override fun loadProductHistory(productId: Long): Product {
        val id =
            productHistoryDataSource.loadProductHistory(productId)
                ?: throw NoSuchElementException("there is no product history with id $productId")
        return productDataSource.findById(id).toDomain(quantity = 0)
    }

    override fun loadLatestProduct(): Product {
        val productId: Long = productHistoryDataSource.loadLatestProduct()
        return productDataSource.findById(productId).toDomain(quantity = 0)
    }

    override fun saveProductHistoryAsync(
        productId: Long,
        callback: (Boolean) -> Unit,
    ) {
        productHistoryDataSource.saveProductHistoryAsync(productId, callback)
    }

    override fun loadLatestProductAsync(callback: (Long) -> Unit) {
        productHistoryDataSource.loadLatestProductAsync { productId ->
            callback(productId)
        }
    }

    override fun loadAllProductHistoryAsync(callback: (List<Product>) -> Unit) {
        productHistoryDataSource.loadAllProductHistoryAsync { ids ->
            val products = synchronizedList(List(ids.size) { Product.NULL })
            val remaining = AtomicInteger(ids.size)

            ids.forEachIndexed { index, id ->
                productDataSource.findByIdAsync(id) { product ->
                    products[index] = product.toDomain(quantity = 0)

                    if (remaining.decrementAndGet() == 0) {
                        callback(products.toList())
                    }
                }
            }
        }
    }

    override fun loadProductHistoryAsync(
        productId: Long,
        callback: (Product) -> Unit,
    ) {
        productHistoryDataSource.loadProductHistoryAsync(productId) { id ->
            productDataSource.findByIdAsync(id) { product ->
                callback(product.toDomain(quantity = 0))
            }
        }
    }

    override fun saveProductHistoryAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
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

    override fun loadProductHistoryAsyncResult(productId: Long, callback: (Result<Product>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun loadLatestProductIdAsyncResult(callback: (Result<Long>) -> Unit) {
        productHistoryDataSource.loadLatestProductAsyncResult(callback)
    }
}
