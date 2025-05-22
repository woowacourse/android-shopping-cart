package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productsLiveData: MutableLiveData<Page<ShoppingCartItemUiModel>> = MutableLiveData()
    private val productsCount: Int get() = shoppingCartRepository.totalSize()

    val productsLiveData: LiveData<Page<ShoppingCartItemUiModel>> get() = _productsLiveData

    fun removeProduct(
        shoppingCartItemUiModel: ShoppingCartItemUiModel,
        currentPage: Int,
    ) {
        shoppingCartRepository.remove(shoppingCartItemUiModel)
        requestProductsPage(pageNumberAfterRemoval(currentPage))
    }

    fun requestProductsPage(requestPage: Int) {
        val pageRequest =
            PageRequest(
                pageSize = PAGE_SIZE,
                requestPage = requestPage,
            )
        val item = shoppingCartRepository.findAll(pageRequest)
        _productsLiveData.value = item
    }

    fun saveCurrentShoppingCart(shoppingCartItemUiModels: List<ShoppingCartItemUiModel>) {
        shoppingCartItemUiModels.forEach {
            shoppingCartRepository.update(it)
        }
    }

    private fun pageNumberAfterRemoval(currentPage: Int): Int {
        val newPageNumber =
            if (productsCount % PAGE_SIZE == 0 && currentPage * PAGE_SIZE == productsCount) {
                currentPage - 1
            } else {
                currentPage
            }
        return newPageNumber.coerceAtLeast(0)
    }

    companion object {
        private const val PAGE_SIZE = 5
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val shoppingCartRepository = (this[APPLICATION_KEY] as ShoppingCartApplication).shoppingCartRepository
                    ShoppingCartViewModel(
                        shoppingCartRepository,
                    )
                }
            }
    }
}
