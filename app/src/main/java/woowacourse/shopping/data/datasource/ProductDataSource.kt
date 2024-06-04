package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.product.CartableProduct

interface ProductDataSource {
    fun fetchProduct(id: Long): CartableProduct

    fun fetchSinglePage(page: Int): List<CartableProduct>
}
