package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.data.recentlyproducts.RecentlyProductsRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.utils.MutableSingleLiveData
import woowacourse.shopping.utils.SingleLiveData

class ProductDetailViewModel(
    val product: Product,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentlyProductsRepository: RecentlyProductsRepository,
) : ViewModel() {
    private val _event: MutableSingleLiveData<ProductDetailEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ProductDetailEvent> get() = _event

    private val _quantity = MutableLiveData(0)
    val quantity: LiveData<Int> = _quantity

    private val _recentProduct = MutableLiveData<Product>(null)
    val recentProduct: LiveData<Product> = _recentProduct

    fun addToShoppingCart() {
        shoppingCartRepository.insert(product.id, _quantity.value ?: 0) { result: Result<Unit> ->
            result
                .onSuccess {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS)
                }.onFailure {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
                }
        }
    }

    fun increaseItemQuantity(productId: Long) {
        _quantity.value = _quantity.value?.plus(1)
    }

    fun decreaseItemQuantity(productId: Long) {
        _quantity.value = _quantity.value?.minus(1)
    }

    fun getLastViewedProduct() {
        recentlyProductsRepository.getFirst { result ->
            result
                .onSuccess {
                    _recentProduct.postValue(it?.toProductDomain())
                }.onFailure {
                    _event.postValue(ProductDetailEvent.RECORD_RECENT_PRODUCT_FAILURE)
                }
        }
    }

    fun deleteMostRecentProduct() {
        recentlyProductsRepository.deleteMostRecent { result ->
            result.getOrNull()
        }
    }

    companion object {
        fun provideFactory(
            product: Product,
            repository: ShoppingCartRepository = ShoppingProvider.shoppingCartRepository,
            recentlyProductsRepository: RecentlyProductsRepository = ShoppingProvider.recentlyProductsRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                        return ProductDetailViewModel(product, repository, recentlyProductsRepository) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
