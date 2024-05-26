package woowacourse.shopping.productDetail

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
import woowacourse.shopping.domain.model.Product
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
import woowacourse.shopping.ui.productDetail.ProductDetailViewModel
import kotlin.concurrent.thread

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private var productId: Long = -1
    private lateinit var productsSource: ProductDataSource
    private lateinit var cartSource: ShoppingCartProductIdDataSource
    private lateinit var shoppingProductRepository: ShoppingProductsRepository

    private lateinit var historyDataSource: ProductHistoryDataSource
    private lateinit var historyRepository: ProductHistoryRepository
    private lateinit var viewModel: ProductDetailViewModel

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

        productId = 1
        productsSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(40).toMutableList(),
            )
        cartSource = FakeShoppingCartProductIdDataSource(data = mutableListOf())
        shoppingProductRepository = DefaultShoppingProductRepository(productsSource, cartSource)

        historyDataSource = FakeProductHistorySource()
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productsSource)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `현재 상품을 표시한다`() {
        // given
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(3, 2).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepository(productsSource, cartSource)
        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val actualProduct = viewModel.currentProduct.getOrAwaitValue()
        val expectedProduct = productTestFixture(1).toDomain(quantity = 2)
        assertThat(actualProduct).isEqualTo(expectedProduct)
    }

    @Test
    fun `현재 상품의 개수를 ui 에서만 더한다`() {
        // given
        cartSource = FakeShoppingCartProductIdDataSource(data = mutableListOf())
        shoppingProductRepository = DefaultShoppingProductRepository(productsSource, cartSource)
        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.onIncrease(productId)

        // then
        val actualCount = viewModel.productCount.getOrAwaitValue()
        val expectedCount = 2
        assertThat(actualCount).isEqualTo(expectedCount)
    }

    @Test
    fun `현재 상품의 개수를 1 에서 더 줄여도 줄어들지 않는다 `() {
        // given
        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.onDecrease(productId)

        // then
        val actualCount = viewModel.productCount.getOrAwaitValue()
        val expectedCount = 1
        assertThat(actualCount).isEqualTo(expectedCount)
    }

    @Test
    fun `현재 상품의 개수를 2에서 1로 줄인다`() {
        // given
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(3, 2).toMutableList(),
            )
        shoppingProductRepository = DefaultShoppingProductRepository(productsSource, cartSource)
        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.onDecrease(productId)

        // then
        val actualCount = viewModel.productCount.getOrAwaitValue()
        val expectedCount = 1

        assertThat(actualCount).isEqualTo(expectedCount)
    }

    @Test
    fun `현재 상품을 장바구니에 담는다`() {
        // given
        productId = 1
        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)
        viewModel.loadAll()

        // when
        viewModel.addProductToCart()
        Thread.sleep(2000) // todo: thread 를 사용하면서 생기는 문제를 해결해야 함. 이렇게 sleep 을 걸지 않아도 되도록 수정해야 함

        // then
        val actualProduct = shoppingProductRepository.loadProduct(productId)
        val expectedProduct = productTestFixture(1).toDomain(quantity = 1)
        assertThat(actualProduct).isEqualTo(expectedProduct)
    }

    // todo: 이 테스트 깨짐 수정 필요
//    @Test
//    fun `현재 상품이 이미 장바구니에 있을 때 장바구니에 담으면 장바구니에 수 만큼 더 담긴다`() {
//        // given
//        cartSource =
//            FakeShoppingCartProductIdDataSource(
//                data = productsIdCountDataTestFixture(3, 2).toMutableList(),
//            )
//        shoppingProductRepository = DefaultShoppingProductRepository(productsSource, cartSource)
//        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)
//        viewModel.loadAll()
//
//        // when
//        viewModel.onIncrease(productId)
//        val actualCount = viewModel.productCount.getOrAwaitValue()
//        assertThat(actualCount).isEqualTo(2)
//
//        viewModel.addProductToCart()
//        Thread.sleep(5000) // todo: thread 를 사용하면서 생기는 문제를 해결해야 함. 이렇게 sleep 을 걸지 않아도 되도록 수정해야 함
//
//        // then
//        val actualProduct = shoppingProductRepository.loadProduct(productId)
//        val expectedProduct = productTestFixture(1).toDomain(quantity = 4)
//        assertThat(actualProduct).isEqualTo(expectedProduct)
//    }

    @Test
    fun `최근 상품이 없으면 fake 객체`() {
        // given
        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val actualLatestProduct = viewModel.latestProduct.getOrAwaitValue()
        assertThat(actualLatestProduct).isEqualTo(Product.NULL)
    }

    @Test
    fun `최근 상품이 있으면 해당 객체`() {
        // given
        historyDataSource =
            FakeProductHistorySource(
                history = ArrayDeque<Long>(listOf(1, 2, 3)),
            )
        historyRepository = DefaultProductHistoryRepository(historyDataSource, productsSource)
        viewModel = ProductDetailViewModel(productId, shoppingProductRepository, historyRepository)

        // when
        viewModel.loadAll()

        // then
        val actualLatestProduct = viewModel.latestProduct.getOrAwaitValue()
        assertThat(actualLatestProduct).isEqualTo(productTestFixture(3).toDomain(0))
    }
}
