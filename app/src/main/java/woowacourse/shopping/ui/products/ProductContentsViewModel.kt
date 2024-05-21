package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.ProductDao
import kotlin.math.min

class ProductContentsViewModel(private val productDao: ProductDao) : ViewModel() {
    private var currentOffset = DEFAULT_OFFSET
    private val items: MutableList<Product> = mutableListOf()

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    private val _isItemPlusButtonVisible: MutableLiveData<MutableList<Boolean>> = MutableLiveData()
    val isItemPlusButtonVisible: LiveData<MutableList<Boolean>> get() = _isItemPlusButtonVisible

    private val _itemCount: MutableLiveData<MutableList<Int>> = MutableLiveData()
    val itemCount: LiveData<MutableList<Int>> get() = _itemCount

    fun onItemPlusButtonClick(id: Long) {
        _isItemPlusButtonVisible.value =
            _isItemPlusButtonVisible.value?.apply {
                set(id.toInt(), false)
            }
        val temp = _itemCount.value!!
        temp[id.toInt()] += CHANGED_ITEM_COUNT
        _itemCount.value = temp
    }

    fun onItemDecreaseButtonClick(id: Long) {
        val temp = _itemCount.value!!
        if (temp[id.toInt()] == MINIMUM_ITEM_COUNT) return
        temp[id.toInt()] -= CHANGED_ITEM_COUNT
        _itemCount.value = temp
    }

    fun onItemIncreaseButtonClick(id: Long) {
        val temp = _itemCount.value!!
        temp[id.toInt()] += CHANGED_ITEM_COUNT
        _itemCount.value = temp
    }

    fun loadProducts() {
        items.clear()
        val allProducts = productDao.findAll()
        items.addAll(allProducts)
        _products.value = getProducts()
        _isItemPlusButtonVisible.value = List(allProducts.size) { true }.toMutableList()
        _itemCount.value = List(allProducts.size) { MINIMUM_ITEM_COUNT }.toMutableList()
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
        private const val MINIMUM_ITEM_COUNT = 0
        private const val CHANGED_ITEM_COUNT = 1
    }
}
