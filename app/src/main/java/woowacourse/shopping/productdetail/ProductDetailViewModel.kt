package woowacourse.shopping.productdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.GetLastProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productdetail.uimodel.CountResultUiModel
import woowacourse.shopping.productdetail.uimodel.CountState
import woowacourse.shopping.productdetail.uimodel.RecentProductState
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingRepository
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import java.time.LocalDateTime

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _countState: MutableLiveData<CountState> = MutableLiveData()
    val countState: LiveData<CountState> get() = _countState

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _isAddSuccess: MutableSingleLiveData<Boolean> = MutableSingleLiveData(false)
    val isAddSuccess: SingleLiveData<Boolean> get() = _isAddSuccess

    private val _recentProductState: MutableLiveData<RecentProductState> = MutableLiveData()
    val recentProductState: LiveData<RecentProductState> get() = _recentProductState

    private fun currentCountResult(): CountResultUiModel = _countState.value?.countResult ?: error("초기화 이후에 메서드를 실행해주세요")

    private fun currentProduct(): Product = _product.value ?: error("초기화 이후에 메서드를 실행해주세요")

    fun loadProductDetail(productId: Long) {
        runCatching {
            productRepository.productById(productId)
        }.onSuccess {
            _product.value = it
            _countState.value = CountState.ShowCount(CountResultUiModel(1, it.price))
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun minusProductCount() {
        val shoppingCartItem = ShoppingCartItem(currentProduct(), currentCountResult().count)
        when (val result = shoppingCartItem.decreaseQuantity()) {
            is QuantityUpdate.Success -> {
                _countState.value =
                    CountState.ShowCount(
                        result.value.toCountResult(),
                    )
            }

            QuantityUpdate.Failure ->
                _countState.value =
                    CountState.MinusFail(currentCountResult())
        }
    }

    fun plusProductCount() {
        val shoppingCartItem = ShoppingCartItem(currentProduct(), currentCountResult().count)
        when (val result = shoppingCartItem.increaseQuantity()) {
            is QuantityUpdate.Success -> {
                _countState.value = CountState.ChangeItemCount(result.value.toCountResult())
            }

            QuantityUpdate.Failure ->
                _countState.value =
                    CountState.PlusFail(currentCountResult())
        }
    }

    fun addProductToCart() {
        runCatching {
            val shoppingCartItem = ShoppingCartItem(currentProduct(), currentCountResult().count)
            shoppingRepository.addCartItem(shoppingCartItem)
        }.onSuccess {
            _isAddSuccess.setValue(true)
        }.onFailure {
            _isAddSuccess.setValue(false)
        }
    }

    fun updateRecentProduct(productId: Long) {
        runCatching {
            productRepository.addRecentProduct(productId, LocalDateTime.now())
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun loadRecentProduct() {
        runCatching {
            productRepository.lastRecentProduct()
        }.onSuccess { state ->
            when (state) {
                is GetLastProduct.Success,
                -> _recentProductState.value = isSameProduct(state.value)

                GetLastProduct.Fail,
                -> _recentProductState.value = RecentProductState.NoRecentProduct
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun isSameProduct(recent: RecentProduct): RecentProductState =
        if (recent.product.id == currentProduct().id) {
            RecentProductState.Same
        } else {
            RecentProductState.Show(recent.name, recent.product.id)
        }
}
