package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.MockProducts.mockProducts

class CatalogViewModel : ViewModel() {
    private val _catalogProducts =
        MutableLiveData<List<ProductUiModel>>(emptyList<ProductUiModel>())
    val catalogProducts: LiveData<List<ProductUiModel>> = _catalogProducts

    val page = MutableLiveData<Int>(INITIAL_PAGE)

    init {
        loadCatalogProducts(PAGE_SIZE)
    }

    fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val fromIndex = (page.value ?: 0) * pageSize
        val toIndex = minOf(fromIndex + pageSize, mockProducts.size)
        val pagedProducts: List<ProductUiModel> =
            mockProducts.subList(fromIndex, toIndex).map { it.toUiModel() }
        _catalogProducts.value = _catalogProducts.value?.plus(pagedProducts)
        increasePage()
    }

    fun increasePage() {
        page.value = page.value?.plus(1)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
