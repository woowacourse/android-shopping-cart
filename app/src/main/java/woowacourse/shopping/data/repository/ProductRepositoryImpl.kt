package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DUMMY_PRODUCTS
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

object ProductRepositoryImpl : ProductRepository {
    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): ProductItems {
        require(page >= 0) { "페이지 번호는 0보다 크거나 같은 정수여야 합니다." }
        require(pageSize >= 1) { "페이지 사이즈는 1보다 큰 정수여야 합니다." }
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, DUMMY_PRODUCTS.size)

        require(fromIndex <= toIndex) { "끝 인덱스는 시작 인덱스보다 작거나 같아야 합니다." }

        val result = DUMMY_PRODUCTS.subList(fromIndex, toIndex)
        return ProductItems(result)
    }

    override fun getProductCount(): Int = DUMMY_PRODUCTS.size

    override fun getProduct(id: String): Product? =
        DUMMY_PRODUCTS.find { it.id == id }
}
