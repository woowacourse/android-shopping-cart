package woowacourse.shopping.data.product.remote

import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class ProductRemoteRepository(private val productService: ProductService) : ProductRepository {
    override fun find(id: Long): Product = productService.find(id)

    override fun findAll(): List<Product> = productService.findAll()

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> = productService.findRange(page, pageSize)
}
