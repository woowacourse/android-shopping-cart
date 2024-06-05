package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.presentation.ui.EventState
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.UpdateUiModel
import kotlin.concurrent.thread

class ShoppingViewModel(private val repository: Repository) :
    ViewModel(), ShoppingActionHandler {
    private var offSet: Int = 0

    private val _products = MutableLiveData<UiState<List<CartProduct>>>(UiState.None)
    val products: LiveData<UiState<List<CartProduct>>> get() = _products

    private val _cartCount = MutableLiveData<Int>(0)
    val cartCount: LiveData<Int> get() = _cartCount

    private val _recentProducts = MutableLiveData<UiState<List<RecentProduct>>>(UiState.None)
    val recentProducts: LiveData<UiState<List<RecentProduct>>> get() = _recentProducts

    private val _errorHandler = MutableLiveData<EventState<String>>()
    val errorHandler: LiveData<EventState<String>> get() = _errorHandler

    private val _navigateHandler = MutableLiveData<EventState<NavigateUiState>>()
    val navigateHandler: LiveData<EventState<NavigateUiState>> get() = _navigateHandler

    fun loadProductByOffset() {
        thread {
            repository.findProductByPagingWithMock(offSet, PAGE_SIZE).onSuccess {
                if (_products.value is UiState.None) {
                    _products.postValue(UiState.Success(it))
                } else {
                    _products.postValue(
                        UiState.Success((_products.value as UiState.Success).data + it),
                    )
                }
                offSet++
            }.onFailure {
                _errorHandler.postValue(EventState(LOAD_ERROR))
            }
        }
    }

    fun getItemCount() {
        thread {
            repository.getMaxCartCount().onSuccess { maxCount ->
                _cartCount.postValue(maxCount)
            }
        }
    }

    companion object {
        const val LOAD_ERROR = "아이템을 끝까지 불러왔습니다"
        const val PAGE_SIZE = 20
    }

    override fun onProductClick(productId: Long) {
        _navigateHandler.value = EventState(NavigateUiState.ToDetail(productId))
    }

    override fun onCartClick() {
        _navigateHandler.value = EventState(NavigateUiState.ToCart)
    }

    override fun loadMore() {
        loadProductByOffset()
    }

    override fun onPlus(cartProduct: CartProduct) {
        thread {
            val cartProducts = (_products.value as UiState.Success).data.map { it.copy() }
            val index = cartProducts.indexOfFirst { it.productId == cartProduct.productId }
            cartProducts[index].plusQuantity()

            repository.saveCart(Cart(cartProducts[index].productId, cartProducts[index].quantity!!))
                .onSuccess {
                    _products.postValue(UiState.Success(cartProducts))
                    getItemCount()
                }
                .onFailure {
                    _errorHandler.postValue(EventState("아이템 증가 오류"))
                }
        }
    }

    override fun onMinus(cartProduct: CartProduct) {
        thread {
            val cartProducts = (_products.value as UiState.Success).data.map { it.copy() }
            val index = cartProducts.indexOfFirst { it.productId == cartProduct.productId }
            cartProducts[index].minusQuantity()

            if (cartProducts[index].quantity > 0) {
                repository.saveCart(
                    Cart(
                        cartProducts[index].productId,
                        cartProducts[index].quantity,
                    ),
                )
                    .onSuccess {
                        _products.postValue(UiState.Success(cartProducts))
                        getItemCount()
                    }
                    .onFailure {
                        _errorHandler.postValue(EventState("아이템 증가 오류"))
                    }
            } else {
                repository.deleteCart(
                    cartProducts[index].productId,
                ).onSuccess {
                    _products.postValue(UiState.Success(cartProducts))
                    getItemCount()
                }.onFailure {
                    _errorHandler.postValue(EventState("아이템 증가 오류"))
                }
            }
        }
    }

    fun updateCartProducts(updateUiModel: UpdateUiModel) {
        val cartProducts = (_products.value as UiState.Success).data.map { it.copy() }
        updateUiModel.updatedItems.forEach { updatedItem ->
            // 해당 productId를 갖는 제품을 찾아서 수량을 업데이트
            val cartProductToUpdate = cartProducts.find { it.productId == updatedItem.key }
            cartProductToUpdate?.quantity = updatedItem.value.quantity
        }
        _products.value = UiState.Success(cartProducts)
    }

    fun findAllRecent() {
        thread {
            repository.findByLimit(10).onSuccess {
                _recentProducts.postValue(UiState.Success(it))
            }.onFailure {
                _errorHandler.postValue(EventState("최근 아이템 로드 에러"))
            }
        }
    }
}
