package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

class FakeProductRepository(
    private val products: Products = Products(),
) : ProductRepository {
    override suspend fun getProducts(): Products = products

    override suspend fun getPagingProducts(
        page: Int,
        pageSize: Int,
    ): Products {
        if (page < 0 || pageSize <= 0) return Products()

        val fromIndex = page * pageSize
        if (fromIndex >= products.productItems.size) return Products()

        val toIndex = min(fromIndex + pageSize, products.productItems.size)

        return Products(products.productItems.subList(fromIndex, toIndex))
    }

    override suspend fun hasNextPage(
        currentPage: Int,
        pageSize: Int,
    ): Boolean {
        val nextPageStartIndex = (currentPage + 1) * pageSize
        return nextPageStartIndex < products.productItems.size
    }

    override suspend fun findProductById(productId: Int): Product? = products.productItems.firstOrNull { it.productId == productId }
}
