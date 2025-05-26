package woowacourse.shopping.data.repository

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.toDomain
import woowacourse.shopping.domain.model.Product
class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun fetchProducts(
        page: Int,
        count: Int,
        callback: (List<Product>) -> Unit
    ) {
        val offset = (page - 1) * count
        Thread {
            val result = productDao.fetchProductsWithOffset(offset, count)
                .map { it.toDomain() }

            mainHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun fetchProductDetail(
        id: Int,
        callback: (Product?) -> Unit
    ) {
        Thread {
            val result = productDao.getById(id)?.toDomain()

            mainHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun fetchIsProductsLoadable(
        lastId: Int,
        callback: (Boolean) -> Unit
    ) {
        Thread {
            val result = productDao.hasMoreThan(lastId)

            mainHandler.post {
                callback(result)
            }
        }.start()
    }
}