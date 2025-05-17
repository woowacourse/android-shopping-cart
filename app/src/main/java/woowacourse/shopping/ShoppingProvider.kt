package woowacourse.shopping

import android.content.Context
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl

object ShoppingProvider {
    lateinit var shoppingCartRepository: ShoppingCartRepositoryImpl

    fun initProductRepository(context: Context) {
        val database = ShoppingCartDatabase.getDataBase(context)
        shoppingCartRepository = ShoppingCartRepositoryImpl(database.shoppingCartDao())
    }
}
