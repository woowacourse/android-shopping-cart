package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.network.GoodsDaoImpl
import woowacourse.shopping.data.network.MockServer
import woowacourse.shopping.data.repository.GoodsRepositoryImpl
import woowacourse.shopping.data.repository.LatestGoodsRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    val goodsRepository: GoodsRepository by lazy { GoodsRepositoryImpl(GoodsDaoImpl()) }
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

        thread {
            MockServer().startMockWebServer()
        }
    }

    fun initShoppingRepository(repo: ShoppingRepositoryImpl) {
        shoppingRepository = repo
    }

    fun initLatestGoodsRepository(repo: LatestGoodsRepositoryImpl) {
        latestGoodsRepository = repo
    }
}
