package woowacourse.shopping.product.catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.repository.FakeCartProductRepositoryImpl
import woowacourse.shopping.data.repository.FakeCatalogProductRepositoryImpl
import woowacourse.shopping.data.repository.FakeRecentlyViewedProductRepositoryImpl
import woowacourse.shopping.util.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CatalogViewModel

    @Test
    fun `초기 상태 테스트`() {
        val catalogProductRepositoryImpl = FakeCatalogProductRepositoryImpl(size = 20)
        viewModel =
            CatalogViewModel(
                catalogProductRepository = catalogProductRepositoryImpl,
                cartProductRepository = FakeCartProductRepositoryImpl(),
                recentlyViewedProductRepository =
                    FakeRecentlyViewedProductRepositoryImpl(catalogProductRepositoryImpl),
            )

        assertThat(0).isEqualTo(viewModel.page.value)

        val catalogProducts: List<CatalogItem> = viewModel.catalogItems.value ?: emptyList()

        assertThat(catalogProducts.size).isEqualTo(20)
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 페이지가 증가되고 상품이 로드된다`() {
        // given
        val catalogProductRepositoryImpl = FakeCatalogProductRepositoryImpl(size = 25)
        viewModel =
            CatalogViewModel(
                catalogProductRepository = catalogProductRepositoryImpl,
                cartProductRepository = FakeCartProductRepositoryImpl(),
                recentlyViewedProductRepository =
                    FakeRecentlyViewedProductRepositoryImpl(catalogProductRepositoryImpl),
            )
        assertThat(viewModel.page.value).isEqualTo(0)

        // when
        viewModel.loadNextCatalogProducts()

        // then
        assertThat(viewModel.page.value).isEqualTo(1)
        assertThat(viewModel.catalogItems.value?.size).isEqualTo(25)
    }

    @Test
    fun `상품 목록이 20개 이상이면 더보기 버튼이 활성화된다`() {
        val catalogProductRepositoryImpl = FakeCatalogProductRepositoryImpl(size = 20)
        viewModel =
            CatalogViewModel(
                catalogProductRepository = catalogProductRepositoryImpl,
                cartProductRepository = FakeCartProductRepositoryImpl(),
                recentlyViewedProductRepository =
                    FakeRecentlyViewedProductRepositoryImpl(catalogProductRepositoryImpl),
            )

        // 페이지 항목 20개
        assertThat(viewModel.catalogItems.value?.size).isEqualTo(20)
    }
}
