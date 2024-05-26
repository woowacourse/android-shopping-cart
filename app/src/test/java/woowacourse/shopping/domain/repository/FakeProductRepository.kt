package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.fixture.getFixtureCartableProducts
import kotlin.math.min

class FakeProductRepository : ProductRepository {
    private val cartableProducts: List<CartableProduct> = getFixtureCartableProducts(100)

    override fun fetchSinglePage(page: Int): List<CartableProduct> {
        val fromIndex = page * 20
        val toIndex = min(fromIndex + 20, cartableProducts.size)
        return cartableProducts.subList(fromIndex, toIndex)
    }

    override fun fetchProduct(id: Long): CartableProduct {
        return cartableProducts.first { it.product.id == id }
    }
}
