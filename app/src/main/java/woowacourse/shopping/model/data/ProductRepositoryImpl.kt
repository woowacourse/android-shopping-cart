package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product

class ProductRepositoryImpl(private val productServerApi: ProductServerApi) : ProductRepository {
    override fun start() = productServerApi.start()

    override fun find(id: Long): Product = productServerApi.find(id)

    override fun findAll(): List<Product> = productServerApi.findAll()

    override fun getProducts(): List<Product> = productServerApi.getProducts()

    override fun shutdown() = productServerApi.shutdown()
}
