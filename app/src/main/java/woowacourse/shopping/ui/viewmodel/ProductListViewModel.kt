package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.ui.fashionlist.ProductListViewType
import kotlin.concurrent.thread

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val cartRepository = CartRepository.get()
    private val _products = MutableLiveData<List<ProductListViewType>>(emptyList())
    val products: LiveData<List<ProductListViewType>> = _products

    private var pageNumber = 0

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    private val _cartItems = MutableLiveData<Map<Long, Int>>()
    val cartItems: LiveData<Map<Long, Int>> = _cartItems

    private val _isButtonVisible = MutableLiveData(true)
    val isButtonVisible: LiveData<Boolean> = _isButtonVisible

    init {
        loadProducts()
        loadCartItems()
    }

    fun loadProducts() {
        val currentProducts = _products.value.orEmpty()
            .filterIsInstance<ProductListViewType.FashionProductItemType>()

        val cartMap = _cartItems.value.orEmpty()

        val newProducts = productRepository.productsByPageNumberAndSize(pageNumber++, 20)
            .map {
                val quantity = cartMap[it.id] ?: 0
                ProductListViewType.FashionProductItemType(
                    product = it,
                    quantity = quantity,
                    isButtonVisible = quantity == 0
                )
            }

        _products.value = if (productRepository.canMoreLoad(pageNumber, 20)) {
            currentProducts + newProducts + ProductListViewType.LoadMoreType
        } else {
            currentProducts + newProducts
        }
    }

    fun loadCartItems() {
        thread {
            val items = cartRepository.getCartItems()
            val cartMap = items.associateBy { it.id }
            _cartItems.postValue(cartMap.mapValues { it.value.quantity })

            val updatedProducts = _products.value.orEmpty().map {
                if (it is ProductListViewType.FashionProductItemType) {
                    val cartItem = cartMap[it.product.id]
                    it.copy(
                        isButtonVisible = cartItem == null || cartItem.quantity == 0,
                        quantity = cartItem?.quantity ?: 0
                    )
                } else it
            }
            _products.postValue(updatedProducts)
        }
    }

    fun onFloatingButtonClick(productId: Long) {
        val currentList = _products.value.orEmpty()

        val updatedList = currentList.map {
            if (it is ProductListViewType.FashionProductItemType && it.product.id == productId) {
                it.copy(isButtonVisible = false)
            } else {
                it
            }
        }
        _products.value = updatedList
    }

    fun add(product: Product) {
        thread {
            cartRepository.insert(product)
            loadCartItems()
        }
    }

    fun increaseQuantity(id: Long) {
        thread {
            cartRepository.increaseQuantity(id)
            val cartItem = cartRepository.findById(id)
            if (cartItem != null) {
                updateCartItemState(id, cartItem.quantity)
                updateProductQuantityInList(id, cartItem.quantity)
            }
        }
    }

    fun decreaseQuantity(id: Long) {
        thread {
            val cartItem = cartRepository.findById(id) ?: return@thread

            if (cartItem.quantity == 1) {
                cartRepository.delete(cartItem.id)
                updateCartItemState(id, 0)
            } else {
                cartRepository.decreaseQuantity(id)

                val updated = cartRepository.findById(id)
                updateCartItemState(id, updated?.quantity ?: 1)
            }
        }
    }

    private fun updateCartItemState(productId: Long, quantity: Int) {
        _cartItems.postValue(
            _cartItems.value.orEmpty().toMutableMap().apply {
                if (quantity == 0) {
                    remove(productId)
                } else {
                    put(productId, quantity)
                }
            }
        )

        val updatedList = _products.value?.map {
            if (it is ProductListViewType.FashionProductItemType && it.product.id == productId) {
                it.copy(quantity = quantity, isButtonVisible = quantity == 0)
            } else it
        } ?: return

        _products.postValue(updatedList)
    }

    private fun updateProductQuantityInList(productId: Long, quantity: Int) {
        val updatedList = _products.value?.map {
            if (it is ProductListViewType.FashionProductItemType && it.product.id == productId) {
                it.copy(quantity = quantity, isButtonVisible = quantity == 0)
            } else it
        } ?: return
        _products.postValue(updatedList)
    }
}
