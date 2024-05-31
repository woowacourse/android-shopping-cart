package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener

class ProductDetailViewModel(
    private val productId: Long,
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ViewModel(), OnItemQuantityChangeListener, OnProductItemClickListener {
    private val _currentProduct: MutableLiveData<Product> = MutableLiveData()
    val currentProduct: LiveData<Product> get() = _currentProduct

    private val _productCount: MutableLiveData<Int> = MutableLiveData(FIRST_QUANTITY)
    val productCount: LiveData<Int> get() = _productCount

    private val _latestProduct: MutableLiveData<Product> = MutableLiveData()
    val latestProduct: LiveData<Product> get() = _latestProduct

    private var _detailProductDestinationId: MutableSingleLiveData<Long> = MutableSingleLiveData()
    val detailProductDestinationId: SingleLiveData<Long> get() = _detailProductDestinationId

    fun loadAll() {
        shoppingProductsRepository.loadProductAsync(productId) { product ->
            _currentProduct.postValue(product)
            _productCount.postValue(1)
        }
        productHistoryRepository.loadLatestProductAsync { latestProductId ->
            shoppingProductsRepository.loadProductAsync(latestProductId) { latestProduct ->
                _latestProduct.postValue(latestProduct)
            }
        }

        productHistoryRepository.saveProductHistoryAsync(productId) {
            // TODO: 담겼다는 메시지
        }
    }

    fun addProductToCart() {
        repeat(productCount.value ?: 0) {
            shoppingProductsRepository.increaseShoppingCartProductAsync(productId) {
                // TODO: 여기서 담겼다는 메시지? 한 번에 여러개 넣으면 터짐..
            }
        }
    }

    override fun onIncrease(productId: Long) {
        _productCount.value = _productCount.value?.plus(SINGLE_CHANGE_AMOUNT)
    }

    override fun onDecrease(productId: Long) {
        val currentProductCount = _productCount.value
        if (currentProductCount == FIRST_QUANTITY) {
            return
        }
        _productCount.value = _productCount.value?.minus(SINGLE_CHANGE_AMOUNT)
    }

    override fun onClick(productId: Long) {
        _detailProductDestinationId.setValue(productId)
    }

    companion object {
        private const val TAG = "ProductDetailViewModel"
        private const val FIRST_QUANTITY = 1
        private const val SINGLE_CHANGE_AMOUNT = 1

        fun factory(
            productId: Long,
            shoppingProductsRepository: ShoppingProductsRepository =
                DefaultShoppingProductRepository(
                    productsSource = ShoppingApp.productSource,
                    cartSource = ShoppingApp.cartSource,
                ),
            historyRepository: ProductHistoryRepository =
                DefaultProductHistoryRepository(
                    productHistoryDataSource = ShoppingApp.historySource,
                    productDataSource = ShoppingApp.productSource,
                ),
        ): UniversalViewModelFactory {
            return UniversalViewModelFactory {
                ProductDetailViewModel(
                    productId = productId,
                    shoppingProductsRepository = shoppingProductsRepository,
                    productHistoryRepository = historyRepository,
                )
            }
        }
    }
}
