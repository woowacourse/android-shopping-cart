package woowacourse.shopping.product.catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.product.catalog.fixture.FakeCatalogProductRepository
import woowacourse.shopping.util.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CatalogViewModel

    @Test
    fun `초기 상태 테스트`() {
        viewModel = CatalogViewModel(FakeCatalogProductRepository(size = 20))

        assertThat(0).isEqualTo(viewModel.page.value)

        val catalogProducts: List<CatalogItem> = viewModel.catalogItems.value ?: emptyList()

        assertThat(catalogProducts.size).isEqualTo(20)
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 페이지가 증가되고 상품이 로드된다`() {
        // given
        viewModel = CatalogViewModel(FakeCatalogProductRepository(size = 25))
        val catalogProducts: List<CatalogItem> = (viewModel.catalogItems.value ?: emptyList())
        assertThat(viewModel.page.value).isEqualTo(0)

        // when
        viewModel.loadNextCatalogProducts(20)

        // then
        assertThat(viewModel.page.value).isEqualTo(1)
        assertThat(viewModel.catalogItems.value?.size).isEqualTo(5)
    }

    @Test
    fun `상품 목록이 20개 이상이면 더보기 버튼이 활성화된다`() {
        viewModel = CatalogViewModel(FakeCatalogProductRepository(size = 25))

        // 0 페이지 항목 20개 + 더보기 버튼 1개 = 21개
        assertThat(viewModel.catalogItems.value?.size).isEqualTo(21)
    }
}
