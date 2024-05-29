package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.OrderDao
import woowacourse.shopping.model.data.OrderEntity
import woowacourse.shopping.model.data.OrdersRepository
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.data.RecentProductDao
import woowacourse.shopping.model.data.RecentProductsRepository
import kotlin.concurrent.thread
import kotlin.math.min

class ProductContentsViewModel(
    private val productDao: ProductDao,
    private val orderDao: OrderDao,
    private val recentProductDao: RecentProductDao,
) : ViewModel() {
    private val _recentProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val recentProducts: LiveData<List<Product>> get() = _recentProducts

    private val ordersRepository = OrdersRepository(orderDao)
    private val recentProductsRepository = RecentProductsRepository(recentProductDao)

    private var currentOffset = DEFAULT_OFFSET
    private val items: MutableList<Product> = mutableListOf()

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    private val _isItemPlusButtonVisible: MutableLiveData<MutableMap<Long, Boolean>> =
        MutableLiveData(mutableMapOf())
    val isItemPlusButtonVisible: LiveData<MutableMap<Long, Boolean>> get() = _isItemPlusButtonVisible

    private val _itemCount: MutableLiveData<MutableMap<Long, Int>> = MutableLiveData(mutableMapOf())
    val itemCount: LiveData<MutableMap<Long, Int>> get() = _itemCount

    private val _totalItemCount: MutableLiveData<Int> = MutableLiveData()
    val totalItemCount: LiveData<Int> get() = _totalItemCount

    fun loadProducts() {
        items.clear()
        val allProducts = productDao.findAll()
        items.addAll(allProducts)
        _products.value = getProducts()
        allProducts.forEach {
            _itemCount.value!![it.id] = 0
        }
        val allOrders = ordersRepository.getAllData()
        allOrders.forEach {
            _itemCount.value!![it.productId] = it.quantity
        }
        _itemCount.value = _itemCount.value
        setItemPlusButtonVisible()
    }

    fun loadRecentProducts() {
        _recentProducts.value = recentProductsRepository.getAllData()
    }

    private fun calculateTotalCount() {
        _totalItemCount.value = _itemCount.value?.values?.sum()
    }

    private fun setItemPlusButtonVisible() {
        val allProducts = productDao.findAll()
        allProducts.forEach {
            _isItemPlusButtonVisible.value!![it.id] = (_itemCount.value!![it.id] == 0)
        }
        _isItemPlusButtonVisible.value = _isItemPlusButtonVisible.value
    }

    fun onItemPlusButtonClick(id: Long) {
        val temp = _itemCount.value ?: mutableMapOf()
        temp[id] = temp[id]!! + ITEM_COUNT_CHANGE
        _itemCount.value = temp
        ordersRepository.insert(OrderEntity(id, temp[id]!!))
        setItemPlusButtonVisible()
        calculateTotalCount()
    }

    fun onItemIncreaseButtonClick(id: Long) {
        val temp = _itemCount.value ?: mutableMapOf()
        temp[id] = temp[id]!! + ITEM_COUNT_CHANGE
        _itemCount.value = temp
        ordersRepository.insert(OrderEntity(id, temp[id]!!))
        calculateTotalCount()
    }

    fun onItemDecreaseButtonClick(id: Long) {
        val temp = _itemCount.value ?: mutableMapOf()
        temp[id] = temp[id]!! - ITEM_COUNT_CHANGE
        _itemCount.value = temp
        thread { ordersRepository.insert(OrderEntity(id, temp[id]!!)) }.join()
        if (temp[id] == DEFAULT_ITEM_COUNT) {
            setItemPlusButtonVisible()
            ordersRepository.deleteById(id)
        }
        calculateTotalCount()
    }

    private fun getProducts(): List<Product> {
        val endRange = min(currentOffset + LOAD_LIMIT, items.size)
        val productsInRange = items.toList().subList(DEFAULT_OFFSET, endRange)

        return productsInRange
    }

    fun renewCurrentOffset() {
        val endRange = min(currentOffset + LOAD_LIMIT, items.size)
        currentOffset = endRange
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
        const val LOAD_LIMIT = 20
        private const val DEFAULT_ITEM_COUNT = 0
        private const val ITEM_COUNT_CHANGE = 1
    }
}
