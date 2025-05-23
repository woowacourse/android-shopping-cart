package woowacourse.shopping.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.data.repository.RecentProductsRepository
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.mapper.toProduct
import woowacourse.shopping.mapper.toProductUiModel
import woowacourse.shopping.mapper.toShoppingCartItemUiModel
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.QuantityInfo

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductsRepository: RecentProductsRepository,
) : ViewModel(), ViewRule {
    private val _productsLiveData: MutableLiveData<MainRecyclerViewProduct> = MutableLiveData()
    val productsLiveData: LiveData<MainRecyclerViewProduct> get() = _productsLiveData

    private val _recentProductsLiveData: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val recentProductsLiveData: LiveData<List<ProductUiModel>> get() = _recentProductsLiveData

    val totalSize: MutableLiveData<Int> = MutableLiveData(0)
    val totalShoppingCartSize: MutableLiveData<Int> = MutableLiveData()

    init {
        viewModelScope.launch {
            totalSize.value = productsRepository.totalSize()
            totalShoppingCartSize.value = shoppingCartRepository.totalQuantity()
        }
    }

    fun requestProductsPage(requestPage: Int) {
        val pageRequest =
            PageRequest(
                pageSize = PAGE_SIZE,
                requestPage = requestPage,
            )

        viewModelScope.launch {
            val page =
                withContext(Dispatchers.IO) {
                    productsRepository.findAll(pageRequest)
                }
            val shoppingCartItems =
                withContext(Dispatchers.IO) {
                    shoppingCartRepository.findAll().map { it.toShoppingCartItemUiModel() }
                }

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
    }

    fun saveCurrentShoppingCart(quantityInfo: QuantityInfo<ProductUiModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            quantityInfo.toList().forEach {
                shoppingCartRepository.update(
                    ShoppingCartItem(
                        product = it.first.toProduct(),
                        quantity = it.second,
                    ),
                )
            }
        }
    }

    fun updateShoppingCart(currentPage: Int) {
        viewModelScope.launch {
            val totalQuantity =
                withContext(Dispatchers.IO) {
                    shoppingCartRepository.totalQuantity()
                }
            totalShoppingCartSize.value = totalQuantity
            (0..currentPage).forEach {
                requestProductsPage(it)
            }
        }
    }

    fun increaseShoppingCartTotalSize() {
        totalShoppingCartSize.value = totalShoppingCartSize.value?.inc()
    }

    fun decreaseShoppingCartTotalSize() {
        totalShoppingCartSize.value = totalShoppingCartSize.value?.dec()
    }

    fun requestRecentProducts() {
        viewModelScope.launch {
            val recentProducts = RecentProducts()
            withContext(Dispatchers.IO) {
                recentProductsRepository.findAll().forEach {
                    recentProducts.add(it)
                }
            }
            _recentProductsLiveData.value = recentProducts.items.map { it.product.toProductUiModel() }
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
                        application.recentProductsRepository,
                    )
                }
            }
    }
}
