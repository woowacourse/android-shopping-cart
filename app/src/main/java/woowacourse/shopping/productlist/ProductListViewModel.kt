package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingCartRepository
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productdetail.SingleLiveEvent
import woowacourse.shopping.uimodel.ProductUiModel
import woowacourse.shopping.uimodel.toProductUiModel

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productUiModels: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val productUiModels: LiveData<List<ProductUiModel>> get() = _productUiModels

    private val _totalItemQuantity: MutableLiveData<Int> = MutableLiveData(0)
    val totalItemQuantity: LiveData<Int> get() = _totalItemQuantity

    private val _updatedItemsId: SingleLiveEvent<List<Long>> = SingleLiveEvent()
    val updatedItemsId: LiveData<List<Long>> get() = _updatedItemsId

    private val _currentSize: MutableLiveData<Int> = MutableLiveData()
    val currentSize: LiveData<Int> get() = _currentSize

    val totalSize: Int = productRepository.productsTotalSize()

    init {
        loadProducts(PRODUCTS_START_POSITION)
        _totalItemQuantity.value = shoppingCartRepository.cartTotalItemQuantity()
    }

    fun loadProducts(startPosition: Int) {
        runCatching {
            productRepository.products(startPosition, PRODUCTS_OFFSET_SIZE)
        }.onSuccess { loadedProducts ->
            val currentUiItems = productUiModels.value ?: emptyList()
            _productUiModels.value =
                currentUiItems + loadedProducts.map { it.toProductUiModel() }
            _currentSize.value = productUiModels.value?.size
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun addProductToCart(productId: Long) {
        runCatching {
            val cartItem = ShoppingCartItem(productRepository.productById(productId))
            val userId = shoppingCartRepository.userId()
            val shoppingCart = shoppingCartRepository.shoppingCart(userId)
            shoppingCartRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
        }.onSuccess {
            changeTotalItemQuantity()
            updateUiModelQuantity(productId, PRODUCT_QUANTITY_ONE)
            _updatedItemsId.value = listOf(productId)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun plusProductOnCart(
        productId: Long,
        currentQuantity: Int,
    ) {
        updateItemQuantity(productId, currentQuantity + PRODUCT_QUANTITY_ONE)
    }

    fun minusProductOnCart(
        productId: Long,
        currentQuantity: Int,
    ) {
        if (currentQuantity == PRODUCT_QUANTITY_ONE) {
            deleteProductToCart(productId)
        } else {
            updateItemQuantity(productId, currentQuantity - PRODUCT_QUANTITY_ONE)
        }
    }

    private fun updateItemQuantity(
        productId: Long,
        newQuantity: Int,
    ) {
        runCatching {
            val cartItem = ShoppingCartItem(productRepository.productById(productId), newQuantity)
            val userId = shoppingCartRepository.userId()
            val shoppingCart = shoppingCartRepository.shoppingCart(userId)
            shoppingCartRepository.updateShoppingCart(shoppingCart.updateItem(cartItem))
        }.onSuccess {
            changeTotalItemQuantity()
            updateUiModelQuantity(productId, newQuantity)
            _updatedItemsId.value = listOf(productId)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun deleteProductToCart(productId: Long) {
        runCatching {
            shoppingCartRepository.deleteShoppingCartItem(productId)
        }.onSuccess {
            changeTotalItemQuantity()
            updateUiModelQuantity(productId, ProductUiModel.PRODUCT_DEFAULT_QUANTITY)
            _updatedItemsId.value = listOf(productId)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun changeTotalItemQuantity() {
        _totalItemQuantity.value = shoppingCartRepository.cartTotalItemQuantity()
    }

    private fun updateUiModelQuantity(
        productId: Long,
        newQuantity: Int,
    ) {
        _productUiModels.value =
            _productUiModels.value?.map {
                if (productId == it.id) it.copy(quantity = newQuantity) else it
            }
    }

    companion object {
        private const val PRODUCTS_START_POSITION = 0
        private const val PRODUCTS_OFFSET_SIZE = 20
        private const val PRODUCT_QUANTITY_ONE = 1
    }
}
