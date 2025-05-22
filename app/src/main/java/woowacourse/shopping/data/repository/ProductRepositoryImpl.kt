package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDatasource
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val datasource: ProductDatasource,
) : ProductRepository {
    override fun get(
        productId: Long,
        onResult: (Product) -> Unit,
    ) {
        thread { onResult(datasource.getProducts(productId).toDomain()) }
    }

    override fun loadSinglePage(
        page: Int,
        pageSize: Int,
        onResult: (ProductSinglePage) -> Unit,
    ) {
        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize

        thread { onResult(datasource.singlePage(fromIndex, toIndex).toDomain()) }
    }
}
