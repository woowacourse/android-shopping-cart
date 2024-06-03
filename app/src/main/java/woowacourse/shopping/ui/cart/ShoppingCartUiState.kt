package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.Product

abstract class ShoppingCartUiState {
    abstract val currentPage: LiveData<Int>
    abstract val itemsInCurrentPage: LiveData<List<Product>>
    abstract val isLastPage: LiveData<Boolean>
}

data class DefaultShoppingCartUiState(
    override val currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
    override val itemsInCurrentPage: MutableLiveData<List<Product>> = MutableLiveData(emptyList()),
    override val isLastPage: MutableLiveData<Boolean> = MutableLiveData(false),
) : ShoppingCartUiState() {
    companion object {
        private const val FIRST_PAGE = 1
    }
}