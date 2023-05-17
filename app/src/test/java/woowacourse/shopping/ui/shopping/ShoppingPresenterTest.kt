package woowacourse.shopping.ui.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ShoppingPresenterTest() {
    private lateinit var view: ShoppingContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private lateinit var presenter: ShoppingPresenter
    private val pagingSize = 10
    private val hasNextStandard = 1
    private val onceLoadingSize = pagingSize + hasNextStandard

    @Before
    fun initPresenter() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        presenter = ShoppingPresenter(view, productRepository, recentProductRepository)
    }

    @Test
    fun `상품 데이터를 불러오면 화면이 업데이트 된다`() {
        // given
        every { view.updateProducts(any()) } just runs
        every { productRepository.getPartially(any(), any()) } returns List(onceLoadingSize) {
            Product(
                1,
                "더미입니다만",
                Price(1000),
                "url"
            )
        }

        // when
        presenter.fetchProducts()

        // then
        verify(exactly = 1) { view.updateProducts(any()) }
    }

    @Test
    fun `최근 본 상품 목록을 업데이트 하면 화면 업데이트 로직도 호출된다`() {
        // given
        every { view.updateRecentProducts(any()) } just runs
        every { recentProductRepository.getPartially(any()) } returns listOf(
            RecentProduct(
                1,
                Product(1, "더미입니다만", Price(1000), "url")
            )
        )
        // when
        presenter.fetchRecentProducts()

        // then
        verify(exactly = 1) { view.updateRecentProducts(any()) }
    }
}
