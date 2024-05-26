package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.OnItemQuantityChangeListener

class ProductDetailViewModel(
    private val productId: Long,
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ViewModel(), OnItemQuantityChangeListener {
    private val _currentProduct: MutableLiveData<Product> = MutableLiveData()
    val currentProduct: LiveData<Product> get() = _currentProduct

    private val _productCount: MutableLiveData<Int> = MutableLiveData()
    val productCount: LiveData<Int> get() = _productCount

    private val _latestProduct: MutableLiveData<Product> = MutableLiveData()
    val latestProduct: LiveData<Product> get() = _latestProduct

    fun loadAll() {
        _currentProduct.value = shoppingProductsRepository.loadProduct(id = productId)
        _productCount.value = 1

        // 마지막으로 본 상품
        try {
            _latestProduct.setValue(productHistoryRepository.loadLatestProduct())
        } catch (e: NoSuchElementException) {
            // TODO: 이 때는 최근 상품이 없을 때임
            _latestProduct.setValue(Product.NULL)
        }
        productHistoryRepository.saveProductHistory(productId)
    }

    fun addProductToCart() {
        try {
            // TODO: 상품을 증가시킬 때 특정 수량만큼 추가할 수 있도록 변경해야 함
            repeat(productCount.value!!) {
                shoppingProductsRepository.increaseShoppingCartProduct(productId)
            }

            // TODO: "이미 장바구니에 담긴 상품이어서 수량을 업데이트했음" 토스트 메시지 띄우기
        } catch (e: NoSuchElementException) {
            // TODO: 상품을 추가할 때 특정 수량만큼 추가할 수 있도록 변경해야 함
            shoppingProductsRepository.addShoppingCartProduct(productId)
            repeat(productCount.value!! - 1) {
                shoppingProductsRepository.increaseShoppingCartProduct(productId)
            }

            // TODO: "장바구니에 담겼습니다" 토스트 메시지 띄우기
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

    companion object {
        private const val TAG = "ProductDetailViewModel"
    }
}
