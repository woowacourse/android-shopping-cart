package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.GoodsRepositoryImpl
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.LatestGoodsRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.domain.repository.ShoppingRepository

class ShoppingApplication : Application() {
    val goodsRepository by lazy { GoodsRepositoryImpl }
    val latestGoodsRepository by lazy {
        LatestGoodsRepositoryImpl(
            ShoppingDatabase.getDatabase(this).latestGoodsDao(),
        )
    }
    lateinit var shoppingRepository: ShoppingRepository

    override fun onCreate() {
        super.onCreate()
        val shoppingRepo =
            ShoppingRepositoryImpl(
                ShoppingDatabase.getDatabase(this).shoppingDao(),
            )

        initShoppingRepository(shoppingRepo)
    }

    fun initShoppingRepository(repo: ShoppingRepositoryImpl) {
        shoppingRepository = repo
    }
}
