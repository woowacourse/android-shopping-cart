package woowacourse.shopping

import android.content.Context
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
        private set
    lateinit var shoppingCartRepository: ShoppingCartRepository
        private set
    lateinit var recentGoodsRepository: RecentGoodsRepository
        private set

    fun init(context: Context) {
        val shoppingCartDatabase = ShoppingCartDatabase.getDatabase(context)
        val recentGoodsDatabase = RecentGoodsDatabase.getDatabase(context)
        goodsRepository = GoodsRepositoryImpl()
        shoppingCartRepository = ShoppingCartRepositoryImpl(shoppingCartDatabase.shoppingCartDao())
        recentGoodsRepository = RecentGoodsRepositoryImpl(recentGoodsDatabase.recentGoodsDao())
    }
}
