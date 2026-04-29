package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DUMMY_PRODUCTS
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems
import woowacourse.shopping.domain.repository.ProductRepository

object ProductRepositoryImpl : ProductRepository {

    override fun getProducts(): ProductItems {
        return ProductItems(DUMMY_PRODUCTS)
    }

    override fun getProduct(id: String): Product {
        return DUMMY_PRODUCTS.find { it.id == id }
            ?: throw IllegalArgumentException("상품이 존재하지 않습니다.")
    }
}
