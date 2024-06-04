package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class RemoteProductRepositoryImpl(private val productApiService: ProductApiService = MockProductApiService()) :
    ProductRepository {
    override fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Product>> {
        var result: Result<List<Product>>? = null
        thread {
            result =
                runCatching {
                    productApiService.load(startPage, pageSize)
                }
        }.join()
        return result ?: throw NoSuchElementException()
    }

    override fun loadById(id: Long): Result<Product> {
        var result: Result<Product>? = null
        thread {
            result =
                runCatching {
                    productApiService.loadById(id)
                }
        }.join()
        return result ?: throw NoSuchElementException()
    }
}
