package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.goods.repository.GoodsRepositoryImpl
import woowacourse.shopping.data.shoppingcart.database.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

object RepositoryProvider {
    lateinit var goodsRepository: GoodsRepository
    lateinit var shoppingCartRepository: ShoppingCartRepository

    fun init(application: Application) {
        val database = ShoppingCartDatabase.getDatabase(application)
        goodsRepository = GoodsRepositoryImpl()
        shoppingCartRepository = ShoppingCartRepositoryImpl(database.shoppingCartDao())
    }
}