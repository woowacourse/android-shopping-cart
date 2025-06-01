package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage

interface ProductRepository {
    fun getProduct(
        productId: Long,
        onResult: (Product) -> Unit,
    )

    fun getProducts(
        productIds: List<Long>,
        onResult: (List<Product>) -> Unit,
    )

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
        onResult: (ProductSinglePage) -> Unit,
    )
}
