package woowacourse.shopping.view.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.InventoryRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.model.InventoryItem.ProductUiModel
import woowacourse.shopping.view.model.toUiModel
import woowacourse.shopping.view.page.Page

class InventoryViewModel(repository: InventoryRepository) : ViewModel() {
    private val allProducts: Set<ProductUiModel> = repository.getAll().map(Product::toUiModel).toSet()
    private val _productsLiveData: MutableLiveData<Page<ProductUiModel>> = MutableLiveData()

    val totalSize: Int get() = allProducts.size
    val productsLiveData: LiveData<Page<ProductUiModel>> get() = _productsLiveData

    fun requestProductsPage(requestPage: Int) {
        val page =
            Page.from(
                allProducts.toList(),
                requestPage,
                PAGE_SIZE,
            )
        _productsLiveData.value = page
    }

    companion object {
        private const val PAGE_SIZE = 20

        @Suppress("UNCHECKED_CAST")
        fun createFactory(inventoryRepository: InventoryRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (InventoryViewModel(inventoryRepository) as T)
                }
            }
        }
    }
}
