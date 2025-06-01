package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.util.SingleLiveEvent
import android.os.Handler
import android.os.Looper
import android.util.Log
import woowacourse.shopping.data.repository.LastProductRepository

class ProductDetailViewModel(
    private val productsRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val lastProductRepository: LastProductRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> =
        MutableLiveData(Product.Companion.DEFAULT_PRODUCT)
    val product: LiveData<Product> get() = _product

    private val _count: MutableLiveData<Int> = MutableLiveData(1)
    val count: LiveData<Int> get() = _count

    val onProductAddedEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onFinishEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private val mainHandler = Handler(Looper.getMainLooper())

    private val _latestProduct = MutableLiveData<Product?>()
    val latestProduct: LiveData<Product?> get() = _latestProduct

    private val _isEqualProduct = MutableLiveData<Boolean>(false)
    val isEqualProduct: LiveData<Boolean> get() = _isEqualProduct

    fun loadLatestViewedProduct() {
        lastProductRepository.fetchLatestProduct { product ->
            _latestProduct.value = product
            _isEqualProduct.value = (_product.value?.id == _latestProduct.value?.id)
            Log.d("test","${_product.value?.id}  ${_latestProduct.value?.id}")

            Log.d("test",isEqualProduct.value.toString())

        }
    }

    fun addLastProduct() {
        Thread {
            if(product.value!=null){
                lastProductRepository.insertProduct(_product.value!!)
            }
        }.start()
    }


    fun updateProductDetail(id: Int) {
        productsRepository.fetchProductDetail(id) { result ->
            _product.value = result ?: Product.DEFAULT_PRODUCT
        }
    }

    fun addCartProduct() {
        val p = product.value ?: return
        val c = count.value ?: return

        Thread {
            cartRepository.upsertCartProduct(p, c)

            mainHandler.post {
                onProductAddedEvent.call()
                onFinishEvent.call()
            }
        }.start()
    }

    fun upCount() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun downCount() {
        val newValue = (_count.value ?: 0) - 1
        _count.value = if (newValue < 1) 1 else newValue
    }
}