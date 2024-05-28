package woowacourse.shopping.data.shopping.product

import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.remote.service.ProductService

class DefaultProductDataSource(
    private val productService: ProductService,
) : ProductDataSource {
    override fun products(
        currentPage: Int,
        size: Int,
    ): Result<ProductPageData> {
        return runCatching {
            productService.fetchProducts(currentPage, size)
                .toDataModel()
        }
    }

    override fun productById(id: Long): Result<Product> {
        return runCatching {
            productService.fetchProductById(id).toProduct()
        }
    }

    override fun canLoadMore(
        page: Int,
        size: Int,
    ): Result<Boolean> {
        return runCatching {
            productService.canLoadMore(page, size)
        }
    }
}
