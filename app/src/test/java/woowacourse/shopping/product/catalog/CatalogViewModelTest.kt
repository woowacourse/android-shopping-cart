package woowacourse.shopping.product.catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.product.catalog.fixture.FakeMockProducts
import woowacourse.shopping.util.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CatalogViewModel

    @Test
    fun `초기 상태 테스트`() {
        viewModel = CatalogViewModel(FakeMockProducts(size = 10))

        val paging = viewModel.pagingData.value!!

        assertThat(viewModel.page).isEqualTo(0)
        assertThat(paging.hasNext).isFalse
        assertThat(paging.products).hasSize(10)
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 페이지가 증가되고 상품이 로드된다`() {
        // given
        viewModel = CatalogViewModel(FakeMockProducts(size = 30))
        val catalogProducts: List<ProductUiModel> = (viewModel.products)
        assertThat(viewModel.page).isEqualTo(0)

        // when
        viewModel.loadNextCatalogProducts(PAGE_SIZE)

        // then
        assertThat(viewModel.page).isEqualTo(1)
        assertThat(catalogProducts.size).isEqualTo(20)
    }

    @Test
    fun `상품 목록이 20개 이상이면 더보기 버튼이 활성화된다`() {
        viewModel = CatalogViewModel(FakeMockProducts(size = 30))

        val hasNext = viewModel.pagingData.value?.hasNext

        assertThat(hasNext).isEqualTo(true)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
