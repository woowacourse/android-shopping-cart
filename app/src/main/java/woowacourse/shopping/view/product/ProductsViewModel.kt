package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.product.ProductsItem.LoadItem
import woowacourse.shopping.view.product.ProductsItem.ProductItem

class ProductsViewModel(
    private val productsRepository: ProductsRepository = DefaultProductsRepository(),
) : ViewModel() {
    private val _products: MutableLiveData<List<ProductsItem>> = MutableLiveData(emptyList())
    val products: LiveData<List<ProductsItem>> get() = _products

    private val _event: MutableLiveData<ProductsEvent> = MutableLiveData()
    val event: LiveData<ProductsEvent> get() = _event

    private var loadable: Boolean = false

    fun updateProducts() {
        val lastProductId: Long? =
            (products.value?.lastOrNull { it is ProductItem } as? ProductItem)?.product?.id

        productsRepository.load(lastProductId, LOAD_PRODUCTS_SIZE + 1) { result ->
            result.onSuccess { newProducts ->
                loadable = newProducts.size == LOAD_PRODUCTS_SIZE + 1
                val productsToShow: List<Product> = newProducts.take(LOAD_PRODUCTS_SIZE)
                val updatedProducts: List<ProductsItem> =
                    getUpdateProductsItem(productsToShow, loadable)

                _products.postValue(updatedProducts)
            }
                .onFailure {
                    _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
                }
        }
    }

    private fun getUpdateProductsItem(
        productsToShow: List<Product>,
        loadable: Boolean
    ): List<ProductsItem> {
        val currentProducts = products.value ?: emptyList()
        val productsWithoutLoadItem = if (currentProducts.lastOrNull() is LoadItem) {
            currentProducts.dropLast(1)
        } else {
            currentProducts
        }

        return buildList {
            addAll(productsWithoutLoadItem)
            addAll(productsToShow.map(::ProductItem))
            if (loadable) add(LoadItem)
        }
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
