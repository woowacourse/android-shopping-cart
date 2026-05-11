package woowacourse.shopping.repository

import woowacourse.shopping.model.ViewedProduct
import woowacourse.shopping.repository.dao.ViewedProductDao

class DefaultViewedProductRepository(
    private val productRepository: ProductRepository,
    private val viewedProductDao: ViewedProductDao,
) : ViewedProductRepository {
    override suspend fun addViewedProduct(productId: String) {
        viewedProductDao.addViewedProductByProductId(
            productId = productId,
            viewedAt = System.currentTimeMillis(),
        )
    }

    override suspend fun getRecentlyViewedProducts(
        offset: Int,
        size: Int,
    ): List<ViewedProduct> =
        viewedProductDao.getViewedProducts(offset, size).mapNotNull { viewedProductEntity ->
            productRepository.getProduct(viewedProductEntity.productId)?.let { product ->
                ViewedProduct(
                    product = product,
                    viewedAt = viewedProductEntity.viewedAt,
                )
            }
        }
}
