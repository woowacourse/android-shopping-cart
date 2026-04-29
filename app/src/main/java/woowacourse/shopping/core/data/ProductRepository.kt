package woowacourse.shopping.core.data

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.data.ProductData.products
import woowacourse.shopping.core.model.Product

object ProductRepository {
    val productData = products

    fun getProducts(): ImmutableList<Product> =
        productData
            .map {
                it
            }.toImmutableList()

    fun getProductById(id: String): Product = productData.first { it.id == id }
}
