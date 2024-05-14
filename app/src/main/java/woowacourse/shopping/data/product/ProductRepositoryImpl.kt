package woowacourse.shopping.data.product

import woowacourse.shopping.model.Product

object ProductRepositoryImpl : ProductRepository {
    private val products: MutableMap<Long, Product> = mutableMapOf()
    private var id: Long = 0L

    private const val INVALID_ID_MESSAGE = "해당하는 id의 상품이 존재하지 않습니다."

    init {
        dummyProducts.forEach { save(it.imageUrl, it.title, it.price) }
    }

    override fun find(id: Long): Product {
        return products[id] ?: throw IllegalArgumentException(INVALID_ID_MESSAGE)
    }

    override fun findAll(): List<Product> {
        return products.map { it.value }
    }

    override fun save(
        imageUrl: String,
        title: String,
        price: Int,
    ): Long {
        products[id] = Product(id, imageUrl, title, price)
        return id++
    }
}
