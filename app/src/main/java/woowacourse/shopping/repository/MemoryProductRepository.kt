package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

class MemoryProductRepository(
    products: List<Product>,
) : ProductRepository {
    private val products: List<Product> = products.toList()

    override fun getProduct(productId: Long): Product =
        products.find { it.id == productId } ?: throw IllegalArgumentException("해당 상품을 찾을 수 없습니다.")

    override fun getProducts(): List<Product> = products.toList()
}
