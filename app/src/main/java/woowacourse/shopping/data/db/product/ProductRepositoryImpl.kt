package woowacourse.shopping.data.db.shopping

import woowacourse.shopping.data.db.product.ProductMockWebServer
import woowacourse.shopping.data.db.product.ProductRepository
import woowacourse.shopping.data.db.product.ProductService
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.home.HomeViewModel
import kotlin.concurrent.thread

class ProductRepositoryImpl : ProductRepository {
    private val productMockWebServer: ProductService = ProductMockWebServer()

    private var offset = 0

    override fun findProductsByPage(): List<Product> {
        var pageProducts: List<Product> = emptyList()
        thread {
            val size = productMockWebServer.getSize()
            val start = offset
            offset = Integer.min(offset + HomeViewModel.PAGE_SIZE, size)
            pageProducts = productMockWebServer.findPageProducts(start, offset)
        }.join()

        return pageProducts
    }

    override fun findProductById(id: Long): Product? {
        var product: Product? = null
        thread {
            product = productMockWebServer.findProductById(id)
        }.join()

        return product
    }

    override fun canLoadMore(): Boolean {
        var size = 0
        thread {
            size = productMockWebServer.getSize()
        }.join()

        return !(size < HomeViewModel.PAGE_SIZE || offset == size)
    }
}
