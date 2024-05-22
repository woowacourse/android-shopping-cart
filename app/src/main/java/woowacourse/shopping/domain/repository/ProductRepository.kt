package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.Product

interface ProductRepository {
    fun fetchSinglePage(page: Int): List<CartableProduct>

    fun fetchProduct(id: Long): CartableProduct

    fun addAll(products: List<Product>)
}
