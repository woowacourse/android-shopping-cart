package woowacourse.shopping.data.server

import woowacourse.shopping.domain.Product

interface ProductRemoteDataSource {
    fun getProducts(path: String): List<Product>
}
