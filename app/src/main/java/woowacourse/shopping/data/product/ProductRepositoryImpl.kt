package woowacourse.shopping.data.product

import woowacourse.shopping.data.api.ProductServerApi
import woowacourse.shopping.model.Product
import kotlin.concurrent.thread

class ProductRepositoryImpl(private val productServerApi: ProductServerApi) : ProductRepository {
    override fun start() {
        thread {
            productServerApi.start()
        }.join()
    }

    override fun find(id: Long): Product {
        lateinit var product: Product
        thread {
            product = productServerApi.find(id)
        }.join()
        return product
    }

    override fun findAll(): List<Product> {
        var products = emptyList<Product>()
        thread {
            products = productServerApi.findAll()
        }.join()
        return products
    }

    override fun getProducts(): List<Product> {
        var products = emptyList<Product>()
        thread {
            products = productServerApi.getProducts()
        }.join()
        return products
    }

    override fun shutdown() {
        thread {
            productServerApi.shutdown()
        }.join()
    }
}
