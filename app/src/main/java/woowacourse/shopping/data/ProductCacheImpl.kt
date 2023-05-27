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

    override fun getSubProducts(page: Int, size: Int): List<Product> {
        val startIndex = size * (page - 1)
        if (startIndex > _productList.size) return emptyList()
        if (startIndex + size >= _productList.size) return productList.subList(
            startIndex,
            _productList.size
        )
        return _productList.subList(startIndex, startIndex + size)
    }

    override fun clear() {
        _productList.clear()
    }
}
