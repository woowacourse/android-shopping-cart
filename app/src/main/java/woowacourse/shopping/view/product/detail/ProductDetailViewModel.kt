package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(
    private val cartRepositoryImpl: CartRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    fun fetchData(product: Product) {
        _product.value = product
    }

    fun addData(
        product: Product,
        count: Int,
    ) {
        cartRepositoryImpl.addProduct(product, count)
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val cartRepositoryImpl =
                        (this[APPLICATION_KEY] as ShoppingApplication).cartRepository
                    ProductDetailViewModel(
                        cartRepositoryImpl = cartRepositoryImpl,
                    )
                }
            }
    }
}
