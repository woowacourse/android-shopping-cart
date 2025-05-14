package woowacourse.shopping.domain

interface ProductRepository {
    fun getProducts(): List<Product>

    fun getCartProducts(): List<Product>

    fun deleteProduct(product: Product)
}
