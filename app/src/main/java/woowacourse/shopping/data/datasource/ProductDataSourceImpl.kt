package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.remote.ProductService
import woowacourse.shopping.domain.model.Product

class ProductDataSourceImpl(
    private val productService: ProductService,
) : ProductDataSource {
    override fun start(): Result<Unit> = Result.success(productService.start())

    override fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>> {
        val offset = page * pageSize
        return try {
            Result.success(productService.fetchPagingProducts(offset, pageSize))
        } catch (e: Exception) {
            Result.failure(Exception("상품 목록 조회에 실패했습니다: $e"))
        }
    }

    override fun fetchProductById(id: Long): Result<Product> =
        try {
            Result.success(productService.fetchProductById(id))
        } catch (e: Exception) {
            Result.failure(Exception("상품 조회에 실패했습니다: $e"))
        }

    override fun shutdown(): Result<Unit> = Result.success(productService.shutdown())
}
