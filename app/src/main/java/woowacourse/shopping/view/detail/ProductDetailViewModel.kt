package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productLiveData: MutableLiveData<Product> = MutableLiveData()
    val productLiveData: LiveData<Product> get() = _productLiveData
    val quantityLiveData: MutableLiveData<Int> = MutableLiveData(1)

    fun addProduct(product: Product) {
        shoppingCartRepository.save(
            ShoppingCartItem(
                product = product,
                quantity = quantityLiveData.value!!,
            ),
        )
    }

    fun setProduct(product: Product) {
        _productLiveData.value = product
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as ShoppingCartApplication)
                    ProductDetailViewModel(application.shoppingCartRepository)
                }
            }
    }
}
