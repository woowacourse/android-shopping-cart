package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.Product

class ProductsRepositoryImpl : ProductsRepository {
    override fun findAll(
        offset: Int,
        limit: Int,
    ): List<Product> {
        return DummyProducts.products
            .distinct()
            .subList(offset, offset + limit)
    }

    override fun totalSize(): Int = DummyProducts.products.size
}
