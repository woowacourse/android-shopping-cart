package woowacourse.shopping.data.repository

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.toDomain
import woowacourse.shopping.domain.model.Product

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {

    override fun fetchProducts(
        page: Int,
        count: Int,
        onSuccess: (List<Product>) -> Unit
    ) {
        val offset = (page - 1) * count
        Thread {
            val result = productDao.fetchProductsWithOffset(offset, count)
                .map { it.toDomain() }
            onSuccess(result)
        }.start()
    }

    override fun fetchProductDetail(
        id: Int,
        onSuccess: (Product?) -> Unit
    ) {
        Thread {
            val result = productDao.getById(id)?.toDomain()
            onSuccess(result)
        }.start()
    }

    override fun fetchIsProductsLoadable(
        lastId: Int,
        onSuccess: (Boolean) -> Unit
    ) {
        Thread {
            val result = productDao.hasMoreThan(lastId)
            onSuccess(result)
        }.start()
    }
}