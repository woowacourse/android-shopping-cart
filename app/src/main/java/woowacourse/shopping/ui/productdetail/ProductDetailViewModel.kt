package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Product.Companion.EMPTY_PRODUCT
import woowacourse.shopping.domain.repository.ProductRepository

class ProductDetailViewModel(
    private val productsRepository: ProductRepository,
    private val cartDummyRepository: CartDummyRepositoryImpl = CartDummyRepositoryImpl,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(EMPTY_PRODUCT)
    val product: LiveData<Product> get() = _product

    fun loadProductDetail(id: Int) {
        productsRepository.fetchProductDetail(id) { product ->
            _product.postValue(product)
        }
    }

    fun addCartProduct() {
        cartDummyRepository.addCartProduct(product.value ?: return)
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY])

                    return ProductDetailViewModel(
                        (application as ShoppingApp).productRepository,
                    ) as T
                }
            }
    }
}
