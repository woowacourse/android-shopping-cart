package woowacourse.shopping

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.data.history.repository.HistoryRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsViewModel
import woowacourse.shopping.fixture.FakeCartRepository
import woowacourse.shopping.server.TestCartServiceImpl
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@Suppress("ktlint:standard:function-naming")
@ExperimentalCoroutinesApi
class GoodsDetailsViewModelTest {
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    private lateinit var cartService: TestCartServiceImpl
    private lateinit var cartRepository: CartRepository
    private lateinit var historyRepository: HistoryRepository
    private lateinit var viewModel: GoodsDetailsViewModel

    @BeforeEach
    fun setup() {
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)

        cartService = TestCartServiceImpl()

        cartRepository = FakeCartRepository()

        historyRepository =
            object : HistoryRepository {
                override fun getAll(callback: (List<Cart>) -> Unit) {
                    callback(
                        listOf(
                            Cart(goods = Goods(1, "Test", 1000, "url"), quantity = 1),
                        ),
                    )
                }

                override fun insert(history: Cart) {
                    // 테스트용 빈 구현
                }

                override fun findLatest(callback: (Cart?) -> Unit) {
                    callback(Cart(goods = Goods(1, "Test", 1000, "url"), quantity = 1))
                }
            }

        viewModel = GoodsDetailsViewModel(cartRepository, historyRepository)
    }

    @AfterEach
    fun tearDown() {
        cartService.server.shutdown()
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun 수량_증가시_장바구니_수량이_증가한다() =
        runTest(testDispatcher) {
            val cart = Cart(goods = Goods(1, "Test", 1000, "url"), quantity = 1)
            viewModel.setInitialCart(cart)
            advanceUntilIdle()

            viewModel.increaseQuantity()
            advanceUntilIdle()

            assertEquals(2, viewModel.cart.getOrAwaitValue().quantity)
        }

    @Test
    fun 수량_감소시_장바구니_수량이_감소한다() =
        runTest(testDispatcher) {
            val cart = Cart(goods = Goods(1, "Test", 1000, "url"), quantity = 2)
            viewModel.setInitialCart(cart)
            advanceUntilIdle()

            viewModel.decreaseQuantity()
            advanceUntilIdle()

            assertEquals(1, viewModel.cart.getOrAwaitValue().quantity)
        }

    @Test
    fun 최근_히스토리와_일치하는_카트를_찾는다() =
        runTest(testDispatcher) {
            val cart = Cart(goods = Goods(1, "Test", 1000, "url"), quantity = 2)
            cartRepository.insert(cart)
            viewModel.setInitialCart(cart)
            advanceUntilIdle()

            var emitted: Cart? = null
            viewModel.navigateToLastViewedCart.observe(
                object : LifecycleOwner {
                    override val lifecycle: Lifecycle
                        get() = LifecycleRegistry(this).apply { currentState = Lifecycle.State.RESUMED }
                },
            ) {
                emitted = it
            }
            viewModel.emitLastViewedCart()
            advanceUntilIdle()
            assertEquals(cart, emitted)
        }
}
