package woowacourse.shopping.repository

import woowacourse.shopping.repository.dao.ViewedProductDao

class DatabaseViewedProductRepository(
    private val productRepository: ProductRepository,
    private val viewedProductDao: ViewedProductDao,
) : ViewedProductRepository {
    override suspend fun addViewedProductByProductId(productId: String) {
        viewedProductDao.addViewedProductByProductId(
            productId = productId,
            viewedAt = System.currentTimeMillis(),
        )
    }

    override suspend fun getViewedProducts(
        offset: Int,
        size: Int,
    ): List<ViewedProduct> =
        viewedProductDao.getViewedProducts(offset, size).mapNotNull { viewedProduct ->
            productRepository.getProduct(viewedProduct.productId)?.let { product ->
                ViewedProduct(
                    product = product,
                    viewedAt = viewedProduct.viewedAt,
                )
            }
        }
}
