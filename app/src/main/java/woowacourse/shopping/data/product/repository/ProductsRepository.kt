package woowacourse.shopping.data.product.repository

import woowacourse.shopping.domain.product.Product

interface ProductsRepository {
    fun load(onLoad: (Result<List<Product>>) -> Unit)
}
