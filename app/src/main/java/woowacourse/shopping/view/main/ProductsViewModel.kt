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
import woowacourse.shopping.data.repository.product.ProductsRepository
import woowacourse.shopping.data.repository.recent.RecentProductsRepository
import woowacourse.shopping.data.repository.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.mapper.toProduct
import woowacourse.shopping.mapper.toProductPageUiModel
import woowacourse.shopping.mapper.toProductUiModel
import woowacourse.shopping.mapper.toShoppingCartItemUiModel
import woowacourse.shopping.view.mainThread
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.QuantityInfo
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel
import kotlin.concurrent.thread

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

    var quantityInfo: QuantityInfo<ProductUiModel> = QuantityInfo()
        private set

    init {
        thread {
            val value = productsRepository.totalSize()

            val quantity = shoppingCartRepository.totalQuantity()

            mainThread {
                totalSize.value = value
                totalShoppingCartSize.value = quantity
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
            val page = productsRepository.findAll(pageRequest)
            val shoppingCartItems =
                shoppingCartRepository.findAll().map { it.toShoppingCartItemUiModel() }

            quantityInfo +=
                QuantityInfo(
                    page.toProductPageUiModel().items.quantityMap(
                        shoppingCartItems,
                    ),
                )

            mainThread {
                _productsLiveData.value =
                    MainRecyclerViewProduct(
                        page = page.toProductPageUiModel(),
                        quantityInfo,
                    )
            }
        }
    }

    fun saveCurrentShoppingCart(quantityInfo: QuantityInfo<ProductUiModel>) {
        thread {
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
        thread {
            val totalQuantity = shoppingCartRepository.totalQuantity()
            mainThread {
                totalShoppingCartSize.value = totalQuantity
                (0..currentPage).forEach {
                    requestProductsPage(it)
                }
            }
        }
    }

    fun requestRecentProducts() {
        val recentProducts = RecentProducts()

        thread {
            recentProductsRepository.findAll().forEach {
                recentProducts.add(it)
            }
            mainThread {
                _recentProductsLiveData.value =
                    recentProducts.items.map { it.product.toProductUiModel() }
            }
        }
    }

    fun increaseCount(uiModel: ProductUiModel) {
        quantityInfo[uiModel].value =
            quantityInfo[uiModel].value?.inc()
        totalShoppingCartSize.value = totalShoppingCartSize.value?.inc()
    }

    fun decreaseCount(uiModel: ProductUiModel) {
        quantityInfo[uiModel].value =
            quantityInfo[uiModel].value?.dec()
        totalShoppingCartSize.value = totalShoppingCartSize.value?.dec()
    }

    private fun List<ProductUiModel>.quantityMap(
        newShoppingCartItemUiModels: List<ShoppingCartItemUiModel>,
    ): Map<ProductUiModel, MutableLiveData<Int>> {
        return associateWith { product ->
            MutableLiveData(
                newShoppingCartItemUiModels.find { it.productUiModel.id == product.id }
                    ?.quantity ?: DEFAULT_QUANTITY,
            )
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val DEFAULT_QUANTITY = 0

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
