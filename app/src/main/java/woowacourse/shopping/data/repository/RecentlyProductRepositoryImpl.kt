package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.recently.RecentlyProductDatabase
import woowacourse.shopping.data.model.RecentlyProductEntity
import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.domain.repository.RecentlyProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class RecentlyProductRepositoryImpl(context: Context): RecentlyProductRepository {
    private val recentlyProductDao = RecentlyProductDatabase.getInstance(context).recentlyProductDao()

    override fun addRecentlyProduct(recentlyProduct: RecentlyProduct) {
        thread {
            val addedItemId =
                recentlyProductDao.addRecentlyProduct(RecentlyProductEntity.makeRecentlyProductEntity(recentlyProduct))
            if (addedItemId == ERROR_DATA_ID) throw NoSuchDataException()
        }
    }

    override fun getMostRecentlyProduct(): RecentlyProduct {
        var recentlyProduct = RecentlyProduct.defaultRecentlyProduct
        thread {
            val firstProduct = recentlyProductDao.getMostRecentlyProduct()?.toRecentlyProduct()
            if (firstProduct != null){
                recentlyProduct = firstProduct
            }
        }.join()
        if (recentlyProduct.productId == ERROR_DATA_ID) throw NoSuchDataException()
        return recentlyProduct
    }

    override fun getRecentlyProductList(): List<RecentlyProduct> {
        var pagingData = emptyList<RecentlyProduct>()
        thread {
            pagingData = recentlyProductDao.findPagingRecentlyProduct(CURRENT_CART_ITEM_LOAD_PAGING_SIZE).map { it.toRecentlyProduct() }
        }.join()
        return pagingData
    }

    override fun deleteRecentlyProduct(id: Long) {
        var deleteId = ERROR_DELETE_DATA_ID
        thread {
            deleteId = recentlyProductDao.deleteRecentlyProductById(id)
        }.join()
        if (deleteId == ERROR_DELETE_DATA_ID) throw NoSuchDataException()
    }

    companion object {
        const val ERROR_DATA_ID = -1L
        const val ERROR_DELETE_DATA_ID = 0
        const val CURRENT_CART_ITEM_LOAD_PAGING_SIZE = 10
    }
}
