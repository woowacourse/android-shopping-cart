package woowacourse.shopping.presentation.ui.productlist

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyData.PRODUCT_LIST
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(MockKExtension::class, InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @MockK
    private lateinit var repository: ProductRepository

    @BeforeEach
    fun setUp() {
        every { repository.getPagingProduct(0, 20) } returns
            Result.success(PRODUCT_LIST.subList(0, 20))
        every { repository.getPagingProduct(1, 20) } returns
            Result.success(PRODUCT_LIST.subList(20, 40))

        viewModel = ProductListViewModel(repository)
    }

    @Test
    fun `상품을 불러온다`() {
        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingProduct.productList).isEqualTo(PRODUCT_LIST.subList(0, 20))
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 상품을 더 불러온다`() {
        // when
        viewModel.loadMoreProducts()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingProduct.productList).isEqualTo(PRODUCT_LIST.subList(0, 40))
    }

    @Test
    fun `상품을 누르면 상품의 상세 소개 화면으로 넘어가는 이벤트를 발생시킨다`() {
        // given
        val productList = PRODUCT_LIST.subList(0, 20)

        // when
        viewModel.navigateToProductDetail(productList.first().id)

        // then
        val actual = viewModel.navigateAction.getOrAwaitValue()
        val expected = ProductListNavigateAction.NavigateToProductDetail(productList.first().id)
        assertThat(actual.value).isEqualTo(expected)
    }
}
