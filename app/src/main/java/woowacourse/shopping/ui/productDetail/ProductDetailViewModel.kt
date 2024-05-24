package woowacourse.shopping.ui.productDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.data.source.DummyShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.domain.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.domain.repository.ProductIdsCountRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.productList.ProductRecyclerViewAdapter
import java.lang.Exception

class ProductDetailViewModel(
    private val productId: Int,
    shoppingProductsRepository: ShoppingProductsRepository,
    private val productIdsCountRepository: ProductIdsCountRepository =
        DefaultProductIdsCountRepository(
            DummyShoppingCartProductIdDataSource(),
        ),
) : ViewModel(), ProductRecyclerViewAdapter.OnItemQuantityChangeListener {
    val currentProduct: ProductData = shoppingProductsRepository.loadProduct(productId)

    private val _productCount: MutableLiveData<Int> =
        MutableLiveData(
            try {
                productIdsCountRepository.findByProductId(productId).quantity
            } catch (e: NoSuchElementException) {
                1
            },
        )

    val productCount: LiveData<Int> get() = _productCount

    fun addProductToCart() {
        try {
            val product = productIdsCountRepository.findByProductId(productId)
            // alreay have -> toast msg (이미 있습니다)
            Log.d(TAG, "addProductToCart: $product 는 이미 있습니다")
        } catch (e: Exception) {
            productIdsCountRepository.addedProductsId(ProductIdsCount(productId, productCount.value ?: 1))
        }
    }

    override fun onIncrease(productId: Int) {
        _productCount.value = _productCount.value?.plus(1)
    }

    override fun onDecrease(productId: Int) {
        _productCount.value = _productCount.value?.minus(1)
    }

    companion object {
        private const val TAG = "ProductDetailViewModel"
    }
}
