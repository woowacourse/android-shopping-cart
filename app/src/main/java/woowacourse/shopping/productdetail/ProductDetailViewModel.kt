package woowacourse.shopping.productdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.RecentlyViewedProductRepositoryInterface
import woowacourse.shopping.ShoppingCartRepositoryInterface
import woowacourse.shopping.ViewModelQuantityActions
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.uimodel.ProductUiModel
import woowacourse.shopping.uimodel.toProductUiModel

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepositoryInterface,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepositoryInterface
) : ViewModel(), ViewModelQuantityActions {
    private lateinit var product: Product
    private var quantity: Quantity = Quantity()

    private val _productUi: MutableLiveData<ProductUiModel> = MutableLiveData()
    val productUi: LiveData<ProductUiModel> get() = _productUi

    private val _quantityUi: MutableLiveData<Int> = MutableLiveData()
    val quantityUi: LiveData<Int> get() = _quantityUi

    private val _isAddSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isAddSuccess: LiveData<Boolean> get() = _isAddSuccess

    private val _addedItems: MutableLiveData<Set<Long>> = MutableLiveData()
    val addedItems: LiveData<Set<Long>> get() = _addedItems

    private val _latestViewedProduct: MutableLiveData<ProductUiModel> = MutableLiveData()
    val latestViewedProduct: LiveData<ProductUiModel> get() = _latestViewedProduct

    fun loadProductDetail(productId: Long) {
        runCatching {
            productRepository.productById(productId)
        }.onSuccess {
            product = it
            _productUi.value = it.toProductUiModel()
            _quantityUi.value = quantity.value
            addToRecentlyViewedProduct(productId)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun addToRecentlyViewedProduct(productId: Long) {
        runCatching {
            val product = productRepository.productById(productId)
            recentlyViewedProductRepository.addRecentlyViewedProduct(product)
        }.onSuccess {
            Log.d(this::class.java.simpleName, "Successfully add to recent list: id=$productId")
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun showLatestViewedProduct(recentProductClicked: Boolean) {
        if (!recentProductClicked) {
            runCatching {
                recentlyViewedProductRepository.lastlyViewedProduct()
            }.onSuccess {
                _latestViewedProduct.value = it?.toProductUiModel()
            }.onFailure {
                Log.d(this::class.java.simpleName, "$it")
            }
        }
    }

    override fun plusQuantity(productId: Long) {
        runCatching {
            quantity = quantity.increase()
        }.onSuccess {
            _quantityUi.value = quantity.value
            updateProductPrice()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    override fun minusQuantity(productId: Long) {
        runCatching {
            quantity = quantity.decrease()
        }.onSuccess {
            _quantityUi.value = quantity.value
            updateProductPrice()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun updateProductPrice() {
        val updatedPrice = product.price.times(quantity.value)
        _productUi.value =
            productUi.value?.copy(price = updatedPrice.value)
                ?: product.toProductUiModel()
    }

    fun addProductToCart() {
        runCatching {
            shoppingCartRepository.addShoppingCartItem(product, quantity.value)
        }.onSuccess {
            val productId = requireNotNull(product.id)
            _addedItems.value = setOf(productId)
            _isAddSuccess.value = true
        }.onFailure {
            _isAddSuccess.value = false
        }
    }
}
