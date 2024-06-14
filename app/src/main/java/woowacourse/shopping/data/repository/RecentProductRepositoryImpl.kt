package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.mapper.toDomainModel
import woowacourse.shopping.data.model.mapper
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(context: Context) : RecentProductRepository {
    private val dao = ShoppingDatabase.getInstance(context).recentProductDao()

    override fun save(product: Product) {
        threadAction {
            dao.deleteWithProductId(product.id)
            dao.save(product.mapper())
        }
    }

    override fun loadLatest(): RecentProduct? {
        var recentProduct: RecentProduct? = null
        threadAction {
            recentProduct = dao.loadLatest()?.toDomainModel()
        }
        return recentProduct
    }

    override fun loadSecondLatest(): RecentProduct? {
        var secondRecentProduct: RecentProduct? = null
        threadAction {
            secondRecentProduct = dao.loadSecondLatest()?.toDomainModel()
        }
        return secondRecentProduct
    }

    override fun loadLatestList(): List<RecentProduct> {
        var recentProducts: List<RecentProduct> = emptyList()
        threadAction {
            recentProducts = dao.loadLatestList().map { it.toDomainModel() }
        }
        return recentProducts
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }
}
