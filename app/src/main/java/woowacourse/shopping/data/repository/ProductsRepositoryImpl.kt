package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.Product

class ProductsRepositoryImpl : ProductsRepository {
    override fun findAll(pageRequest: PageRequest): Page<Product> {
        val items =
            DummyProducts.products
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun totalSize(): Int = DummyProducts.products.size
}
