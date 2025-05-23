package woowacourse.shopping.view.detail

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
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.repository.RecentProductsRepository
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.mapper.toProduct
import woowacourse.shopping.view.uimodel.ProductUiModel
import java.time.LocalDateTime

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductsRepository: RecentProductsRepository,
) : ViewModel() {
    private val _productUiModelLiveData: MutableLiveData<ProductUiModel> = MutableLiveData()
    val productUiModelLiveData: LiveData<ProductUiModel> get() = _productUiModelLiveData
    val quantityLiveData: MutableLiveData<Int> = MutableLiveData(1)
    val recentProducts = RecentProducts()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            recentProductsRepository.findAll().forEach {
                recentProducts.add(it)
            }
        }
    }

    fun addProduct(productUiModel: ProductUiModel) {
        quantityLiveData.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                shoppingCartRepository.save(
                    ShoppingCartItem(
                        product = productUiModel.toProduct(),
                        quantity = it,
                    ),
                )
            }
        }
    }

    fun setProduct(productUiModel: ProductUiModel) {
        _productUiModelLiveData.value = productUiModel
    }

    fun addRecentProduct() {
        recentProducts.add(
            RecentProduct(
                product = _productUiModelLiveData.value?.toProduct() ?: return,
                viewTime = LocalDateTime.now(),
            ),
        )
        viewModelScope.launch(Dispatchers.IO) {
            recentProducts.items.forEach {
                if (recentProducts.isFull()) {
                    recentProductsRepository.update(it)
                } else {
                    recentProductsRepository.insert(it)
                }
            }
        }
    }

    fun isInRecentProducts() {
        recentProducts.contains(
            productUiModelLiveData.value?.toProduct() ?: return,
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as ShoppingCartApplication)
                    ProductDetailViewModel(
                        application.shoppingCartRepository,
                        application.recentProductsRepository,
                    )
                }
            }
    }
}
