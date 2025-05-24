package woowacourse.shopping.data.fake

import woowacourse.shopping.data.datasource.ProductsDataSource
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.domain.repository.ProductRepository

class FakeProductRepositoryImpl(
    private val datasource: ProductsDataSource,
) : ProductRepository {
    override fun getProduct(
        productId: Long,
        onResult: (Product) -> Unit,
    ) {
        val result = datasource.getProduct(productId).toDomain()
        onResult(result)
    }

    override fun getProducts(
        productIds: List<Long>,
        onResult: (List<Product>) -> Unit,
    ) {
        val products = datasource.getProducts(productIds).map { it.toDomain() }
        onResult(products)
    }

    override fun loadSinglePage(
        page: Int,
        pageSize: Int,
        onResult: (ProductSinglePage) -> Unit,
    ) {
        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize
        val productSinglePage = datasource.singlePage(fromIndex, toIndex).toDomain()
        onResult(productSinglePage)
    }
}
