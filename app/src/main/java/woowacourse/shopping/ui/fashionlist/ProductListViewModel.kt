package woowacourse.shopping.ui.fashionlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartItemRepositoryImpl
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.HistoryRepositoryImpl
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val cartRepository = CartRepositoryImpl.get()
    private val cartItemRepository = CartItemRepositoryImpl.get()
    private val historyRepository = HistoryRepositoryImpl.get()
    private val _products = MutableLiveData<List<ProductListViewType>>(emptyList())
    val products: LiveData<List<ProductListViewType>> = _products

    private var pageNumber = 0

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    private val _cartItems = MutableLiveData<Map<Long, Int>>()
    val cartItems: LiveData<Map<Long, Int>> = _cartItems

    private val _isButtonVisible = MutableLiveData(true)
    val isButtonVisible: LiveData<Boolean> = _isButtonVisible

    private val _recentProducts = MutableLiveData<List<Product>>(emptyList())
    val recentProducts: LiveData<List<Product>> = _recentProducts

    init {
        loadProducts()
        loadCartItems()
        loadRecentProducts()
    }

    fun loadProducts() {
        val currentProducts =
            _products.value.orEmpty()
                .filterIsInstance<ProductListViewType.FashionProductItemType>()

        val cartMap = _cartItems.value.orEmpty()

        val newProducts =
            productRepository.productsByPageNumberAndSize(pageNumber++, 20)
                .map {
                    val quantity = cartMap[it.id] ?: 0
                    ProductListViewType.FashionProductItemType(
                        product = it,
                        quantity = quantity,
                        isButtonVisible = quantity == 0,
                    )
                }

        val productList =
            if (productRepository.canMoreLoad(pageNumber, 20)) {
                currentProducts + newProducts + ProductListViewType.LoadMoreType
            } else {
                currentProducts + newProducts
            }

        val recent =
            _recentProducts.value?.takeIf { it.isNotEmpty() }?.let {
                listOf(ProductListViewType.RecentProducts(it))
            } ?: emptyList()

        _products.value = recent + currentProducts + productList
    }

    fun loadCartItems() {
        thread {
            val items = cartItemRepository.getCartItems()
            val cartMap = items.associateBy { it.id }
            _cartItems.postValue(cartMap.mapValues { it.value.quantity })

            val updatedProducts =
                _products.value.orEmpty().map {
                    if (it is ProductListViewType.FashionProductItemType) {
                        val cartItem = cartMap[it.product.id]
                        it.copy(
                            isButtonVisible = cartItem == null || cartItem.quantity == 0,
                            quantity = cartItem?.quantity ?: 0,
                        )
                    } else {
                        it
                    }
                }
            _products.postValue(updatedProducts)
        }
    }

    fun loadRecentProducts() {
        thread {
            val items = historyRepository.getRecentProducts()
            _recentProducts.postValue(items)

            val rest = _products.value.orEmpty()

            val updated = listOf(ProductListViewType.RecentProducts(items)) + rest
            _products.postValue(updated)
        }
    }

    fun addRecentProduct(id: Long) {
        thread {
            historyRepository.insert(id)
        }
    }

    fun onFloatingButtonClick(productId: Long) {
        val currentList = _products.value.orEmpty()

        val updatedList =
            currentList.map {
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
            cartItemRepository.insert(CartItem(product.id, product, 1))
            loadCartItems()
        }
    }

    fun increaseQuantity(id: Long) {
        thread {
            cartItemRepository.increaseQuantity(id)
        }
    }

    fun decreaseQuantity(id: Long) {
        thread {
            cartItemRepository.decreaseQuantity(id)
        }
    }

//    private fun updateCartItemState(
//        productId: Long,
//        quantity: Int,
//    ) {
//        _cartItems.postValue(
//            _cartItems.value.orEmpty().toMutableMap().apply {
//                if (quantity == 0) {
//                    remove(productId)
//                } else {
//                    put(productId, quantity)
//                }
//            },
//        )
//
//        val updatedList =
//            _products.value?.map {
//                if (it is ProductListViewType.FashionProductItemType && it.product.id == productId) {
//                    it.copy(quantity = quantity, isButtonVisible = quantity == 0)
//                } else {
//                    it
//                }
//            } ?: return
//
//        _products.postValue(updatedList)
//    }
//
//    private fun updateProductQuantityInList(
//        productId: Long,
//        quantity: Int,
//    ) {
//        val updatedList =
//            _products.value?.map {
//                if (it is ProductListViewType.FashionProductItemType && it.product.id == productId) {
//                    it.copy(quantity = quantity, isButtonVisible = quantity == 0)
//                } else {
//                    it
//                }
//            } ?: return
//        _products.postValue(updatedList)
//    }
}
