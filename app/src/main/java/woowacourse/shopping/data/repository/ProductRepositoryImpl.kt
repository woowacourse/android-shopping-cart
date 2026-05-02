package woowacourse.shopping.data.repository

import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val SHOPPING_PAGE_SIZE = 20

object ProductRepositoryImpl : ProductRepository {
    private val products = Products(ProductFixture.productList)

    override fun getProducts(): Products = products

    override fun getPagingProducts(page: Int): Products {
        val fromIndex = page * SHOPPING_PAGE_SIZE
        val toIndex = min(fromIndex + SHOPPING_PAGE_SIZE, products.productItems.size)
        return Products(products.productItems.subList(0, toIndex))
    }

    override fun hasNextPage(currentPage: Int): Boolean =
        getPagingProducts(page = currentPage).productItems.size >= SHOPPING_PAGE_SIZE &&
            (currentPage + 1) * SHOPPING_PAGE_SIZE < products.productItems.size

    @OptIn(ExperimentalUuidApi::class)
    override fun findProductById(productId: Uuid): Product? = ProductFixture.productList.firstOrNull { it.productId == productId }
}
