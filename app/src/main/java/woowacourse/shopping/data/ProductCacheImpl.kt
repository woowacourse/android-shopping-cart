package woowacourse.shopping.data

import com.example.domain.ProductCache
import com.example.domain.model.Product

object ProductCacheImpl : ProductCache {
    private val _productList = mutableListOf<Product>()
    override val productList: List<Product>
        get() = _productList.toList()

    override fun addProducts(products: List<Product>) {
        _productList.addAll(products)
    }

    override fun clear() {
        _productList.clear()
    }
}
