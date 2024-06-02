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
import woowacourse.shopping.productdetail.uimodel.CountEvent
import woowacourse.shopping.productdetail.uimodel.CountResultUiModel
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
    private val _countEvent: MutableSingleLiveData<CountEvent> = MutableSingleLiveData()
    val countEvent: SingleLiveData<CountEvent> get() = _countEvent

    private val _count: MutableLiveData<CountResultUiModel> = MutableLiveData()
    val count: LiveData<CountResultUiModel> get() = _count

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _isAddSuccess: MutableSingleLiveData<Boolean> = MutableSingleLiveData(false)
    val isAddSuccess: SingleLiveData<Boolean> get() = _isAddSuccess

    private val _recentProductState: MutableLiveData<RecentProductState> = MutableLiveData()
    val recentProductState: LiveData<RecentProductState> get() = _recentProductState

    private fun currentCountResult(): CountResultUiModel = _count.value ?: error("초기화 이후에 메서드를 실행해주세요")

    private fun currentProduct(): Product = _product.value ?: error("초기화 이후에 메서드를 실행해주세요")

    fun initProductDetail(productId: Long) {
        _count.value ?: runCatching {
            productRepository.productById(productId)
        }.onSuccess {
            _product.value = it
            val count = MINIMUM_COUNT
            val price = it.price
            _count.value = CountResultUiModel(count, price)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun minusProductCount() {
        val shoppingCartItem = ShoppingCartItem(currentProduct(), currentCountResult().count)
        when (val result = shoppingCartItem.decreaseQuantity()) {
            is QuantityUpdate.Success -> {
                _count.value = result.value.toCountResult()
            }
            QuantityUpdate.CantChange -> _countEvent.setValue(CountEvent.MinusFail)
        }
    }

    fun plusProductCount() {
        val shoppingCartItem = ShoppingCartItem(currentProduct(), currentCountResult().count)
        when (val result = shoppingCartItem.increaseQuantity()) {
            is QuantityUpdate.Success -> {
                _count.value = result.value.toCountResult()
            }
            QuantityUpdate.CantChange -> _countEvent.setValue(CountEvent.PlusFail)
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

                GetLastProduct.NoRecentProduct,
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

    companion object {
        private const val MINIMUM_COUNT = 1
    }
}
