package woowacourse.shopping

interface ProductRepository {
    fun getProduct(productId: Long): Product

    fun getProducts(): List<Product>
}
