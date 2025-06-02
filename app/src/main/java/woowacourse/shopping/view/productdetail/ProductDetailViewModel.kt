package woowacourse.shopping.view.productdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.recentProducts.RecentProductsRepository
import woowacourse.shopping.data.recentProducts.RecentProductsRepositoryImpl
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.model.product.Product
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityListener
import woowacourse.shopping.view.ToastMessageHandler

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    private val recentProductsRepository: RecentProductsRepository,
    productId: Long,
) : ViewModel(),
    QuantityListener,
    ToastMessageHandler {
    private val _selectedProduct = MutableLiveData(EMPTY_ITEM)
    val selectedProduct: LiveData<CartItem> = _selectedProduct

    init {
        recentProductsRepository.findRecentProductById(productId) { result ->
            result
                .onSuccess {
                    _selectedProduct.postValue(it)
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    private val _addToCart = MutableLiveData<Event<Unit>>()
    val addToCart: LiveData<Event<Unit>> = _addToCart

    private val _lastProductTitle = MutableLiveData<String>()
    val lastProductTitle: LiveData<String> = _lastProductTitle

    private val _lastProductVisibility = MutableLiveData<Boolean>()
    val lastProductVisibility: LiveData<Boolean> = _lastProductVisibility

    private val _toastMessage = MutableLiveData<Event<Unit>>()
    override val toastMessage: LiveData<Event<Unit>> = _toastMessage

    override fun increaseQuantity(
        productId: Long,
        quantityIncrease: Int,
    ) {
        val currentQuantity = _selectedProduct.value?.quantity ?: INIT_QUANTITY
        val newQuantity = currentQuantity + quantityIncrease
        _selectedProduct.value = _selectedProduct.value?.copy(quantity = newQuantity)
    }

    override fun decreaseQuantity(
        productId: Long,
        quantityDecrease: Int,
        minQuantity: Int,
    ) {
        val currentQuantity = _selectedProduct.value?.quantity ?: INIT_QUANTITY
        if (currentQuantity > minQuantity) {
            val newQuantity = currentQuantity - quantityDecrease
            _selectedProduct.value = _selectedProduct.value?.copy(quantity = newQuantity)
        }
    }

    override fun updateQuantity() {
        val productId = _selectedProduct.value?.product?.id ?: return
        val quantity = _selectedProduct.value?.quantity ?: INIT_QUANTITY
        cartRepository.update(productId, quantity) { result ->
            result
                .onSuccess {
                    return@update
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    fun onAddToCartClicked() {
        val newCartItem =
            _selectedProduct.value?.copy(
                quantity = _selectedProduct.value?.quantity ?: INIT_QUANTITY,
            ) ?: return
        _addToCart.value = Event(Unit)
        cartRepository.add(newCartItem) { result ->
            result
                .onSuccess {
                    return@add
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    fun setLastProductTitle() {
        recentProductsRepository.getSecondMostRecentProduct { result ->
            result
                .onSuccess {
                    _lastProductTitle.postValue(it.product.title)
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    companion object {
        private const val INIT_QUANTITY = 1
        private val EMPTY_ITEM =
            CartItem(Product(1, "", "", 0), 0)

        class Factory(
            private val productId: Long,
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val context = application.applicationContext

                val database = ShoppingDatabase.getDatabase(context)
                val cartDao = database.cartDao()
                val recentProductDao = database.recentProductDao()

                return ProductDetailViewModel(
                    CartRepositoryImpl(cartDao),
                    RecentProductsRepositoryImpl(recentProductDao),
                    productId,
                ) as T
            }
        }
    }
}
