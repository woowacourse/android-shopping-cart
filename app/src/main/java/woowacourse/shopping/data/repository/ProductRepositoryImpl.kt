package woowacourse.shopping.data.repository

import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productStorage: ProductStorage,
) : ProductRepository {
    override fun get(id: Long): Product = productStorage[id]

    override fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): ProductSinglePage {
        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize
        return productStorage.singlePage(fromIndex, toIndex)
    }
}
