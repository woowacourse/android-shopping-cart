package woowacourse.shopping.presentation.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.fakeProductRepository
import woowacourse.shopping.presentation.view.catalog.CatalogViewModel
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.view.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CatalogViewModelTest {
    private lateinit var viewModel: CatalogViewModel

    @BeforeEach
    fun setUp() {
        viewModel = CatalogViewModel(fakeProductRepository)
    }

    @Test
    fun `초기화 시 상품 목록이 로드된다`() {
        val observedItems = viewModel.products.getOrAwaitValue()

        assertAll(
            { assertThat(observedItems).isNotNull },
            { assertThat(observedItems).isNotEmpty },
        )
    }

    @Test
    fun `새로운 상품 조회 시 중복 없이 제품 목록이 누적된다`() {
        viewModel.fetchProducts()

        val observedItems = viewModel.products.getOrAwaitValue()
        val distinctCount =
            observedItems.map { (it as CatalogItem.ProductItem).product.id }.distinct().count()

        assertAll(
            { assertThat(observedItems.size).isGreaterThanOrEqualTo(2) },
            { assertThat(observedItems.size).isEqualTo(distinctCount) },
        )
    }

    @Test
    fun `hasMore가 true일 때 LoadMoreItem이 포함된다`() {
        val observedItems = viewModel.products.getOrAwaitValue()
        val lastProduct = observedItems.lastOrNull()

        assertThat(lastProduct).isInstanceOf(CatalogItem.LoadMoreItem::class.java)
    }
}
