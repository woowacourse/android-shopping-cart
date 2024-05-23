package woowacourse.shopping.data.shopping

import woowacourse.shopping.data.shopping.product.ProductDataSource
import woowacourse.shopping.data.shopping.product.ProductPageData
import woowacourse.shopping.data.shopping.recent.RecentProductData
import woowacourse.shopping.data.shopping.recent.RecentProductDataSource
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.domain.repository.ShoppingRepository
import java.time.LocalDateTime

class DefaultShoppingRepository(
    private val productDataSource: ProductDataSource,
    private val recentProductDataSource: RecentProductDataSource,
) : ShoppingRepository {
    private val cachedProducts = mutableMapOf<Int, List<Product>>()
    private var pageData: ProductPageData? = null

    override fun products(
        currentPage: Int,
        size: Int,
    ): Result<List<Product>> {
        val products = cachedProducts[currentPage]
        if (products != null) return Result.success(products)
        return productDataSource.products(currentPage, size)
            .mapCatching {
                pageData = it
                cachedProducts[currentPage] = it.content
                it.content
            }
    }

    override fun productById(id: Long): Result<Product> {
        if (pageData != null) {
            val product = pageData?.content?.find { it.id == id }
            if (product != null) return Result.success(product)
        }
        return productDataSource.productById(id)
    }

    override fun canLoadMore(
        page: Int,
        size: Int,
    ): Result<Boolean> {
        val totalPages = pageData?.totalPages
        if (totalPages != null) {
            val canLoadMore = totalPages > page
            return Result.success(canLoadMore)
        }
        return productDataSource.canLoadMore(page, size)
    }

    override fun recentProducts(size: Int): Result<List<Product>> {
        val result = recentProductDataSource.recentProducts(size)
        return result.mapCatching {
            it.map { product ->
                val productResult = productDataSource.productById(product.productId)
                if (productResult.isFailure) error("Product(id=${product.productId}) not found")
                productResult.getOrThrow()
            }
        }
    }

    override fun saveRecentProduct(id: Long): Result<Long> {
        return recentProductDataSource.saveRecentProduct(RecentProductData(id, LocalDateTime.now()))
    }
}
