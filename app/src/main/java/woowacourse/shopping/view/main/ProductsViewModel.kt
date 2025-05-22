package woowacourse.shopping.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productsLiveData: MutableLiveData<MainRecyclerViewProduct> = MutableLiveData()
    val productsLiveData: LiveData<MainRecyclerViewProduct> get() = _productsLiveData
    val totalSize: Int get() = productsRepository.totalSize()
    val totalShoppingCartSize: MutableLiveData<Int> = MutableLiveData(shoppingCartRepository.totalQuantity())

    fun requestProductsPage(requestPage: Int) {
        val pageRequest =
            PageRequest(
                pageSize = PAGE_SIZE,
                requestPage = requestPage,
            )
        val page = productsRepository.findAll(pageRequest)
        val shoppingCartItems = shoppingCartRepository.findAll(pageRequest)

        _productsLiveData.value =
            MainRecyclerViewProduct(
                page = page,
                shoppingCartItems = shoppingCartItems,
                totalShoppingCartSize = shoppingCartRepository.totalSize(),
            )
    }

    fun saveCurrentShoppingCart(quantityMap: Map<Product, MutableLiveData<Int>>) {
        quantityMap.forEach {
            if (it.value.value!! > 0) {
                shoppingCartRepository.update(
                    ShoppingCartItem(
                        product = it.key,
                        quantity = it.value.value!!,
                    ),
                )
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
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
