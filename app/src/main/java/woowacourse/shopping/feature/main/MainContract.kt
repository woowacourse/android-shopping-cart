package woowacourse.shopping.feature.main

import androidx.lifecycle.LiveData
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel

interface MainContract {
    interface View {
        sealed class MainScreenEvent {
            object ShowCartScreen : MainScreenEvent()
            class ShowProductDetailScreen(
                val product: ProductUiModel,
                val recentProduct: RecentProductUiModel?
            ) : MainScreenEvent()

            object HideLoadMore : MainScreenEvent()
        }
    }

    interface Presenter {
        val products: LiveData<List<ProductUiModel>>
        val recentProducts: LiveData<List<RecentProductUiModel>>
        val badgeCount: LiveData<Int>
        val mainScreenEvent: LiveData<View.MainScreenEvent>
        fun loadProducts()
        fun loadRecent()
        fun loadCartCountSize()
        fun moveToCart()
        fun showProductDetail(productId: Long)
        fun showRecentProductDetail(productId: Long)
        fun changeProductCartCount(productId: Long, count: Int)
        fun loadMoreProduct()
        fun resetProducts()
    }
}
