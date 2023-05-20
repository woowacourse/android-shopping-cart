package woowacourse.shopping.feature.detail

import com.example.domain.datasource.productsDatasource
import com.example.domain.model.RecentProduct
import com.example.domain.repository.RecentProductRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.RecentProductUiModel
import java.time.LocalDateTime

internal class DetailPresenterTest {
    private lateinit var view: DetailContract.View
    private lateinit var presenter: DetailContract.Presenter
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun init() {
        view = mockk(relaxed = true)
        recentProductRepository = mockk()
    }

    @Test
    fun `최근 본 상품의 상세 페이지이면 최근 본 상품 바로가기 뷰를 숨긴다`() {
        // given
        presenter = DetailPresenter(
            view,
            recentProductRepository,
            mockProduct1,
            mockRecentProduct1.toPresentation()
        )

        // when
        presenter.initScreen()

        verify { view.hideRecentScreen() }
    }

    @Test
    fun `최근 본 상품이 아닌 상품의 상세 페이지이면 최근 본 상품 바로가기 뷰를 보이도록 한다`() {
        // given
        presenter = DetailPresenter(
            view,
            recentProductRepository,
            mockProduct1,
            mockRecentProduct2.toPresentation()
        )

        // when
        presenter.initScreen()

        verify { view.setRecentScreen(any(), any()) }
    }

    @Test
    fun `최근 본 상품의 상세 페이지로 이동한다`() {
        // given
        presenter = DetailPresenter(
            view,
            recentProductRepository,
            mockProduct1,
            mockRecentProduct2.toPresentation()
        )
        val recentProductSlot = slot<RecentProduct>()
        every { recentProductRepository.addRecentProduct(capture(recentProductSlot)) } just Runs

        val recentProductUiModel = slot<RecentProductUiModel>()
        every { view.showRecentProductDetailScreen(capture(recentProductUiModel)) } just Runs

        // when
        presenter.navigateRecentProductDetail()

        // then
        verify { recentProductRepository.addRecentProduct(recentProductSlot.captured) }
        verify { view.showRecentProductDetailScreen(recentProductUiModel.captured) }
    }

    @Test
    fun `상품 추가 선택 화면을 보여준다`() {
        // given
        presenter = DetailPresenter(
            view,
            recentProductRepository,
            mockProduct1,
            mockRecentProduct2.toPresentation()
        )

        // when
        presenter.handleAddCartClick()

        // then
        verify { view.showSelectCartProductCountScreen(presenter.product) }
    }

    private val mockProduct1 = productsDatasource[0].toPresentation()
    private val mockProduct2 = productsDatasource[1].toPresentation()
    private val mockRecentProduct1 = RecentProduct(mockProduct1.toDomain(), LocalDateTime.now())
    private val mockRecentProduct2 = RecentProduct(mockProduct2.toDomain(), LocalDateTime.now())
}
