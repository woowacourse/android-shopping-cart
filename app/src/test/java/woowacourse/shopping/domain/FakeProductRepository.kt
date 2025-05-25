package woowacourse.shopping.domain

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class FakeProductRepository : ProductRepository {
    override fun start(onResult: (Result<Unit>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun fetchProducts(onResult: (Result<List<Product>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun fetchProductById(
        productId: Long,
        onResult: (Result<Product>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun fetchCartItems(onResult: (Result<List<CartItem>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun fetchPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun shutdown(onResult: (Result<Unit>) -> Unit) {
        TODO("Not yet implemented")
    }
}
