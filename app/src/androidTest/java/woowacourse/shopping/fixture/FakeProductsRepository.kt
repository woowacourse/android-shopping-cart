package woowacourse.shopping.fixture

import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.product.ProductsRepository
import woowacourse.shopping.domain.Product

class FakeProductsRepository : ProductsRepository {
    override fun findAll(pageRequest: PageRequest): Page<Product> {
        val items =
            TestProducts.productUiModels
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun totalSize(): Int {
        return TestProducts.productUiModels.size
    }
}
