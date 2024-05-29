package woowacourse.shopping.database.recentviewedproducts

import woowacourse.shopping.RecentlyViewedProductRepositoryInterface
import woowacourse.shopping.database.toProduct
import woowacourse.shopping.domain.Product
import java.time.LocalDateTime
import kotlin.concurrent.thread

class RecentlyViewedProductsRepository(private val dao: RecentlyViewedProductsDao) :
    RecentlyViewedProductRepositoryInterface {
    override fun recentTenProducts(): List<Product> {
        var productEntities = listOf<RecentlyViewedProductEntity>()
        thread { productEntities = dao.getRecentTenProducts().orEmpty() }.join()
        return productEntities.map { it.toProduct() }
    }

    override fun lastlyViewedProduct(): Product? {
        var productEntity: RecentlyViewedProductEntity? = null
        thread { productEntity = dao.getLastViewedProduct() }.join()
        return productEntity?.toProduct()
    }

    override fun addRecentlyViewedProduct(product: Product) {
        var existEntity: RecentlyViewedProductEntity? = null
        thread { existEntity = dao.findProductById(product.id) }.join()
        if (existEntity == null) {
            val presentTime = LocalDateTime.now()
            val productEntity =
                RecentlyViewedProductEntity(
                    id = product.id,
                    name = product.name,
                    price = product.price.value,
                    imageUrl = product.imageUrl.url,
                    viewedTime = presentTime,
                )
            thread { dao.insertRecentlyViewedProduct(productEntity) }.join()
        } else {
            updateProductViewedTime(product.id)
        }
    }

    private fun updateProductViewedTime(id: Long) {
        val presentTime = LocalDateTime.now()
        thread { dao.updateViewedTime(id, presentTime) }.join()
    }
}
