package woowacourse.shopping.data.repository

import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.collections.get

class ProductRepositoryImpl(
    private val productStorage: ProductStorage,
) : ProductRepository {
    override fun get(id: Long): Product = productStorage[id]

    override fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        if (fromIndex < 0) return emptyList()
        val toIndex = fromIndex + pageSize
        return productStorage.singlePage(fromIndex, toIndex)
    }
}
