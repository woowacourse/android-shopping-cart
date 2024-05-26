package woowacourse.shopping.ui.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.OrderEntity
import woowacourse.shopping.model.data.OrdersRepository
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.data.RecentProductEntity
import woowacourse.shopping.model.data.RecentProductsRepository
import java.time.LocalDateTime

class ProductDetailViewModel(
    private val productDao: ProductDao,
    applicationContext: Context,
) : ViewModel() {
    private val ordersRepository = OrdersRepository(applicationContext)
    private val recentProductsRepository = RecentProductsRepository(applicationContext)

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _itemCount: MutableLiveData<Int> = MutableLiveData(1)
    val itemCount: LiveData<Int> get() = _itemCount

    private val _recentProductVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val recentProductVisibility: LiveData<Boolean> get() = _recentProductVisibility

    private val _recentProduct: MutableLiveData<Product> = MutableLiveData()
    val recentProduct: LiveData<Product> get() = _recentProduct

    fun loadProduct(productId: Long) {
        _product.value = productDao.find(productId)
        recentProductsRepository.insert(RecentProductEntity(productId, LocalDateTime.now()))
    }

    fun loadRecentProduct() {
        _recentProduct.value = recentProductsRepository.getSecondLastData()
        _recentProductVisibility.value = (_recentProduct.value != null)
    }

    fun increaseItemCount() {
        _itemCount.value = _itemCount.value?.plus(1)
    }

    fun decreaseItemCount() {
        if (_itemCount.value == 1) return
        _itemCount.value = _itemCount.value?.minus(1)
    }

    fun addProductToCart() {
        val productId = _product.value?.id ?: -1
        val existingQuantity = ordersRepository.getById(productId).quantity
        ordersRepository.insert(OrderEntity(productId, existingQuantity + _itemCount.value!!))
    }
}
