package woowacourse.shopping.productList

import android.os.Handler
import android.os.Looper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
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
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeProductHistorySource
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.ui.productList.ProductListViewModel

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
        mockkStatic(Looper::class)
        mockkConstructor(Handler::class)
        val mockMainLooper =
            mockk<Looper> {
                every { thread } returns Thread.currentThread()
            }
        every { Looper.getMainLooper() } returns mockMainLooper
        every { anyConstructed<Handler>().post(any()) } answers {
            firstArg<Runnable>().run()
            true
        }

        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(60).toMutableList(),
            )
        cartSource = FakeShoppingCartProductIdDataSource()
        historyDataSource = FakeProductHistorySource()

        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 상품 20개 로드`() {
        // given setup
        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        Thread.sleep(1000) // todo: thread sleep 을 쓰지 않고 테스트 해야 함

        // then
        val loadedProducts = viewModel.loadedProducts.getOrAwaitValue()
        assertThat(loadedProducts).isEqualTo(
            productsTestFixture(20).map { it.toDomain(0) },
        )
    }

    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 상품 40개 로드`() {
        // given setup
        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        Thread.sleep(1000) // todo: thread sleep 을 쓰지 않고 테스트 해야 함

        viewModel.loadNextPageProducts()
        Thread.sleep(1000) // todo: thread sleep 을 쓰지 않고 테스트 해야 함

        // then
        val loadedProducts = viewModel.loadedProducts
        assertThat(loadedProducts.getOrAwaitValue()).isEqualTo(
            productsTestFixture(40).map { it.toDomain(0) },
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
        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val currentPage = viewModel.currentPage
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

        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        Thread.sleep(1000) // todo: thread sleep 을 쓰지 않고 테스트 해야 함

        // then
        val isLastPage = viewModel.isLastPage.value
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

        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val isLastPage = viewModel.isLastPage.getOrAwaitValue()
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
        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        Thread.sleep(1000) // todo: thread sleep 을 쓰지 않고 테스트 해야 함
        viewModel.loadNextPageProducts()
        Thread.sleep(1000) // todo: thread sleep 을 쓰지 않고 테스트 해야 함

        // then
        assertThat(viewModel.isLastPage.getOrAwaitValue()).isTrue
    }

    @Test
    fun `장바구니에 담긴 상품들의 개수를 로드`() {
        // given
        productSource = FakeProductDataSource(allProducts = productsTestFixture(21).toMutableList())
        cartSource = FakeShoppingCartProductIdDataSource(data = productsIdCountDataTestFixture(10).toMutableList())
        shoppingProductRepository = DefaultShoppingProductRepository(productSource, cartSource)
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)

        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        assertThat(viewModel.cartProductTotalCount.getOrAwaitValue()).isEqualTo(10)
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
        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        viewModel.onClick(productId = 3)

        // then
        val productDetailId = viewModel.detailProductDestinationId.getValue()
        val expected: Long = 3
        assertThat(productDetailId).isEqualTo(expected)
    }

    @Test
    fun `최근 본 상품 내역 로드`() {
        // given
        historyDataSource =
            FakeProductHistorySource(
                history = ArrayDeque<Long>(listOf(1, 2, 3, 4, 5)),
            )
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productSource)
        viewModel = ProductListViewModel(shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()
        Thread.sleep(1000) // todo: thread sleep 을 쓰지 않고 테스트 해야 함

        // then
        val actual = viewModel.productsHistory.getOrAwaitValue()
        assertThat(actual).isEqualTo(
            productsTestFixture(5) {
                productTestFixture(id = it.toLong() + 1)
            }.map { it.toDomain(0) },
        )
    }
}
