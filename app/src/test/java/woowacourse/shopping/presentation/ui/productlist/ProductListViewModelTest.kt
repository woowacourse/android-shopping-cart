package woowacourse.shopping.presentation.ui.productlist

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyData.PRODUCT_LIST
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @MockK
    private lateinit var repository: ProductRepository

    private fun initViewModel() {
        viewModel = ProductListViewModel(repository)
    }

    @Test
    fun `상품을 불러온다`() {
        // given
        every { repository.getPagingProduct(0, 20) } returns Result.success(PRODUCT_LIST)
        initViewModel()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assert(actual.pagingProduct.productList == PRODUCT_LIST)
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 상품을 더 불러온다`() {
        // given
        every { repository.getPagingProduct(0, 20) } returns
            Result.success(PRODUCT_LIST.subList(0, 20))
        every { repository.getPagingProduct(1, 20) } returns
            Result.success(PRODUCT_LIST.subList(20, 40))
        initViewModel()

        // when
        viewModel.onClickLoadMoreButton()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assert(actual.pagingProduct.productList == PRODUCT_LIST.subList(0, 40))
    }

    @Test
    fun `상품을 누르면 상품의 상세 소개 화면으로 넘어가는 이벤트를 발생시킨다`() {
        // given
        val productList = PRODUCT_LIST.subList(0, 20)
        every { repository.getPagingProduct(0, 20) } returns
            Result.success(productList)
        initViewModel()

        // when
        viewModel.onClickProduct(productList.first().id)

        // then
        val actual = viewModel.navigateAction.getOrAwaitValue()
        val expected = ProductListNavigateAction.NavigateToProductDetail(productList.first().id)
        assert(actual.value == expected)
    }
}
