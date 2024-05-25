package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.local.entity.CartProductEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.presentation.ui.ErrorEventState
import woowacourse.shopping.presentation.ui.UiState
import kotlin.concurrent.thread

class ShoppingViewModel(private val repository: Repository) :
    ViewModel(), ShoppingActionHandler {
    private var offSet: Int = 0

    private val _products = MutableLiveData<UiState<List<CartProduct>>>(UiState.None)
    val products: LiveData<UiState<List<CartProduct>>> get() = _products

    private val _errorHandler = MutableLiveData<ErrorEventState<String>>()
    val errorHandler: LiveData<ErrorEventState<String>> get() = _errorHandler

    fun loadProductByOffset() {
        thread {
            repository.findProductByPaging(offSet, PAGE_SIZE).onSuccess {
                if (_products.value is UiState.None) {
                    _products.postValue(UiState.Success(it))
                } else {
                    _products.postValue(
                        UiState.Success((_products.value as UiState.Success).data + it)
                    )
                }
                offSet++
            }.onFailure {
                _errorHandler.value = ErrorEventState(LOAD_ERROR)
            }
        }
    }

    companion object {
        const val LOAD_ERROR = "아이템을 끝까지 불러왔습니다"
        const val PAGE_SIZE = 20
    }

    override fun onPlus(cartProduct: CartProduct) {
        thread {
            val cartProducts = (_products.value as UiState.Success).data.map { it.copy() }
            val index = cartProducts.indexOfFirst { it.productId == cartProduct.productId }
            cartProducts[index].plusQuantity()

            repository.saveCart(Cart(cartProducts[index].productId, cartProducts[index].quantity!!))
                .onSuccess {
                    _products.postValue(UiState.Success(cartProducts))
                }
                .onFailure {
                    _errorHandler.postValue(ErrorEventState("아이템 증가 오류"))
                }
        }
    }

    override fun onMinus(cartProduct: CartProduct) {
        thread {
            val cartProducts = (_products.value as UiState.Success).data.map { it.copy() }
            val index = cartProducts.indexOfFirst { it.productId == cartProduct.productId }
            cartProducts[index].minusQuantity()

            if(cartProducts[index].quantity != null) {
                repository.saveCart(
                    Cart(
                        cartProducts[index].productId,
                        cartProducts[index].quantity!!
                    )
                )
                    .onSuccess {
                        _products.postValue(UiState.Success(cartProducts))
                    }
                    .onFailure {
                        _errorHandler.postValue(ErrorEventState("아이템 증가 오류"))
                    }
            } else {
                repository.deleteCart(
                    cartProducts[index].productId
                ).onSuccess {
                    _products.postValue(UiState.Success(cartProducts))
                }.onFailure {
                    _errorHandler.postValue(ErrorEventState("아이템 증가 오류"))
                }
            }
        }
    }
}
