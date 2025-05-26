package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class FakeRecentProductRepository : RecentProductRepository {
    var recentProduct: Product? = null

    override fun getRecentProducts(onResult: (Result<List<Product>>) -> Unit) {
    }

    override fun getMostRecentProduct(onResult: (Result<Product?>) -> Unit) {
        onResult(Result.success(ProductsFixture.dummyProduct))
    }

    override fun insertRecentProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        onResult(Result.success(Unit))
    }
}
