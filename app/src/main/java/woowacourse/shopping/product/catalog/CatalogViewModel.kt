package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.MockProducts.mockProducts

class CatalogViewModel : ViewModel() {
    private val _catalogProducts = MutableLiveData<List<ProductUiModel>>(emptyList<ProductUiModel>())
    val catalogProducts: LiveData<List<ProductUiModel>> = _catalogProducts

    val page = MutableLiveData<Int>(INITIAL_PAGE)

    init {
        loadCatalogProducts(INITIAL_PAGE)
    }

    fun loadCatalogProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ) {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, mockProducts.size)
        val pagedProducts: List<ProductUiModel> =
            mockProducts.subList(fromIndex, toIndex).map { it.toUiModel() }
        _catalogProducts.value = _catalogProducts.value?.plus(pagedProducts)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
