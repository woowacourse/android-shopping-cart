package woowacourse.shopping.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _showPageButton = MutableLiveData(false)
    val showPageButton: LiveData<Boolean> get() = _showPageButton
    private var page: Int = 0
    val cart: LiveData<List<Goods>> = getProducts(page)
    val totalItemsCount: LiveData<Int> = cartRepository.getAllItemsSize()
    var totalItems: Int = 0

    fun delete(goods: Goods) {
        cartRepository.delete(goods)
    }

    fun addPage() {
        page++
    }

    fun getProducts(page: Int): LiveData<List<Goods>> = cartRepository.getPage(PAGE_SIZE, (page * PAGE_SIZE) - 1)

    fun updatePageButtonVisibility() {
        _showPageButton.value = totalItems > PAGE_SIZE
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
