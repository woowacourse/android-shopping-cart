package woowacourse.shopping.viewmodel.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.products.ShoppingCartItem
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository

class CartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _currentPageItems = MutableLiveData<List<ShoppingCartItem>>()
    val currentPageItems: LiveData<List<ShoppingCartItem>> get() = _currentPageItems

    private val _pageCount = MutableLiveData(0)
    val pageCount: LiveData<Int> get() = _pageCount

    init {
//        refreshCartState()
    }

    fun loadPage() {
        val pageCount = _pageCount.value ?: 0
        shoppingCartRepository.singlePage(pageCount, PAGE_SIZE) { shoppingCart ->

            val cartIds = shoppingCart.map { it.productId }
            val products = productRepository.getProductsById(cartIds)

            val items =
                products.map { product ->
                    val shoppingCart = shoppingCart.find { it.productId == product.id }
                    ShoppingCartItem(product, shoppingCart?.quantity ?: 0)
                }
            // _currentPageItems.value = items
            _currentPageItems.postValue(items)
        }
    }

    fun updateQuantity(
        productId: Int,
        quantity: Int,
    ) {
        // 현재 아이템 목록 가져오기
        val currentItems = _currentPageItems.value ?: return

        // DB 업데이트
        shoppingCartRepository.updateCart(productId, quantity)

        // 변경할 아이템의 인덱스
        val index = currentItems.indexOfFirst { it.product.id == productId }

        // 불변 리스트이므로 가변 리스트로 새로운 객체 생성
        val mutableItems = currentItems.toMutableList()

        // 변경할 인덱스의 아이템 수량 수정
        mutableItems[index] = currentItems[index].copy(quantity = quantity)

        // 라이브 데이터 업데이트
        _currentPageItems.value = mutableItems
    }

    fun removeFromCart(productId: Int) {
        val currentItems = _currentPageItems.value ?: return
        shoppingCartRepository.removeCart(productId)

        val index = currentItems.indexOfFirst { it.product.id == productId }
        val mutableItems = currentItems.toMutableList()
        mutableItems.removeAt(index)
        _currentPageItems.value = mutableItems
    }

//    fun loadNextPage() {
//        val cartState = _cartState.value ?: CartState()
//        val allItems = cartState.getAllShoppingCartItem()
//        val totalPages = (allItems.size + pageSize - 1) / pageSize
//        val currentPage = _pageCount.value ?: 1
//
//        if (currentPage < totalPages) {
//            loadPage(currentPage + 1)
//        }
//    }
//
//    fun loadPreviousPage() {
//        val currentPage = _pageCount.value ?: 1
//        if (currentPage > 1) {
//            loadPage(currentPage - 1)
//        }
//    }
//
//    fun isFirstPage(): Boolean = (_pageCount.value ?: 1) == 1
//
//    fun isLastPage(): Boolean {
//        val cartState = _cartState.value ?: CartState()
//        val allItems = cartState.getAllShoppingCartItem()
//        val totalPages = if (allItems.isEmpty()) 1 else (allItems.size + pageSize - 1) / pageSize
//        return (_pageCount.value ?: 1) == totalPages
//    }
//
//    fun isOnlyOnePage(): Boolean {
//        val cartState = _cartState.value ?: CartState()
//        return cartState.getAllShoppingCartItem().size <= pageSize
//    }
//
//    fun refreshCartState() {
//        _cartState.value = shoppingCartRepository.getCurrentState()
//        loadPage(1)
//    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
