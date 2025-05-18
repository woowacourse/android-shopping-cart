package woowacourse.shopping.data.cartRepository

import woowacourse.shopping.domain.Product

interface CartRepository {
    fun getAllProducts(onResult: (List<Product>) -> Unit)

    fun getProducts(
        limit: Int,
        onResult: (List<Product>) -> Unit,
    )

    fun addProduct(product: Product)

    fun deleteProduct(productId: Long)
}
