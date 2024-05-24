package woowacourse.shopping.productDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.Product
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.productList.ProductRecyclerViewAdapter
import woowacourse.shopping.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.repository.ProductIdsCountRepository
import woowacourse.shopping.repository.ShoppingProductsRepository
import woowacourse.shopping.source.DummyProductIdsCountDataSource
import java.lang.Exception

class ProductDetailViewModel(
    private val productId: Int,
    shoppingProductsRepository: ShoppingProductsRepository,
    private val productIdsCountRepository: ProductIdsCountRepository =
        DefaultProductIdsCountRepository(
            DummyProductIdsCountDataSource(),
        ),
) : ViewModel(), ProductRecyclerViewAdapter.OnItemQuantityChangeListener {
    val currentProduct: Product = shoppingProductsRepository.findById(productId)

    private val _productCount: MutableLiveData<Int> =
        MutableLiveData(productIdsCountRepository.findByProductId(productId).quantity)

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

    // TODO: 이거 레포지토리에서 nullable 값을 리턴해야 겠는데
    override fun onIncrease(productId: Int) {
        try {
            productIdsCountRepository.plusProductsIdCount(productId)
            val product = productIdsCountRepository.findByProductId(productId)
            _productCount.value = product.quantity
        } catch (e: NoSuchElementException) {
            productIdsCountRepository.addedProductsId(ProductIdsCount(productId, 1))
            _productCount.value = 1
        }
    }

    override fun onDecrease(productId: Int) {
        // TODO: 이거 레포지토리에서 nullable 값을 리턴해야 겠는데
        try {
            productIdsCountRepository.minusProductsIdCount(productId)
        } catch (e: NoSuchElementException) {
            return
        }

        try {
            val product = productIdsCountRepository.findByProductId(productId)
            _productCount.value = product.quantity
        } catch (_: NoSuchElementException) {
            // 이미 최소 수량이다
        }
    }

    companion object {
        private const val TAG = "ProductDetailViewModel"
    }
}
