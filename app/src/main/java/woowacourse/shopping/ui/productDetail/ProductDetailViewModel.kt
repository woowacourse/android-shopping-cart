package woowacourse.shopping.ui.productDetail

import android.os.Handler
import android.os.Looper
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
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val productId: Long,
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ViewModel(), OnItemQuantityChangeListener, OnProductItemClickListener {
    private val uiHandler = Handler(Looper.getMainLooper())

    private val _currentProduct: MutableLiveData<Product> = MutableLiveData()
    val currentProduct: LiveData<Product> get() = _currentProduct

    private val _productCount: MutableLiveData<Int> = MutableLiveData(1)
    val productCount: LiveData<Int> get() = _productCount

    private val _latestProduct: MutableLiveData<Product> = MutableLiveData()
    val latestProduct: LiveData<Product> get() = _latestProduct

    private var _detailProductDestinationId: MutableSingleLiveData<Long> = MutableSingleLiveData()
    val detailProductDestinationId: SingleLiveData<Long> get() = _detailProductDestinationId

    fun loadAll() {
        thread {
            val currentProduct = shoppingProductsRepository.loadProduct(id = productId)
            val latestProduct =
                try {
                    productHistoryRepository.loadLatestProduct()
                } catch (e: NoSuchElementException) {
                    Product.NULL
                }

            uiHandler.post {
                _currentProduct.value = currentProduct
                _productCount.value = 1
                _latestProduct.value = latestProduct
            }

            productHistoryRepository.saveProductHistory(productId)
        }
    }

    fun addProductToCart() {
        thread {
            repeat(productCount.value!!) {
                // TODO: 상품을 증가시킬 때 특정 수량만큼 추가할 수 있도록 변경해야 함
                shoppingProductsRepository.increaseShoppingCartProduct(productId)
                // TODO: "장바구니에 담겼습니다" 토스트 메시지 띄우기
            }
        }
    }

    override fun onIncrease(productId: Long) {
        _productCount.value = _productCount.value?.plus(1)
    }

    override fun onDecrease(productId: Long) {
        val currentProductCount = _productCount.value
        if (currentProductCount == 1) {
            return
        }
        _productCount.value = _productCount.value?.minus(1)
    }

    override fun onClick(productId: Long) {
        _detailProductDestinationId.setValue(productId)
    }

    companion object {
        private const val TAG = "ProductDetailViewModel"

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
