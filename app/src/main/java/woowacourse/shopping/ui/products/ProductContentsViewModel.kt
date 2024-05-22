package woowacourse.shopping.ui.products

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.OrderEntity
import woowacourse.shopping.model.data.OrdersRepository
import woowacourse.shopping.model.data.ProductDao
import kotlin.concurrent.thread
import kotlin.math.min

class ProductContentsViewModel(
    private val productDao: ProductDao,
    application: Context,
) : ViewModel() {
    private val ordersRepository = OrdersRepository(application)

    private var currentOffset = DEFAULT_OFFSET
    private val items: MutableList<Product> = mutableListOf()

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    private val _isItemPlusButtonVisible: MutableLiveData<MutableList<Boolean>> = MutableLiveData()
    val isItemPlusButtonVisible: LiveData<MutableList<Boolean>> get() = _isItemPlusButtonVisible

    private val _itemCount: MutableLiveData<MutableList<Int>> = MutableLiveData()
    val itemCount: LiveData<MutableList<Int>> get() = _itemCount

    private fun setItemPlusButtonVisible(
        id: Long,
        condition: Boolean,
    ) {
        _isItemPlusButtonVisible.value =
            _isItemPlusButtonVisible.value?.apply { set(id.toInt(), condition) }
    }

    fun onItemPlusButtonClick(id: Long) {
        setItemPlusButtonVisible(id, false)
        val temp = _itemCount.value ?: mutableListOf()
        temp[id.toInt()] += ITEM_COUNT_CHANGE
        _itemCount.value = temp
        ordersRepository.insert(OrderEntity(id, temp[id.toInt()]))
    }

    fun onItemIncreaseButtonClick(id: Long) {
        val temp = _itemCount.value ?: mutableListOf()
        temp[id.toInt()] += ITEM_COUNT_CHANGE
        _itemCount.value = temp
        ordersRepository.insert(OrderEntity(id, temp[id.toInt()]))
    }

    fun onItemDecreaseButtonClick(id: Long) {
        val temp = _itemCount.value ?: mutableListOf()
        temp[id.toInt()] -= ITEM_COUNT_CHANGE
        _itemCount.value = temp
        thread { ordersRepository.insert(OrderEntity(id, temp[id.toInt()])) }.join()
        if (temp[id.toInt()] == DEFAULT_ITEM_COUNT) {
            setItemPlusButtonVisible(id, true)
            ordersRepository.deleteById(id)
        }
    }

    fun loadProducts() {
        items.clear()
        val allProducts = productDao.findAll()
        items.addAll(allProducts)
        _products.value = getProducts()
        _isItemPlusButtonVisible.value = List(allProducts.size) { true }.toMutableList()
        _itemCount.value = List(allProducts.size) { DEFAULT_ITEM_COUNT }.toMutableList()
    }

    private fun getProducts(): List<Product> {
        val endRange = min(currentOffset + LOAD_LIMIT, items.size)
        val productsInRange = items.toList().subList(currentOffset, endRange)
        currentOffset = endRange

        return productsInRange
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
        const val LOAD_LIMIT = 20
        private const val DEFAULT_ITEM_COUNT = 0
        private const val ITEM_COUNT_CHANGE = 1
    }
}
