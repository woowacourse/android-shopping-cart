package woowacourse.shopping.presentation.ui.productlist

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListPagingSource

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    val repository: ProductRepository = DummyProductRepositoryImpl

    @Test
    fun `상품을 불러온다`() {
        // given
        val productListViewModel: ProductListViewModel = ProductListViewModel(repository)

        // when
        val actual = productListViewModel.uiState.getOrAwaitValue()

        // then
        val expected = DummyProductRepositoryImpl.getPagingProduct(0, 20).getOrThrow()
        assertThat(actual.pagingProduct.productList).isEqualTo(expected)
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 상품을 더 불러온다`() {
        // given
        val productListPagingSource = ProductListPagingSource(repository)
        val dummyPagingProduct = productListPagingSource.load().getOrThrow()
        val nextDummyPagingProduct = productListPagingSource.load().getOrThrow()

        // when
        val productListViewModel: ProductListViewModel = ProductListViewModel(DummyProductRepositoryImpl)
        productListViewModel.onClickLoadMoreButton()
        val actual = productListViewModel.uiState.getOrAwaitValue()

        // then
        val expected =
            PagingProduct(
                productList = dummyPagingProduct.productList + nextDummyPagingProduct.productList,
                last = nextDummyPagingProduct.last,
            )
        assertThat(actual.pagingProduct).isEqualTo(expected)
    }

    @Test
    fun `상품을 누르면 상품의 상세 소개 화면으로 넘어간다`() {
        // given & when
        val productListViewModel: ProductListViewModel =
            ProductListViewModel(DummyProductRepositoryImpl)
        productListViewModel.onClickProduct(1)
        val actual = productListViewModel.navigateAction.getOrAwaitValue()

        // then
        val expected = ProductListNavigateAction.NavigateToProductDetail(1)
        assertThat(actual.value).isEqualTo(expected)
    }
}
