package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.Product

interface ProductStorage {
    fun getProducts(): List<Product>

    fun getProducts(limit: Int): List<Product>
}
