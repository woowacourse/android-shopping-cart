package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.domain.model.Product

abstract class ProductDetailUiState {
    abstract val product: LiveData<Product>
    abstract val productCount: LiveData<Int>
    abstract val latestProduct: LiveData<Product>

    abstract fun currentProduct(): Product

    abstract fun currentQuantity(): Int

    abstract fun postLatestProduct(product: Product)

    abstract fun postCurrentProduct(product: Product)

    abstract fun increaseProductCount()

    abstract fun decreaseProductCount()

}

class DefaultProductDetailUiState : ProductDetailUiState() {
    override val product: MutableLiveData<Product> = MutableLiveData(Product.NULL)
    override val productCount: MutableLiveData<Int> = MutableLiveData(FIRST_QUANTITY)
    override val latestProduct: MutableLiveData<Product> = MutableLiveData(Product.NULL)

    override fun currentProduct(): Product = product.value ?: throw IllegalStateException("Product is null")

    override fun currentQuantity(): Int = productCount.value ?: throw IllegalStateException("Product count is null")

    override fun postLatestProduct(product: Product) {
        latestProduct.postValue(product)
    }

    override fun postCurrentProduct(product: Product) {
        this.product.postValue(product)
    }

    override fun increaseProductCount() {
        productCount.postValue(productCount.value?.plus(CHANGE_AMOUNT))
    }

    override fun decreaseProductCount() {
        val currentCount = productCount.value ?: throw IllegalStateException("Product count is null")
        if (currentCount > MINIMUM_QUANTITY) {
            productCount.postValue(currentCount - CHANGE_AMOUNT)
        }
    }

    companion object {
        private const val FIRST_QUANTITY = 1
        private const val MINIMUM_QUANTITY = 1
        private const val CHANGE_AMOUNT = 1
    }
}