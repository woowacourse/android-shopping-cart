package woowacourse.shopping.domain.product.repository

import woowacourse.shopping.domain.product.model.Product

interface ProductRepository {
    fun getProductsSize(): Int

    fun getProduct(id: String): Product

    fun isProductExist(productId: String): Boolean

    fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
