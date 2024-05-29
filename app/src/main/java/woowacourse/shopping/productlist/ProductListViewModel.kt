package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.ShoppingCartRepositoryInterface
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productdetail.SingleLiveEvent
import woowacourse.shopping.uimodel.ProductUiModel
import woowacourse.shopping.uimodel.toProductUiModel

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepositoryInterface,
) : ViewModel() {
    private val _productUiModels: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val productUiModels: LiveData<List<ProductUiModel>> get() = _productUiModels

    private val _totalItemQuantity: MutableLiveData<Int> = MutableLiveData(0)
    val totalItemQuantity: LiveData<Int> get() = _totalItemQuantity

    private val _updatedItemsId: SingleLiveEvent<Set<Long>> = SingleLiveEvent()
    val updatedItemsId: LiveData<Set<Long>> get() = _updatedItemsId

    private val _currentSize: MutableLiveData<Int> = MutableLiveData()
    val currentSize: LiveData<Int> get() = _currentSize

    val totalSize: Int = productRepository.productsTotalSize()

    init {
        loadProducts(PRODUCTS_START_POSITION)
        loadCartItemQuantities()
    }

    private fun loadCartItemQuantities() {
        val updatedQuantities = shoppingCartRepository.cartItemQuantity()
        val ids = mutableSetOf<Long>()
        updatedQuantities.forEach { (productId, quantity) ->
            updateUiModelQuantity(productId, quantity)
            ids.add(productId)
        }
        _updatedItemsId.value = ids
        changeTotalItemQuantity()
    }

    fun loadProducts(startPosition: Int) {
        runCatching {
            productRepository.products(startPosition, PRODUCTS_OFFSET_SIZE)
        }.onSuccess { loadedProducts ->
            val currentUiItems = productUiModels.value.orEmpty()
            val idsToBeUpdated = mutableSetOf<Long>()
            loadedProducts.forEach {
                if (updatedItemsId.value?.contains(it.id) == true) {
                    idsToBeUpdated.add(it.id)
                }
            }
            _productUiModels.value = currentUiItems + loadedProducts.map { it.toProductUiModel() }
            acceptChangedItems(idsToBeUpdated)
            _currentSize.value = productUiModels.value?.size
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun addProductToCart(productId: Long) {
        runCatching {
            val cartItem = ShoppingCartItem(productRepository.productById(productId))
            shoppingCartRepository.addShoppingCartItem(cartItem.product, cartItem.quantity)
        }.onSuccess {
            updateItemQuantity(productId, PRODUCT_QUANTITY_ONE)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun plusProductOnCart(
        productId: Long,
        currentQuantity: Int,
    ) {
        runCatching {
            shoppingCartRepository.plusCartItemQuantity(productId)
        }.onSuccess {
            updateItemQuantity(productId, currentQuantity + PRODUCT_QUANTITY_ONE)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun minusProductOnCart(
        productId: Long,
        currentQuantity: Int,
    ) {
        if (currentQuantity == PRODUCT_QUANTITY_ONE) {
            deleteProductToCart(productId)
        } else {
            runCatching {
                shoppingCartRepository.minusCartItemQuantity(productId)
            }.onSuccess {
                updateItemQuantity(productId, currentQuantity + PRODUCT_QUANTITY_ONE)
            }.onFailure {
                Log.d(this::class.java.simpleName, "$it")
            }
        }
    }

    private fun deleteProductToCart(productId: Long) {
        runCatching {
            shoppingCartRepository.deleteShoppingCartItem(productId)
        }.onSuccess {
            updateItemQuantity(productId, ProductUiModel.PRODUCT_DEFAULT_QUANTITY)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun updateItemQuantity(
        productId: Long,
        newQuantity: Int,
    ) {
        changeTotalItemQuantity()
        updateUiModelQuantity(productId, newQuantity)
        _updatedItemsId.value = updatedItemsId.value.orEmpty() + setOf(productId)
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

    fun acceptChangedItems(changedItems: Set<Long>) {
        val updatedQuantities = shoppingCartRepository.cartItemQuantity(changedItems)
        updatedQuantities.forEach { (productId, changedQuantity) ->
            updateUiModelQuantity(productId, changedQuantity)
        }
        _updatedItemsId.value = updatedItemsId.value.orEmpty() + changedItems
        changeTotalItemQuantity()
    }

    companion object {
        private const val PRODUCTS_START_POSITION = 0
        private const val PRODUCTS_OFFSET_SIZE = 20
        private const val PRODUCT_QUANTITY_ONE = 1
    }
}
