package woowacourse.shopping.view.main

import android.view.View
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
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.toProduct
import woowacourse.shopping.view.toProductUiModel
import woowacourse.shopping.view.toShoppingCartItemUiModel
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.QuantityInfo

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productsLiveData: MutableLiveData<MainRecyclerViewProduct> = MutableLiveData()
    val productsLiveData: LiveData<MainRecyclerViewProduct> get() = _productsLiveData
    val totalSize: Int get() = productsRepository.totalSize()
    val totalShoppingCartSize: MutableLiveData<Int> = MutableLiveData(shoppingCartRepository.totalQuantity())

    val menuBadgeViewRule = { text: String ->
        if (text.toInt() > MINIMUM_BADGE_DISPLAY_QUANTITY) View.VISIBLE else View.GONE
    }

    val onProductAddedButtonViewRule: (Int) -> Int = { quantityValue: Int ->
        if (quantityValue < 1) View.VISIBLE else View.GONE
    }

    val onProductAddedQuantitySelectorViewRule: (Int) -> Int = { quantityValue: Int ->
        if (quantityValue < 1) View.GONE else View.VISIBLE
    }

    fun requestProductsPage(requestPage: Int) {
        val pageRequest =
            PageRequest(
                pageSize = PAGE_SIZE,
                requestPage = requestPage,
            )
        val page = productsRepository.findAll(pageRequest)
        val shoppingCartItems = shoppingCartRepository.findAll().map { it.toShoppingCartItemUiModel() }

        _productsLiveData.value =
            MainRecyclerViewProduct(
                page =
                    Page(
                        items = page.items.map { it.toProductUiModel() },
                        totalCounts = page.totalCounts,
                        currentPage = page.currentPage,
                        pageSize = page.pageSize,
                    ),
                shoppingCartItemUiModels = shoppingCartItems,
            )
    }

    fun saveCurrentShoppingCart(quantityInfo: QuantityInfo<ProductUiModel>) {
        quantityInfo.forEach { product, data ->
            shoppingCartRepository.update(
                ShoppingCartItem(
                    product = product.toProduct(),
                    quantity = data,
                ),
            )
        }
    }

    fun updateShoppingCart(currentPage: Int) {
        totalShoppingCartSize.value = shoppingCartRepository.totalQuantity()
        (0..currentPage).forEach {
            requestProductsPage(it)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val MINIMUM_BADGE_DISPLAY_QUANTITY = 0
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as ShoppingCartApplication)
                    ProductsViewModel(
                        application.productsRepository,
                        application.shoppingCartRepository,
                    )
                }
            }
    }
}
