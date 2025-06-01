package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductsDataSource
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val datasource: ProductsDataSource,
) : ProductRepository {
    override fun getProduct(
        productId: Long,
        onResult: (Product) -> Unit,
    ) {
        thread {
            onResult(datasource.getProduct(productId).toDomain())
        }
    }

    override fun getProducts(
        productIds: List<Long>,
        onResult: (List<Product>) -> Unit,
    ) {
        thread {
            val products = datasource.getProducts(productIds).map { it.toDomain() }
            onResult(products)
        }
    }

    override fun loadSinglePage(
        page: Int,
        pageSize: Int,
        onResult: (ProductSinglePage) -> Unit,
    ) {
        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize

        thread {
            val productSinglePage = datasource.singlePage(fromIndex, toIndex).toDomain()
            onResult(productSinglePage)
        }
    }
}
