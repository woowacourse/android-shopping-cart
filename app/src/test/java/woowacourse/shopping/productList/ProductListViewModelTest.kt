package woowacourse.shopping.productList

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeProductHistorySource
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productDomainsTestFixture
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.ui.productList.DefaultProductListViewModel
import woowacourse.shopping.ui.productList.ProductListViewModel
import woowacourse.shopping.ui.productList.event.ProductListEvent
import java.util.concurrent.CountDownLatch

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var productSource: ProductDataSource
    private lateinit var cartSource: ShoppingCartProductIdDataSource
    private lateinit var historyDataSource: ProductHistoryDataSource

    private lateinit var shoppingProductRepository: ShoppingProductsRepository
    private lateinit var historyRepository: ProductHistoryRepository

    private lateinit var viewModel: ProductListViewModel

    /**
     * setup 에서 장바구니에는 아무런 데이터도 없도록 만든다
     */
    @BeforeEach
    fun setUp() {
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(60).toMutableList(),
            )
        cartSource = FakeShoppingCartProductIdDataSource()
        historyDataSource = FakeProductHistorySource()

        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)
    }


    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 상품 20개 로드`() {
        // given setup
        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val loadedProducts = viewModel.uiState.loadedProducts.getOrAwaitValue()
        assertThat(loadedProducts).isEqualTo(
            productsTestFixture(20).map { it.toDomain(0) },
        )
    }


    // TODO: 테스트 터짐. 이런 시나리오를 테스트 하려면?
    @Disabled
    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 첫 페이지에서 다음 페이지 더보기를 누르면 상품 40개 로드`() {
        // given setup
        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)
        val latch = CountDownLatch(2)

        // when
        viewModel.loadAll().also {
            latch.countDown()
        }

        viewModel.loadNextPageProducts()

        latch.await()
        val loadedProducts = viewModel.uiState.loadedProducts.getOrAwaitValue()
        assertThat(loadedProducts).isEqualTo(
            productDomainsTestFixture(40)
        )
    }

    @Test
    fun `총 데이터가 15개일 때 현재 페이지는 1페이지`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(15).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)
        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val currentPage = viewModel.uiState.page
        assertThat(currentPage.getOrAwaitValue()).isEqualTo(1)
    }

    @Test
    fun `총 데이터가 20개일 때 첫 페이지가 마지막 페이지이다`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(20).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)

        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val isLastPage = viewModel.uiState.isLastPage.value
        assertThat(isLastPage).isTrue
    }

    @Test
    fun `총 데이터가 21개일 대 첫 페이지가 마지막 페이지가 아니다`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)

        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val isLastPage = viewModel.uiState.isLastPage.getOrAwaitValue()
        assertThat(isLastPage).isFalse
    }

    @Test
    fun `총 데이터가 21개일 때 두번째 페이지가 마지막 페이지이다`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)
        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        viewModel.loadNextPageProducts()

        // then
        assertThat(viewModel.uiState.isLastPage.getOrAwaitValue()).isTrue
    }

    @Test
    fun `장바구니에 담긴 상품들의 개수를 로드`() {
        // given
        productSource = FakeProductDataSource(allProducts = productsTestFixture(21).toMutableList())
        cartSource = FakeShoppingCartProductIdDataSource(data = productsIdCountDataTestFixture(10).toMutableList())
        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)

        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        assertThat(viewModel.uiState.cartProductTotalCount.getOrAwaitValue()).isEqualTo(10)
    }

    @Test
    fun `상품 상세로 이동하기 위한 id 저장`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(5).toMutableList(),
            )
        shoppingProductRepository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)
        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        viewModel.navigateToDetail(productId = 3)

        // then
        val navigation = viewModel.navigationEvent.getValue()
        assertThat(navigation).isEqualTo(ProductListEvent.NavigateToProductDetail(3))
    }

    @Test
    fun `최근 본 상품 내역 로드`() {
        // given
        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyDataSource =
            FakeProductHistorySource(
                history = mutableListOf(0, 1, 2, 3, 4)
            )
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)
        viewModel = DefaultProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val actual = viewModel.uiState.productsHistory.getOrAwaitValue()
        assertThat(actual).isEqualTo(
            productDomainsTestFixture(5)
        )
    }
}
