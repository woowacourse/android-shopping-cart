package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.Product
import kotlin.math.min

class ProductsRepositoryImpl : ProductsRepository {
    override fun findAll(
        offset: Int,
        limit: Int,
    ): List<Product> {
        return DummyProducts.products
            .distinct()
            .subList(offset, min(offset + limit, DummyProducts.products.size))
    }

    override fun totalSize(): Int = DummyProducts.products.size
}
