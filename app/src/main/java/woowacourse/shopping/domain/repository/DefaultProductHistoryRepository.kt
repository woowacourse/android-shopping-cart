package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.domain.model.Product

class DefaultProductHistoryRepository(
    private val productHistoryDataSource: ProductHistoryDataSource,
    private val productDataSource: ProductDataSource,
) : ProductHistoryRepository {

    // ---
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


    override fun saveProductHistoryAsync(productId: Long, callback: (Boolean) -> Unit) {
        productHistoryDataSource.saveProductHistoryAsync(productId, callback)
    }

    override fun loadLatestProductAsync(callback: (Long) -> Unit) {
        productHistoryDataSource.loadLatestProductAsync { productId ->
            callback(productId)
        }
    }

    override fun loadAllProductHistoryAsync(callback: (List<Product>) -> Unit) {
        productHistoryDataSource.loadAllProductHistoryAsync { productIds ->
            val products = productIds.map {
                productDataSource.findById(it).toDomain(quantity = 0)
            }
            callback(products)
        }
    }

    override fun loadProductHistoryAsync(productId: Long, callback: (Product) -> Unit) {
        productHistoryDataSource.loadProductHistoryAsync(productId) { id ->
            val product = productDataSource.findById(id).toDomain(quantity = 0)
            callback(product)
        }
    }
}
