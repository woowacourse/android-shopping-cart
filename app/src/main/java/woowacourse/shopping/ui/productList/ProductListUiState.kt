package woowacourse.shopping.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.Product

abstract class ProductListUiState {
    abstract val currentPage: LiveData<Int>
    abstract val loadedProducts: LiveData<List<Product>>
    abstract val productsHistory: LiveData<List<Product>>
    abstract val cartProductTotalCount: LiveData<Int>
    abstract val isLastPage: LiveData<Boolean>
}

data class DefaultProductListUiState(
    override val currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
    override val loadedProducts: MutableLiveData<List<Product>> = MutableLiveData(emptyList()),
    override val productsHistory: MutableLiveData<List<Product>> = MutableLiveData(emptyList()),
    override val cartProductTotalCount: MutableLiveData<Int> = MutableLiveData(0),
    override val isLastPage: MutableLiveData<Boolean> = MutableLiveData(false),
) : ProductListUiState() {

    companion object {
        private const val FIRST_PAGE = 1
    }
}
