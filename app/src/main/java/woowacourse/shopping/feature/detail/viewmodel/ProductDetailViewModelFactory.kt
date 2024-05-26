package woowacourse.shopping.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryRepository
import woowacourse.shopping.data.product.ProductRepository
import java.lang.IllegalArgumentException

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val inquiryHistoryRepository: InquiryHistoryRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(productRepository, cartRepository, inquiryHistoryRepository) as T
        }
        throw IllegalArgumentException()
    }
}
