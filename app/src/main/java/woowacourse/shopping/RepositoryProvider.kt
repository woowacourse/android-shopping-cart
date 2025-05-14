package woowacourse.shopping

import android.content.Context
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.domain.repository.CartRepository

object RepositoryProvider {
    private const val NOT_INITIALIZED_MESSAGE = "%s가 초기화되지 않았습니다"

    private var _cartRepository: CartRepository? = null
    val cartRepository
        get() =
            requireNotNull(_cartRepository) {
                NOT_INITIALIZED_MESSAGE.format(
                    CartRepository::class.simpleName,
                )
            }

    fun initCartRepository(context: Context) {
        val cartDao = ShoppingDatabase.getDatabase(context).cartDao()
        _cartRepository = CartRepositoryImpl(cartDao)
    }
}
