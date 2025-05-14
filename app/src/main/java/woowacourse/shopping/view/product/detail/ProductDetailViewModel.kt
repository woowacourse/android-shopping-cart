package woowacourse.shopping.view.product.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.data.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(
    val product: Product,
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _navigateEvent = MutableLiveData<Unit>()
    val navigateEvent: LiveData<Unit> = _navigateEvent

    fun addToShoppingCart() {
        repository.insertAll(product)
        _navigateEvent.value = Unit
    }

    companion object {
        fun provideFactory(
            product: Product,
            applicationContext: Context,
            repository: ShoppingCartRepository = ShoppingCartRepositoryImpl(applicationContext),
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                        return ProductDetailViewModel(product, repository) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
