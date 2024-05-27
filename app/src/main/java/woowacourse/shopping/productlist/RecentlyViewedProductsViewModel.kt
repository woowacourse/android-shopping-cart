package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.RecentlyViewedProductRepositoryInterface
import woowacourse.shopping.productdetail.SingleLiveEvent
import woowacourse.shopping.uimodel.ProductUiModel
import woowacourse.shopping.uimodel.toProductUiModel

class RecentlyViewedProductsViewModel(
    private val productRepository: ProductRepository,
    private val recentlyViewedProductsRepository: RecentlyViewedProductRepositoryInterface
) : ViewModel() {
    private val _productUiModels: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val productUiModels: LiveData<List<ProductUiModel>> get() = _productUiModels

    private val _lastlyViewedProduct: SingleLiveEvent<ProductUiModel> = SingleLiveEvent()
    val lastlyViewedProduct: LiveData<ProductUiModel> get() = _lastlyViewedProduct

    private val _isEmptyList: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEmptyList: LiveData<Boolean> get() = _isEmptyList

    fun loadRecentlyViewedProducts() {
        runCatching {
            recentlyViewedProductsRepository.recentTenProducts()
        }.onSuccess { recentProducts ->
            _productUiModels.value = recentProducts.map { it.toProductUiModel() }
            _isEmptyList.value = recentProducts.isEmpty()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun addRecentlyViewedProduct(productId: Long) {
        val product = productRepository.productById(productId)
        runCatching {
            recentlyViewedProductsRepository.addRecentlyViewedProduct(product)
        }.onSuccess {
            checkRecentlyViewed(product.toProductUiModel())
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun checkRecentlyViewed(product: ProductUiModel) {
        val productList = productUiModels.value.orEmpty().toMutableList()
        if (productList.contains(product)) {
            productList.remove(product)
        }
        productList.add(0, product)
        if (productList.size > 10) {
            productList.removeAt(productList.size - 1)
        }
        _productUiModels.value = productList
        _lastlyViewedProduct.value = product
    }
}
