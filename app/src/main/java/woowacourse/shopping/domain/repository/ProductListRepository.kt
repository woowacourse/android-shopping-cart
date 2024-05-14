package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductListRepository {
    fun getProductList(): List<Product>
}
