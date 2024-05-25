package woowacourse.shopping.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.local.RepositoryImpl
import woowacourse.shopping.data.local.RoomDataSource
import woowacourse.shopping.data.local.db.AppDatabase
import woowacourse.shopping.presentation.ui.cart.CartViewModel
import woowacourse.shopping.presentation.ui.detail.ProductDetailViewModel
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel
import java.lang.IllegalStateException

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                ProductDetailViewModel(
                    RepositoryImpl(
                        RoomDataSource(AppDatabase.instance?.cartProductDao() ?: throw IllegalStateException("DB is not initialized")),
                    ),
                ) as T
            }

            modelClass.isAssignableFrom(ShoppingViewModel::class.java) -> {
                ShoppingViewModel(
                    RepositoryImpl(
                        RoomDataSource(AppDatabase.instance?.cartProductDao() ?: throw IllegalStateException("DB is not initialized")),
                    ),
                ) as T
            }

            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(
                    RepositoryImpl(
                        RoomDataSource(AppDatabase.instance?.cartProductDao() ?: throw IllegalStateException("DB is not initialized")),
                    ),
                ) as T
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}
