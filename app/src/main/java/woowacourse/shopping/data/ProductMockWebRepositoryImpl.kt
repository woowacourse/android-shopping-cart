//package woowacourse.shopping.data
//
//import com.example.domain.cache.ProductCache
//import com.example.domain.cache.ProductLocalCache
//import com.example.domain.model.Product
//import com.example.domain.repository.ProductRepository
//import woowacourse.shopping.data.server.MockProductWebServer
//
//class ProductMockWebRepositoryImpl(
//    private val mockProductWebServer: MockProductWebServer,
//    override val cache: ProductCache = ProductLocalCache
//) : ProductRepository {
//    override fun getFirstProducts(): List<Product> {
//
//    }
//
//    override fun getNextProducts(lastProductId: Long): List<Product> {
//        TODO("Not yet implemented")
//    }
//
//    override fun resetCache() {
//        TODO("Not yet implemented")
//    }
//}