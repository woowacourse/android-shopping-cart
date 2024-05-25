package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.recently.RecentlyProductDatabase
import woowacourse.shopping.data.model.RecentlyProductEntity
import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.domain.repository.CurrentProductRepository
import woowacourse.shopping.utils.Mapper.toRecentlyProduct
import woowacourse.shopping.utils.Mapper.toRecentlyProductEntity
import woowacourse.shopping.utils.exception.NoSuchDataException
import kotlin.concurrent.thread

class CurrentProductRepositoryImpl(context: Context) : CurrentProductRepository {
    private val recentlyProductDao =
        RecentlyProductDatabase.getInstance(context).recentlyProductDao()

    override fun saveRecentlyProduct(recentlyProduct: RecentlyProduct) {
        thread {
            val addedRecentlyProductId =
                recentlyProductDao.addRecentlyProduct(
                    recentlyProduct.toRecentlyProductEntity(),
                )
            if (addedRecentlyProductId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        }
    }

    override fun getMostRecentlyProduct(): RecentlyProduct {
        var recentlyProductEntity: RecentlyProductEntity? = null
        thread {
            recentlyProductEntity = recentlyProductDao.getMostRecentlyProduct()
        }.join()
        return recentlyProductEntity?.toRecentlyProduct() ?: throw NoSuchDataException()
    }

    override fun getPagingRecentlyProduct(): List<RecentlyProduct> {
        var pagingData: List<RecentlyProduct> = emptyList()
        thread {
            pagingData = recentlyProductDao.findPagingRecentlyProduct(RECENTLY_PAGING_SIZE).map { it.toRecentlyProduct() }
        }.join()
        if (pagingData.isEmpty()) throw NoSuchDataException()
        return pagingData
    }

    override fun removeRecentlyProduct(id: Long) {
        var deleteId = ERROR_DELETE_DATA_ID
        thread {
            deleteId = recentlyProductDao.deleteRecentlyProductById(id)
        }.join()
        if (deleteId == ERROR_DELETE_DATA_ID) throw NoSuchDataException()
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
        const val RECENTLY_PAGING_SIZE = 10
        const val ERROR_DELETE_DATA_ID = 0
    }
}
