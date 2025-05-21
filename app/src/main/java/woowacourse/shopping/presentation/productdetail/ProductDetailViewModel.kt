package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.SingleLiveData
import woowacourse.shopping.presentation.cart.CartCounterClickListener

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
) : ViewModel(),
    CartCounterClickListener {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product
    private val _productCount: MutableLiveData<Int> = MutableLiveData(1)
    val productCount: LiveData<Int> = _productCount
    private val _insertProductResult: MutableLiveData<ResultState<Unit>> = MutableLiveData()
    val insertProductResult: LiveData<ResultState<Unit>> = _insertProductResult
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    fun fetchData(product: Product) {
        _product.value = product
    }

    fun addToCart(product: Product) {
        cartRepository.insertOrIncrease(product) { result ->
            result
                .onSuccess { _insertProductResult.postValue(ResultState.Success(it)) }
                .onFailure { _insertProductResult.postValue(ResultState.Failure()) }
        }
    }

    override fun onClickMinus(id: Long) {
        val currentCount = _productCount.value ?: return
        if (currentCount == 1) {
            _toastMessage.value = R.string.product_detail_toast_invalid_quantity
            return
        }
        _productCount.value = currentCount - 1
    }

    override fun onClickPlus(id: Long) {
        val currentCount = _productCount.value ?: return
        _productCount.value = currentCount + 1
    }
}
