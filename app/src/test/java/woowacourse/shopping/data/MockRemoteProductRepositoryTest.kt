package woowacourse.shopping.data

import com.example.domain.cache.ProductLocalCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.dataSource.remote.MockProductRemoteService
import woowacourse.shopping.data.repository.remote.MockRemoteProductRepositoryImpl
import java.util.concurrent.CountDownLatch

internal class MockRemoteProductRepositoryTest {
    private lateinit var mockRemoteProductRepository: ProductRepository
    private val mockProductRemoteService: MockProductRemoteService =
        MockProductRemoteService()

    @Before
    fun init() {
        mockRemoteProductRepository =
            MockRemoteProductRepositoryImpl(mockProductRemoteService, ProductLocalCache)
        ProductLocalCache.clear()
    }

    @Test
    fun `처음 20개의 상품을 불러온다`() {
        // given
        var actual: List<Product> = listOf()
        val latch = CountDownLatch(1)

        // when
        mockRemoteProductRepository.fetchFirstProducts(
            onSuccess = {
                actual = it
                latch.countDown()
            },
            onFailure = {}
        )
        latch.await()

        // then
        val expected = productsDatasource.subList(0, 20)
        assert(actual == expected)
    }

    @Test
    fun `20개의 상품을 받은 상태에서 다음 20개의 상품을 추가적으로 더 불러온다`() {
        // given
        var actual: List<Product> = listOf()
        val latch = CountDownLatch(1)

        // when
        mockRemoteProductRepository.fetchNextProducts(
            20L,
            onSuccess = {
                actual = it
                latch.countDown()
            },
            onFailure = {}
        )
        latch.await()

        // then
        val expected = productsDatasource.subList(20, 40)
        assert(actual == expected)
    }

    @Test
    fun `40번 아이디 상품까지 받은 상태에서 추가적으로 더 불러온다`() {
        // given
        var actual: List<Product> = listOf()
        val latch = CountDownLatch(1)

        // when
        mockRemoteProductRepository.fetchNextProducts(
            40L,
            onSuccess = {
                actual = it
                latch.countDown()
            },
            onFailure = {}
        )
        latch.await()

        // then
        val expected = productsDatasource.subList(40, 41)
        assert(actual == expected)
    }
}
