package woowacourse.shopping.data.source

import woowacourse.shopping.domain.Product

interface ProductDataSource {
    val products: List<Product>
}
