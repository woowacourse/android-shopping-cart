package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.mapper.toShoppingCartItem
import woowacourse.shopping.mapper.toShoppingCartItemPageUiModel
import woowacourse.shopping.view.mainThread
import woowacourse.shopping.view.uimodel.QuantityInfo
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel
import woowacourse.shopping.view.uimodel.ShoppingCartRecyclerViewItems
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productsLiveData: MutableLiveData<ShoppingCartRecyclerViewItems> =
        MutableLiveData()

    val productsLiveData: LiveData<ShoppingCartRecyclerViewItems> get() = _productsLiveData

    var quantityInfo: QuantityInfo<ShoppingCartItemUiModel> = QuantityInfo()
        private set

    fun removeProduct(
        shoppingCartItemUiModel: ShoppingCartItemUiModel,
        currentPage: Int,
    ) {
        thread {
            shoppingCartRepository.remove(shoppingCartItemUiModel.toShoppingCartItem())
            val productsCount = shoppingCartRepository.totalSize()

            mainThread {
                requestProductsPage(pageNumberAfterRemoval(currentPage, productsCount))
            }
        }
    }

    fun requestProductsPage(requestPage: Int) {
        val pageRequest =
            PageRequest(
                pageSize = PAGE_SIZE,
                requestPage = requestPage,
            )

        thread {
            val item = shoppingCartRepository.findAll(pageRequest)

            quantityInfo =
                QuantityInfo(
                    item.toShoppingCartItemPageUiModel().items.associateWith {
                        MutableLiveData(it.quantity)
                    },
                )

            mainThread {
                _productsLiveData.value =
                    ShoppingCartRecyclerViewItems(
                        item.toShoppingCartItemPageUiModel(),
                        quantityInfo,
                    )
            }
        }
    }

    fun saveCurrentShoppingCart(shoppingCartItemUiModels: List<ShoppingCartItemUiModel>) {
        thread {
            shoppingCartItemUiModels.forEach {
                shoppingCartRepository.update(it.toShoppingCartItem())
            }
        }
    }

    fun increaseCount(shoppingCartItemUiModel: ShoppingCartItemUiModel) {
        quantityInfo[shoppingCartItemUiModel].value?.let {
            quantityInfo[shoppingCartItemUiModel].value = it + 1
        }
    }

    fun decreaseCount(shoppingCartItemUiModel: ShoppingCartItemUiModel) {
        quantityInfo[shoppingCartItemUiModel].value?.let {
            if (it > 1) {
                quantityInfo[shoppingCartItemUiModel].value = it - 1
            } else {
                removeProduct(shoppingCartItemUiModel, 0)
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
