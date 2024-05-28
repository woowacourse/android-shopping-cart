package woowacourse.shopping.presentation.ui.productlist

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.remote.api.DummyData.PRODUCT_LIST
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExtendWith(MockKExtension::class, InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @MockK
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @MockK
    private lateinit var productRepository: ProductRepository

    @MockK
    private lateinit var productHistoryRepository: ProductHistoryRepository

    @BeforeEach
    fun setUp() {
        every { productRepository.getPagingProduct(0, 20) } returns
            Result.success(PRODUCT_LIST.subList(0, 20).map { it.toDomain() })
        every { productRepository.getPagingProduct(1, 20) } returns
            Result.success(PRODUCT_LIST.subList(20, 40).map { it.toDomain() })
        every { productHistoryRepository.getProductHistory(any()) } returns Result.success(emptyList())
        every { shoppingCartRepository.getAllCartProducts() } returns Result.success(emptyList())

        viewModel =
            ProductListViewModel(
                productRepository,
                shoppingCartRepository,
                productHistoryRepository,
            )

        val latch = CountDownLatch(1)
        latch.await(1, TimeUnit.SECONDS)
    }

    @Test
    fun `상품을 불러온다`() {
        // then
        val actual = viewModel.uiState.getOrAwaitValue()

        assertThat(actual.pagingProduct.productList).isEqualTo(
            PRODUCT_LIST.subList(0, 20).map { it.toDomain() },
        )
    }

    @Test
    fun `더보기 버튼을 눌렀을 때 상품을 더 불러온다`() {
        // when
        every { shoppingCartRepository.getAllCartProducts() } returns Result.success(emptyList())
        viewModel.loadMoreProducts()
        Thread.sleep(3000)

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingProduct.productList).isEqualTo(
            PRODUCT_LIST.subList(0, 40).map { it.toDomain() },
        )
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
