package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.presentation.ui.EventState
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.UpdateUiModel
import kotlin.concurrent.thread

class CartViewModel(private val repository: Repository) : ViewModel(), CartActionHandler {
    var maxOffset: Int = 0
        private set

    var offSet: Int = 0
        private set
        get() = field.coerceAtMost(maxOffset)
    private val _carts = MutableLiveData<UiState<List<CartProduct>>>(UiState.None)

    val carts: LiveData<UiState<List<CartProduct>>> get() = _carts

    private val _errorHandler = MutableLiveData<EventState<String>>()
    val errorHandler: LiveData<EventState<String>> get() = _errorHandler

    val updateUiModel: UpdateUiModel = UpdateUiModel()

    init {
        getItemCount()
    }

    fun findProductByOffset() {
        thread {
            repository.findCartByPaging(offSet, PAGE_SIZE).onSuccess {
                _carts.postValue(UiState.Success(it))
            }.onFailure {
                _errorHandler.value = EventState(CART_LOAD_ERROR)
            }
        }
    }

    private fun getItemCount() {
        thread {
            repository.getMaxCartCount().onSuccess { maxCount ->
                maxOffset = ((maxCount + PAGE_UPPER_BOUND) / PAGE_SIZE - 1).coerceAtLeast(0)
            }
        }
    }

    override fun onDelete(cartProduct: CartProduct) {
        thread {
            updateUiModel.add(cartProduct.productId, cartProduct.copy(quantity = 0))
            repository.deleteCart(cartProduct.productId).onSuccess {
                getItemCount()
                findProductByOffset()
            }.onFailure {
                _errorHandler.value = EventState(CART_DELETE_ERROR)
            }
        }
    }

    override fun onNext() {
        if (offSet == maxOffset) return
        offSet++
        findProductByOffset()
    }

    override fun onPrevious() {
        if (offSet == 0) return
        offSet--
        findProductByOffset()
    }

    override fun onPlus(cartProduct: CartProduct) {
        thread {
            val cartProducts = (_carts.value as UiState.Success).data.map { it.copy() }
            val index = cartProducts.indexOfFirst { it.productId == cartProduct.productId }
            cartProducts[index].plusQuantity()
            updateUiModel.add(cartProduct.productId, cartProducts[index])

            repository.saveCart(Cart(cartProducts[index].productId, cartProducts[index].quantity))
                .onSuccess {
                    _carts.postValue(UiState.Success(cartProducts))
                }
                .onFailure {
                    _errorHandler.postValue(EventState("아이템 증가 오류"))
                }
        }
    }

    override fun onMinus(cartProduct: CartProduct) {
        thread {
            val cartProducts = (_carts.value as UiState.Success).data.map { it.copy() }
            val index = cartProducts.indexOfFirst { it.productId == cartProduct.productId }
            cartProducts[index].minusQuantity()
            updateUiModel.add(cartProduct.productId, cartProducts[index])

            if (cartProducts[index].quantity > 0) {
                repository.saveCart(
                    Cart(
                        cartProducts[index].productId,
                        cartProducts[index].quantity,
                    ),
                )
                    .onSuccess {
                        _carts.postValue(UiState.Success(cartProducts))
                    }
                    .onFailure {
                        _errorHandler.postValue(EventState("아이템 증가 오류"))
                    }
            } else {
                repository.deleteCart(
                    cartProducts[index].productId,
                ).onSuccess {
                    _carts.postValue(UiState.Success(cartProducts))
                }.onFailure {
                    _errorHandler.postValue(EventState("아이템 증가 오류"))
                }
            }
        }
    }

    companion object {
        const val CART_LOAD_ERROR = "LOAD ERROR"
        const val CART_DELETE_ERROR = "DELETE ERROR"
        const val PAGE_SIZE = 5
        const val PAGE_UPPER_BOUND = 4
    }
}
