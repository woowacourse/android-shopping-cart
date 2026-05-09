package woowacourse.shopping.ui.productdetail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ProductDetailViewModel(
    private val recentViewedProductsRepository: RecentlyViewedProductsRepository,
    private val currentProductId: Uuid,
) : ViewModel() {

    var lastViewedProduct by mutableStateOf<Product?>(null)
        private set

    init {
        viewModelScope.launch {
            recentViewedProductsRepository.getRecentlyViewedProducts()
                .collect { recentlyViewedProducts ->
                    lastViewedProduct = recentlyViewedProducts.products.firstOrNull {
                        it.productId != currentProductId
                    }
                }
        }
    }
}
