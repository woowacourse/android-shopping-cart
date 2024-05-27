package woowacourse.shopping.presentation.shopping.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory

class RecentProductViewModel(private val recentProductRepository: RecentProductRepository) :
    ViewModel() {
    private val _recentProducts = MutableLiveData<List<RecentProduct>>(emptyList())
    val recentProducts: LiveData<List<RecentProduct>> = _recentProducts

    init {
        loadRecentProducts()
    }

    fun loadRecentProducts() {
        val products = recentProductRepository.recentProducts(RECENT_PRODUCTS_SIZE)
        _recentProducts.value = products
    }

    fun addRecentProduct(productId: Long) {
        recentProductRepository.addRecentProduct(productId)
    }

    companion object {
        private const val RECENT_PRODUCTS_SIZE: Int = 10

        fun factory(recentProduct: RecentProductRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { RecentProductViewModel(recentProduct) }
        }
    }
}
