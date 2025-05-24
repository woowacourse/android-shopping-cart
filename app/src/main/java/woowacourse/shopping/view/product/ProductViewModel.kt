package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.map
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.cartRepository.CartRepository
import woowacourse.shopping.data.productsRepository.ProductRepository
import woowacourse.shopping.domain.Product

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _isShowMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val isShowMore: LiveData<Boolean> get() = _isShowMore

    private val _cartItemsCount: MutableLiveData<Int> = MutableLiveData(0)
    val cartItemsCount: LiveData<String>
        get() =
            _cartItemsCount.map { count -> if (count > 99) CART_ITEMS_COUNT_OVER_100 else count.toString() }

    private val _isAddCart: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAddCart: LiveData<Boolean> get() = _isAddCart

    var totalProductsCount: Int = 0
    private var currentIndex = 0

    init {
        fetchInitData()
        fetchData()
    }

    fun fetchInitData() {
        totalProductsCount = productRepository.getProductsSize()
        _isShowMore.postValue(totalProductsCount > LIMIT_COUNT)
        fetchCartItemsCount()
    }

    fun fetchData() {
        val newProducts = productRepository.getProducts(currentIndex, LIMIT_COUNT)
        _products.value = (_products.value ?: emptyList()).plus(newProducts)
        currentIndex += LIMIT_COUNT
    }

    fun fetchCartItemsCount() {
        cartRepository.getAllProductsSize {
            _cartItemsCount.postValue(it)
        }
    }

    fun changeShowState(isShow: Boolean) {
        _isShowMore.postValue(isShow)
    }

    fun addCart(product: Product) {
        cartRepository.addProduct(product)
        _isAddCart.postValue(true)
        fetchCartItemsCount()
    }

    companion object {
        private const val LIMIT_COUNT: Int = 20
        private const val CART_ITEMS_COUNT_OVER_100: String = "99+"

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val productRepository =
                        (this[APPLICATION_KEY] as ShoppingApplication).productRepository
                    val cartRepository =
                        (this[APPLICATION_KEY] as ShoppingApplication).cartRepository
                    ProductViewModel(
                        productRepository = productRepository,
                        cartRepository = cartRepository,
                    )
                }
            }
    }
}
