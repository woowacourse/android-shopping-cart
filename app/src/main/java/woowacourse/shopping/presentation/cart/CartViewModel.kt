package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    private val pageSize = 5
    
    private val _shoppingCart = MutableLiveData<List<CartItem>>()
    val shoppingCart: LiveData<List<CartItem>> = _shoppingCart
    
    private val _currentPage = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage
    
    private val _isFirstPage = MutableLiveData(true)
    val isFirstPage: LiveData<Boolean> = _isFirstPage
    
    private val _isLastPage = MutableLiveData(false)
    val isLastPage: LiveData<Boolean> = _isLastPage
    
    private val _isPageControlVisible = MutableLiveData<Boolean>()
    val isPageControlVisible: LiveData<Boolean> = _isPageControlVisible
    
    init {
        loadPage(_currentPage.value ?: 0)
    }
    
    private fun updatePageControlVisibility() {
        val totalItems = repository.findAll().items.size
        _isPageControlVisible.postValue(totalItems > pageSize)
    }
    
    private fun loadPage(page: Int) {
        val totalItems = repository.findAll().items.size
        val maxPage = totalItems / pageSize
        
        _currentPage.value = page.coerceIn(0, maxPage)
        _isFirstPage.value = _currentPage.value == 0
        _isLastPage.value = _currentPage.value == maxPage
        
        val shoppingCart = repository.findAllPagedItems(_currentPage.value ?: 0, pageSize)
        _shoppingCart.postValue(shoppingCart.items)
        updatePageControlVisibility()
    }
    
    fun loadNextPage() {
        val nextPage = (_currentPage.value ?: 0) + 1
        loadPage(nextPage)
    }
    
    fun loadPreviousPage() {
        val prevPage = (_currentPage.value ?: 0) - 1
        loadPage(prevPage)
    }
    
    fun deleteItem(itemId: Long) {
        repository.delete(itemId)
        loadPage(_currentPage.value ?: 0)
    }
}
