package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.mapper.toShoppingCartItem
import woowacourse.shopping.mapper.toShoppingCartItemUiModel
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productsLiveData: MutableLiveData<Page<ShoppingCartItemUiModel>> =
        MutableLiveData()

    val productsLiveData: LiveData<Page<ShoppingCartItemUiModel>> get() = _productsLiveData

    fun removeProduct(
        shoppingCartItemUiModel: ShoppingCartItemUiModel,
        currentPage: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingCartRepository.remove(shoppingCartItemUiModel.toShoppingCartItem())
            val productsCount = async { shoppingCartRepository.totalSize() }.await()
            requestProductsPage(pageNumberAfterRemoval(currentPage, productsCount))
        }
    }

    fun requestProductsPage(requestPage: Int) {
        val pageRequest =
            PageRequest(
                pageSize = PAGE_SIZE,
                requestPage = requestPage,
            )

        viewModelScope.launch {
            val item =
                withContext(Dispatchers.IO) {
                    shoppingCartRepository.findAll(pageRequest)
                }
            _productsLiveData.value =
                Page(
                    items = item.items.map { it.toShoppingCartItemUiModel() },
                    totalCounts = item.totalCounts,
                    currentPage = item.currentPage,
                    pageSize = item.pageSize,
                )
        }
    }

    fun saveCurrentShoppingCart(shoppingCartItemUiModels: List<ShoppingCartItemUiModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingCartItemUiModels.forEach {
                shoppingCartRepository.update(it.toShoppingCartItem())
            }
        }
    }

    private fun pageNumberAfterRemoval(
        currentPage: Int,
        productsCount: Int,
    ): Int {
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
                    val shoppingCartRepository =
                        (this[APPLICATION_KEY] as ShoppingCartApplication).shoppingCartRepository
                    ShoppingCartViewModel(
                        shoppingCartRepository,
                    )
                }
            }
    }
}
