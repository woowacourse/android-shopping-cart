package woowacourse.shopping.data.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products

class RecentlyViewedProductRepository(private val recentlyViewedProductDao: RecentlyViewedProductDao) {
    fun getAll(): Flow<Products> {
        return recentlyViewedProductDao.getAll().map {
            val items = it?.map { it.toObject() } ?: emptyList()
            Products(items)
        }
    }

    suspend fun updateList(product: Product) {
        recentlyViewedProductDao.enqueueAndLimit10(product.toEntity())
    }

    fun getLatestItem(): Flow<Product?> {
        return recentlyViewedProductDao.getLatestItem().map {
            it?.toObject()
        }
    }
}

private fun Product.toEntity() =
    RecentlyViewedProductEntity(
        id = id,
        name = name,
        price = price,
        imageUri = imageUri,
    )
