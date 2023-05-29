package woowacourse.shopping.repository

import woowacourse.shopping.Product

interface ProductRepository {
    fun findProductById(
        id: Int,
        onSuccess: (Product?) -> Unit,
    )

    fun getProductsWithRange(
        startIndex: Int,
        size: Int,
        onSuccess: (List<Product>) -> Unit,
    )
}
