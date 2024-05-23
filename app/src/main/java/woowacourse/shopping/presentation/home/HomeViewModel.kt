package woowacourse.shopping.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.local.PRODUCT_DATA
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.util.Event
import kotlin.concurrent.thread

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), HomeItemEventListener, QuantityListener {
    private var page: Int = 0

    private val _products: MutableLiveData<List<CartableProduct>> =
        MutableLiveData<List<CartableProduct>>(emptyList())
    val products: LiveData<List<CartableProduct>>
        get() = _products

    private val _loadStatus: MutableLiveData<LoadStatus> = MutableLiveData(LoadStatus())
    val loadStatus: LiveData<LoadStatus>
        get() = _loadStatus

    private val _navigateToDetailEvent: MutableLiveData<Event<Long>> = MutableLiveData()
    val navigateToDetailEvent: LiveData<Event<Long>>
        get() = _navigateToDetailEvent

    private val _changedPosition: MutableLiveData<Event<Int>> = MutableLiveData()
    val changedPosition: LiveData<Event<Int>>
        get() = _changedPosition

    private val _totalQuantity: MutableLiveData<Int> = MutableLiveData(0)
    val totalQuantity: LiveData<Int>
        get() = _totalQuantity

    init {
        loadProducts()
    }

    private fun loadProducts() {
        thread {
            _loadStatus.postValue(loadStatus.value?.copy(isLoadingPage = true, loadingAvailable = false))
            _products.postValue(products.value?.plus(productRepository.fetchSinglePage(page++)))
            products.value?.let {
                _loadStatus.postValue(
                    loadStatus.value?.copy(
                        loadingAvailable = productRepository.fetchSinglePage(page).isNotEmpty(),
                        isLoadingPage = false,
                    )
                )
            }
            _totalQuantity.postValue(
                cartRepository.fetchTotalCount()
            )
        }
    }

    override fun navigateToProductDetail(id: Long) {
        _navigateToDetailEvent.value = Event(id)
    }

    override fun loadNextPage() {
        loadProducts()
    }

    override fun onQuantityChange(productId: Long, cartItemId: Long?, quantity: Int) {
        thread {
            Log.i("TAG", "onQuantityChange: $productId, quantity: $quantity")
            if (quantity <= -1) return@thread
            val id = if (cartItemId == null) {
                cartRepository.addCartItem(
                    CartItem(
                        productId = productId,
                        quantity = quantity,
                    )
                )
            } else {
                cartRepository.updateQuantity(cartItemId, quantity)
                cartItemId
            }
            Log.i("TAG", "onQuantityChange: $id")
            val cartItem = cartRepository.fetchCartItem(id)
            val target = products.value?.map {
                if (it.cartItem?.id == id) {
                    val item = it.copy(cartItem = cartItem)
                    Log.i("TAG", "onQuantityChange: $item")
                    item
                } else it
            }
            _products.postValue(target)
            _changedPosition.postValue(Event(products.value?.indexOfFirst { it.cartItem?.id == cartItem.id } ?: return@thread))
            _totalQuantity.postValue(
                cartRepository.fetchTotalCount()
            )
        }
    }
}
