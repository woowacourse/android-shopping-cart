package woowacourse.shopping.view.productdetail

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
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityController

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    private val recentProductsRepository: RecentProductsRepository,
    val cartItem: CartItem,
) : ViewModel(),
    QuantityController {
    private val _addToCart = MutableLiveData<Event<Unit>>()
    val addToCart: LiveData<Event<Unit>> = _addToCart

    private val _quantity = MutableLiveData(cartItem.quantity)
    val quantity: LiveData<Int> = _quantity

    private val _lastProductTitle = MutableLiveData<String>()
    val lastProductTitle: LiveData<String> = _lastProductTitle

    override fun increaseQuantity(
        productId: Long,
        quantityIncrease: Int,
    ) {
        val current = _quantity.value ?: INIT_QUANTITY
        val newQuantity = current + quantityIncrease
        _quantity.postValue(newQuantity)
    }

    override fun decreaseQuantity(
        productId: Long,
        quantityDecrease: Int,
        minQuantity: Int,
    ) {
        val current = _quantity.value ?: INIT_QUANTITY
        if (current > minQuantity) {
            val newQuantity = current - quantityDecrease
            _quantity.postValue(newQuantity)
        }
    }

    override fun updateQuantity() {
        val productId = cartItem.product.id
        val quantity = _quantity.value ?: INIT_QUANTITY
        cartRepository.update(productId, quantity)
    }

    fun onAddToCartClicked() {
        val newCartItem = cartItem.copy(quantity = _quantity.value ?: INIT_QUANTITY)
        _addToCart.value = Event(Unit)
        cartRepository.add(newCartItem) {}
    }

    fun setLastProductTitle() {
        recentProductsRepository.getMostRecentProduct {
            _lastProductTitle.postValue(it.title)
        }
    }

    companion object {
        private const val INIT_QUANTITY = 1

        class Factory(
            private val cartItem: CartItem,
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
                    cartItem,
                ) as T
            }
        }
    }
}
