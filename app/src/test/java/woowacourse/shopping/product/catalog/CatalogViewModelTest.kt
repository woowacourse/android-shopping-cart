package woowacourse.shopping.product.catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.FakeCartItemRepository
import woowacourse.shopping.fixture.FakeCatalogItemRepository
import woowacourse.shopping.fixture.FakeViewedItemRepository
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CatalogViewModel

    @Test
    fun `초기 상태 테스트`() {
        viewModel =
            CatalogViewModel(
                FakeCatalogItemRepository(10),
                FakeCartItemRepository(0),
                FakeViewedItemRepository(0),
            )

        val paging = viewModel.pagingData.getOrAwaitValue()

        assertThat(viewModel.page).isEqualTo(0)
        assertThat(paging.hasNext).isFalse
        assertThat(paging.products).hasSize(10)
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 페이지가 증가되고 상품이 로드된다`() {
        // given
        viewModel =
            CatalogViewModel(
                FakeCatalogItemRepository(30),
                FakeCartItemRepository(0),
                FakeViewedItemRepository(0),
            )
        val catalogProducts: List<ProductUiModel> = (viewModel.pagingData.getOrAwaitValue().products)
        assertThat(viewModel.page).isEqualTo(0)

        // when
        viewModel.loadNextCatalogProducts()

        // then
        assertThat(viewModel.page).isEqualTo(1)
        assertThat(catalogProducts.size).isEqualTo(20)
    }

    @Test
    fun `상품 목록이 20개 이상이면 더보기 버튼이 활성화된다`() {
        // given
        viewModel =
            CatalogViewModel(
                FakeCatalogItemRepository(30),
                FakeCartItemRepository(0),
                FakeViewedItemRepository(0),
            )

        val hasNext = viewModel.pagingData.getOrAwaitValue().hasNext

        assertThat(hasNext).isEqualTo(true)
    }

    @Test
    fun `최근 본 상품이 있는 경우에 10개 이하이면 모든 데이터를 불러온다`() {
        viewModel =
            CatalogViewModel(
                FakeCatalogItemRepository(30),
                FakeCartItemRepository(0),
                FakeViewedItemRepository(9),
            )
        val viewedSize = viewModel.recentViewedItems.getOrAwaitValue().size
        val hasViewed = viewModel.hasRecentViewedItems.getOrAwaitValue()

        assertThat(viewedSize).isEqualTo(9)
        assertThat(hasViewed).isEqualTo(true)
    }

    @Test
    fun `최근 본 상품이 있는 경우에 10개 이상이면 10개의 데이터만 불러온다`() {
        viewModel =
            CatalogViewModel(
                FakeCatalogItemRepository(30),
                FakeCartItemRepository(0),
                FakeViewedItemRepository(20),
            )
        val viewedSize = viewModel.recentViewedItems.getOrAwaitValue().size
        val hasViewed = viewModel.hasRecentViewedItems.getOrAwaitValue()

        assertThat(viewedSize).isEqualTo(10)
        assertThat(hasViewed).isEqualTo(true)
    }
}
