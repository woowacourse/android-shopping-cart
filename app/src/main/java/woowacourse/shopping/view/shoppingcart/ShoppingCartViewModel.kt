package woowacourse.shopping.view.shoppingcart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.data.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.ShoppingProduct

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    fun deleteProduct(shoppingProduct: ShoppingProduct) {
        repository.delete(shoppingProduct.position)
    }

    companion object {
        fun provideFactory(
            applicationContext: Context,
            repository: ShoppingCartRepository = ShoppingCartRepositoryImpl(applicationContext),
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
                        return ShoppingCartViewModel(repository) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
