package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Recent
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.presentation.ui.EventState
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.UpdateUiModel
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val repository: Repository,
) : ViewModel(), DetailActionHandler {
    private val _product = MutableLiveData<UiState<CartProduct>>(UiState.None)
    val product: LiveData<UiState<CartProduct>> get() = _product

    private val _recentProduct = MutableLiveData<UiState<RecentProduct>>(UiState.None)
    val recentProduct: LiveData<UiState<RecentProduct>> get() = _recentProduct

    private val _errorHandler = MutableLiveData<EventState<String>>()
    val errorHandler: LiveData<EventState<String>> get() = _errorHandler

    private val _cartHandler = MutableLiveData<EventState<UpdateUiModel>>()
    val cartHandler: LiveData<EventState<UpdateUiModel>> get() = _cartHandler

    private val _navigateHandler = MutableLiveData<EventState<Long>>()
    val navigateHandler: LiveData<EventState<Long>> get() = _navigateHandler

    private val updateUiModel: UpdateUiModel = UpdateUiModel()

    fun findCartProductById(id: Long) {
        thread {
            repository.findProductById(id).onSuccess {
                if (it == null) {
                    _errorHandler.postValue(EventState(PRODUCT_NOT_FOUND))
                } else {
                    _product.postValue(UiState.Success(it))
                    saveRecentProduct(it)
                }
            }.onFailure {
                _errorHandler.value = EventState(PRODUCT_NOT_FOUND)
            }
        }
    }

    fun findOneRecentProduct() {
        thread {
            repository.findOne().onSuccess {
                if (it == null) {
                    _recentProduct.postValue(UiState.None)
                } else {
                    _recentProduct.postValue(UiState.Success(it))
                }
            }.onFailure {
                _errorHandler.postValue(EventState(PRODUCT_NOT_FOUND))
            }
        }
    }

    override fun onAddToCart(cartProduct: CartProduct) {
        thread {
            updateUiModel.add(cartProduct.productId, cartProduct)

            if (cartProduct.quantity > 0) {
                repository.saveCart(
                    Cart(
                        cartProduct.productId,
                        cartProduct.quantity,
                    ),
                )
                    .onSuccess {
                        _product.postValue(UiState.Success(cartProduct))
                    }
                    .onFailure {
                        _errorHandler.postValue(EventState("아이템 증가 오류"))
                    }
            } else {
                repository.deleteCart(
                    cartProduct.productId,
                ).onSuccess {
                    _product.postValue(UiState.Success(cartProduct))
                }.onFailure {
                    _errorHandler.postValue(EventState("아이템 증가 오류"))
                }
            }
            _cartHandler.postValue(EventState(updateUiModel))
        }
    }

    override fun onNavigateToDetail(productId: Long) {
        _navigateHandler.value = EventState(productId)
    }

    override fun onPlus(cartProduct: CartProduct) {
        thread {
            cartProduct.plusQuantity()
            _product.postValue(UiState.Success(cartProduct))
        }
    }

    override fun onMinus(cartProduct: CartProduct) {
        thread {
            cartProduct.minusQuantity()
            _product.postValue(UiState.Success(cartProduct))
        }
    }

    fun saveRecentProduct(cartProduct: CartProduct) {
        thread {
            repository.saveRecent(
                Recent(
                    cartProduct.productId,
                    System.currentTimeMillis(),
                ),
            ).onSuccess {
            }.onFailure {
                _errorHandler.postValue(EventState("최근 아이템 추가 에러"))
            }
        }
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "PRODUCT NOT FOUND"
    }
}
