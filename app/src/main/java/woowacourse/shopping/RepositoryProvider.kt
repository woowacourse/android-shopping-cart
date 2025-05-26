package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.goods.repository.GoodsRepositoryImpl
import woowacourse.shopping.data.recentgoods.database.RecentGoodsDatabase
import woowacourse.shopping.data.recentgoods.repository.RecentGoodsRepositoryImpl
import woowacourse.shopping.data.shoppingcart.database.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.RecentGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

object RepositoryProvider {
    lateinit var goodsRepository: GoodsRepository
    lateinit var shoppingCartRepository: ShoppingCartRepository
    lateinit var recentGoodsRepository: RecentGoodsRepository

    fun init(application: Application) {
        val shoppingCartDatabase = ShoppingCartDatabase.getDatabase(application)
        val recentGoodsDatabase = RecentGoodsDatabase.getDatabase(application)
        goodsRepository = GoodsRepositoryImpl()
        shoppingCartRepository = ShoppingCartRepositoryImpl(shoppingCartDatabase.shoppingCartDao())
        recentGoodsRepository = RecentGoodsRepositoryImpl(recentGoodsDatabase.recentGoodsDao())
    }
}
