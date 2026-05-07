package woowacourse.shopping.repository.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.domain.Products

class InMemoryProductRepository(
    packageName: String,
) : ProductRepository {
    private var products by mutableStateOf(Products(ProductFixture.productList(packageName)))

    override fun getAllProducts(): Products = products
}
