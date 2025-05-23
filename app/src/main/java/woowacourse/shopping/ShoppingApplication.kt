package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.GoodsRepositoryImpl
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.LatestGoodsRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository

class ShoppingApplication : Application() {
    val goodsRepository by lazy { GoodsRepositoryImpl }
    lateinit var shoppingRepository: ShoppingRepository
    lateinit var latestGoodsRepository: LatestGoodsRepository

    override fun onCreate() {
        super.onCreate()
        val shoppingRepo =
            ShoppingRepositoryImpl(
                ShoppingDatabase.getDatabase(this).shoppingDao(),
            )

        initShoppingRepository(shoppingRepo)

        val latestGoodsRepo =
            LatestGoodsRepositoryImpl(
                ShoppingDatabase.getDatabase(this).latestGoodsDao(),
            )
        initLatestGoodsRepository(latestGoodsRepo)
    }

    fun initShoppingRepository(repo: ShoppingRepositoryImpl) {
        shoppingRepository = repo
    }

    fun initLatestGoodsRepository(repo: LatestGoodsRepositoryImpl) {
        latestGoodsRepository = repo
    }
}
