import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.dao.LastProductDao
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.LastProductEntity
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.toDomain
import woowacourse.shopping.data.repository.LastProductRepository
import woowacourse.shopping.data.entity.toDomain as productToDomain
import woowacourse.shopping.domain.model.LastProduct
import woowacourse.shopping.domain.model.Product

class LastProductRepositoryImpl(
    private val lastDao: LastProductDao,
    private val productDao: ProductDao
) : LastProductRepository {

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun fetchProducts(callback: (List<LastProduct>) -> Unit) {
        Thread {
            val result = lastDao.getRecent10().mapNotNull { entity ->
                val productEntity: ProductEntity? = productDao.getById(entity.productId.toInt())
                productEntity?.productToDomain()?.let { product ->
                    entity.toDomain(product)
                }
            }
            mainHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun insertProduct(product: Product) {
        Thread {
            val existing = lastDao.findByProductId(product.id.toLong())
            if (existing != null) {
                lastDao.delete(existing)
            }

            lastDao.insert(
                LastProductEntity(
                    productId = product.id.toLong(),
                    viewedAt = System.currentTimeMillis()
                )
            )
            lastDao.deleteOldExceptRecent10()
        }.start()
    }

    override fun deleteLastProduct() {
        Thread {
            lastDao.getRecent10().lastOrNull()?.let {
                lastDao.delete(it)
            }
        }.start()
    }
}