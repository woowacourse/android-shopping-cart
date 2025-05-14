package woowacourse.shopping.viewmodel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.Products

class ProductsViewModel(
    private val products: Products,
) : ViewModel() {
    private val _productsInShop = MutableLiveData(products.value)
    val productsInShop: LiveData<List<Product>> get() = _productsInShop
    private val pageSize = 20
    private var currentPage = 1
    private val loadedList =
        mutableListOf<Product>().apply {
            addAll(products.value.take(pageSize))
        }

    fun loadNextPage() {
        val nextStart = currentPage * pageSize
        val nextEnd = minOf(nextStart + pageSize, products.value.size)

        if (nextStart < products.value.size) {
            val nextItems = products.value.subList(nextStart, nextEnd)
            loadedList.addAll(nextItems)
            _productsInShop.value = loadedList
            currentPage++
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    checkNotNull(extras[APPLICATION_KEY])
                    extras.createSavedStateHandle()

                    return ProductsViewModel(
                        Products(),
                    ) as T
                }
            }
    }
}
