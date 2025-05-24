package woowacourse.shopping.view.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.repository.recent.RecentProductsRepository
import woowacourse.shopping.data.repository.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.mapper.toProduct
import woowacourse.shopping.view.mainThread
import woowacourse.shopping.view.uimodel.ProductUiModel
import java.time.LocalDateTime
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductsRepository: RecentProductsRepository,
) : ViewModel() {
    private val _productUiModelLiveData: MutableLiveData<ProductUiModel> = MutableLiveData()
    val productUiModelLiveData: LiveData<ProductUiModel> get() = _productUiModelLiveData
    val quantityLiveData: MutableLiveData<Int> = MutableLiveData(1)
    val recentProducts = RecentProducts()
    val allLoaded = MutableLiveData(false)

    init {
        thread {
            recentProductsRepository.findAll().forEach {
                recentProducts.add(it)
            }
            mainThread {
                allLoaded.value = true
            }
        }
    }

    fun addProduct(productUiModel: ProductUiModel) {
        quantityLiveData.value?.let {
            thread {
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
        thread {
            recentProducts.items.forEach {
                if (recentProducts.isFull()) {
                    recentProductsRepository.update(it)
                } else {
                    recentProductsRepository.insert(it)
                }
            }
        }
    }

    fun isInRecentProducts(): Int {
        return if (recentProducts.contains(
                productUiModelLiveData.value?.toProduct() ?: return View.GONE,
            ) || recentProducts.items.isEmpty()
        ) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun lastViewedProductName(): String {
        if (recentProducts.items.isEmpty()) return ""
        return recentProducts.items.first().product.name
    }

    fun increaseCount() {
        quantityLiveData.value?.let {
            quantityLiveData.value = it + 1
        }
    }

    fun decreaseCount() {
        quantityLiveData.value?.let {
            if (it > 1) {
                quantityLiveData.value = it - 1
            }
        }
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
