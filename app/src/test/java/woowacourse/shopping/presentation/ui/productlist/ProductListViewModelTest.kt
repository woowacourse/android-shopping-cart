package woowacourse.shopping.presentation.ui.productlist

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.DummyData
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ProductListViewModelTest {
    @MockK
    private lateinit var productListRepository: ProductListRepository

    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK
    private lateinit var historyRepository: HistoryRepository

    @InjectMockKs
    private lateinit var productListViewModel: ProductListViewModel

    @Test
    fun `상품을 불러온다`() {
        // given
        every { orderRepository.getOrders() } returns listOf(Order(1, 1, DummyData.STUB_PRODUCT_1))
        every {
            productListRepository.getPagingProduct(
                0,
                20,
            )
        } returns DummyProductList.getPagingProduct(0, 20)
        every { historyRepository.getHistories(any()) } returns List(10) { History(DummyData.STUB_PRODUCT_1, 1L) }

        // when
        productListViewModel.initPage()

        // then
        val actual = productListViewModel.uiState.getOrAwaitValue()
        val expected = DummyProductList.getPagingProduct(0, 20).getOrThrow()
        assertThat(actual.pagingProduct).isEqualTo(expected)
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 상품을 더 불러온다`() {
        // given
        every { orderRepository.getOrders() } returns listOf(Order(1, 1, DummyData.STUB_PRODUCT_1))
        every { historyRepository.getHistories(any()) } returns List(10) { History(DummyData.STUB_PRODUCT_1, 1L) }
        val dummyPagingProduct = DummyProductList.getPagingProduct(0, 20).getOrThrow()
        val nextDummyPagingProduct = DummyProductList.getPagingProduct(1, 20).getOrThrow()

        every {
            productListRepository.getPagingProduct(
                0,
                20,
            ).getOrThrow()
        } returns dummyPagingProduct

        every {
            productListRepository.getPagingProduct(
                1,
                20,
            ).getOrThrow()
        } returns nextDummyPagingProduct
        productListViewModel.initPage()

        // when
        productListViewModel.onClickLoadMoreButton()

        // then
        val actual = productListViewModel.uiState.getOrAwaitValue().pagingProduct
        val expected =
            PagingProduct(
                currentPage = nextDummyPagingProduct.currentPage,
                productList = dummyPagingProduct.productList + nextDummyPagingProduct.productList,
                isLastPage = nextDummyPagingProduct.isLastPage,
            )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `상품을 누르면 상품의 상세 소개 화면으로 넘어간다`() {
        // given & when
        productListViewModel.onClickProduct(1)
        val actual = productListViewModel.navigateAction.getOrAwaitValue()

        // then
        val expected = ProductListNavigateAction.NavigateToProductDetail(1)
        assertThat(actual.value).isEqualTo(expected)
    }

    @Test
    fun `상품의 더하기 버튼을 누르면 repo에 상품을 하나 더 주문하고 바뀐 주문 정보를 요청한다`() {
        // given
        every {
            productListRepository.getPagingProduct(
                0,
                20,
            )
        } returns DummyProductList.getPagingProduct(0, 20)
        every { productListRepository.findProductById(1) } returns Result.success(DummyData.STUB_PRODUCT_1)
        every { orderRepository.getOrders() } returns listOf(Order(1, 2, DummyData.STUB_PRODUCT_1))
        every { orderRepository.plusOrder(DummyData.STUB_PRODUCT_1) } just runs
        every { historyRepository.getHistories(any()) } returns List(10) { History(DummyData.STUB_PRODUCT_1, 1L) }
        productListViewModel.initPage()

        // when
        productListViewModel.onClickPlusOrderButton(1)
        productListViewModel.onClickPlusOrderButton(1)

        // then
        verify(exactly = 2) { orderRepository.plusOrder(DummyData.STUB_PRODUCT_1) }
        val actual = productListViewModel.pagingProductUiModel.value?.productUiModels?.get(0)?.quantity
        val expected = 2
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `상품의 빼기 버튼을 누르면 repo에 주문한 상품을 하나 빼고 바뀐 주문 정보를 요청한다`() {
        // given
        every {
            productListRepository.getPagingProduct(
                0,
                20,
            )
        } returns DummyProductList.getPagingProduct(0, 20)
        every { productListRepository.findProductById(1) } returns Result.success(DummyData.STUB_PRODUCT_1)
        every { orderRepository.getOrders() } returns listOf(Order(1, 1, DummyData.STUB_PRODUCT_1))
        every { orderRepository.minusOrder(DummyData.STUB_PRODUCT_1) } just runs
        every { historyRepository.getHistories(any()) } returns List(10) { History(DummyData.STUB_PRODUCT_1, 1L) }
        productListViewModel.initPage()

        // when
        productListViewModel.onClickMinusOrderButton(1)

        // then
        verify(exactly = 1) { orderRepository.minusOrder(DummyData.STUB_PRODUCT_1) }
        val uiState = productListViewModel.uiState.getOrAwaitValue()
        val actual = uiState.orders?.get(0)?.quantity
        val expected = 1
        assertThat(actual).isEqualTo(expected)
    }
}
