package woowacourse.shopping.data.goods.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.Goods
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class GoodsRepositoryTest {
    private lateinit var repository: GoodsRepositoryImpl
    private val remoteDataSource = mockk<GoodsRemoteDataSource>()
    private val localDataSource = mockk<GoodsLocalDataSource>()

    @BeforeEach
    fun setup() {
        repository = GoodsRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `상품 목록 조회는 원격 데이터소스에 위임`() {
        // Given
        val expectedGoods = listOf(Goods("상품1", 10000, "url1", 1))

        every { remoteDataSource.fetchPageGoods(10, 0, any()) } answers {
            thirdArg<(List<Goods>) -> Unit>()(expectedGoods)
        }

        // When
        val result =
            executeCallback<List<Goods>> {
                repository.fetchPageGoods(10, 0, it)
            }

        // Then
        assertThat(result).hasSize(1)
        verify { remoteDataSource.fetchPageGoods(10, 0, any()) }
    }

    @Test
    fun `최근 본 상품 저장은 로컬 데이터소스에 위임`() {
        // Given
        val goods = Goods("상품", 10000, "url", 1)
        every { localDataSource.loggingRecentGoods(goods, any()) } answers {
            secondArg<() -> Unit>()()
        }

        // When
        executeUnitCallback { repository.loggingRecentGoods(goods, it) }

        // Then
        verify { localDataSource.loggingRecentGoods(goods, any()) }
    }

    @Test
    fun `최근 본 상품 목록 조회 - 로컬ID 조회 후 원격 상세정보 조회`() {
        // Given
        val ids = listOf("1", "2")
        val goods1 = Goods("상품1", 10000, "url1", 1)
        val goods2 = Goods("상품2", 20000, "url2", 2)

        // Local: ID 목록 반환
        every { localDataSource.fetchRecentGoodsIds(any()) } answers {
            firstArg<(List<String>) -> Unit>()(ids)
        }

        // Remote: 각 상품 상세정보 반환
        every { remoteDataSource.fetchGoodsById(1, any()) } answers {
            secondArg<(Goods?) -> Unit>()(goods1)
        }
        every { remoteDataSource.fetchGoodsById(2, any()) } answers {
            secondArg<(Goods?) -> Unit>()(goods2)
        }

        // When
        val result =
            executeCallback<List<Goods>> {
                repository.fetchRecentGoods(it)
            }

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("상품1") // 순서 유지

        verify { localDataSource.fetchRecentGoodsIds(any()) }
        verify { remoteDataSource.fetchGoodsById(1, any()) }
        verify { remoteDataSource.fetchGoodsById(2, any()) }
    }

    @Test
    fun `최근 본 상품이 없으면 빈 목록 반환`() {
        // Given
        every { localDataSource.fetchRecentGoodsIds(any()) } answers {
            firstArg<(List<String>) -> Unit>()(emptyList())
        }

        // When
        val result =
            executeCallback<List<Goods>> {
                repository.fetchRecentGoods(it)
            }

        // Then
        assertThat(result).isEmpty()
        verify(exactly = 0) { remoteDataSource.fetchGoodsById(any(), any()) }
    }

    @Test
    fun `가장 최근 상품 조회 - 첫번째 ID만 조회`() {
        // Given
        val ids = listOf("5", "3", "1")
        val mostRecentGoods = Goods("최신상품", 15000, "url5", 5)

        every { localDataSource.fetchRecentGoodsIds(any()) } answers {
            firstArg<(List<String>) -> Unit>()(ids)
        }
        every { remoteDataSource.fetchGoodsById(5, any()) } answers {
            secondArg<(Goods?) -> Unit>()(mostRecentGoods)
        }

        // When
        val result =
            executeCallback<Goods?> {
                repository.fetchMostRecentGoods(it)
            }

        // Then
        assertThat(result?.name).isEqualTo("최신상품")
        verify { remoteDataSource.fetchGoodsById(5, any()) }
        verify(exactly = 0) { remoteDataSource.fetchGoodsById(3, any()) } // 첫번째만 조회
    }

    private fun <T> executeCallback(action: ((T) -> Unit) -> Unit): T {
        val latch = CountDownLatch(1)
        var result: T? = null
        action {
            result = it
            latch.countDown()
        }
        latch.await(3, TimeUnit.SECONDS)
        return result!!
    }

    private fun executeUnitCallback(action: (() -> Unit) -> Unit) {
        val latch = CountDownLatch(1)
        action { latch.countDown() }
        latch.await(3, TimeUnit.SECONDS)
    }
}
