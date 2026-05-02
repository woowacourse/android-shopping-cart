package woowacourse.shopping.data.repository

import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object ProductRepositoryImpl : ProductRepository {
    private val products = Products(ProductFixture.productList)

    override fun getProducts(): Products = products

    override fun getPagingProducts(
        page: Int,
        pageSize: Int,
    ): Products {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, products.productItems.size)
        return Products(products.productItems.subList(0, toIndex))
    }

    override fun hasNextPage(
        currentPage: Int,
        pageSize: Int,
    ): Boolean =
        getPagingProducts(page = currentPage, pageSize = pageSize).productItems.size >= pageSize &&
            (currentPage + 1) * pageSize < products.productItems.size

    @OptIn(ExperimentalUuidApi::class)
    override fun findProductById(productId: Uuid): Product? = ProductFixture.productList.firstOrNull { it.productId == productId }
}
