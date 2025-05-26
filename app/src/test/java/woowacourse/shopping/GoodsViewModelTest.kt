package woowacourse.shopping

import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.data.history.repository.HistoryRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.goods.GoodsViewModel
import woowacourse.shopping.fixture.FakeCartRepository
import woowacourse.shopping.server.TestCartServiceImpl
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExtendWith(InstantTaskExecutorExtension::class)
@Suppress("ktlint:standard:function-naming")
@ExperimentalCoroutinesApi
class GoodsViewModelTest {
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    private lateinit var cartService: TestCartServiceImpl
    private lateinit var cartRepository: CartRepository
    private lateinit var historyRepository: HistoryRepository
    private lateinit var viewModel: GoodsViewModel

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
                }
            }

        viewModel = GoodsViewModel(cartRepository, historyRepository)
    }

    @AfterEach
    fun tearDown() {
        cartService.server.shutdown()
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `초기 로딩 시 히스토리와 장바구니 아이템이 로드된다`() =
        runTest(testDispatcher) {
            testScheduler.advanceUntilIdle()

            val items = viewModel.items.getOrAwaitValue()
            assert(items.isNotEmpty())
            assert(items.first() is List<*>)
            assert(items.drop(1).all { it is Cart })
        }

    @Test
    fun `장바구니 총 수량이 올바르게 계산된다`() =
        runTest(testDispatcher) {
            testScheduler.advanceUntilIdle()

            val totalQuantity = viewModel.totalQuantity.getOrAwaitValue()
            assertEquals(0, totalQuantity)
        }

    @Test
    fun `초기 로드 후 items, totalQuantity, hasNextPage 값을 검증한다`() =
        runTest(testDispatcher) {
            val itemsObserver = mock<Observer<List<Any>>>()
            val totalQuantityObserver = mock<Observer<Int>>()
            val hasNextPageObserver = mock<Observer<Boolean>>()

            viewModel.items.observeForever(itemsObserver)
            viewModel.totalQuantity.observeForever(totalQuantityObserver)
            viewModel.hasNextPage.observeForever(hasNextPageObserver)

            testScheduler.advanceUntilIdle()

            verify(itemsObserver).onChanged(viewModel.items.value ?: emptyList())
            verify(totalQuantityObserver).onChanged(viewModel.totalQuantity.value ?: 0)
            verify(hasNextPageObserver).onChanged(viewModel.hasNextPage.value != false)

            val items = viewModel.items.value!!
            assertTrue(items.isNotEmpty())
            assertTrue(viewModel.totalQuantity.value!! >= 0)
            assertTrue(viewModel.hasNextPage.value == true || viewModel.hasNextPage.value == false)

            viewModel.items.removeObserver(itemsObserver)
            viewModel.totalQuantity.removeObserver(totalQuantityObserver)
            viewModel.hasNextPage.removeObserver(hasNextPageObserver)
        }

    @Test
    fun `addPage 호출시 페이지를 증가하고 데이터를 로딩한다`() =
        runTest(testDispatcher) {
            val oldPage = 0
            val newPage = oldPage + 1

            viewModel.addPage()
            val pageField = viewModel.javaClass.getDeclaredField("page")
            pageField.isAccessible = true
            val currentPage = pageField.getInt(viewModel)
            assertEquals(newPage, currentPage)

            testScheduler.advanceUntilIdle()

            val items = viewModel.items.value!!
            assertTrue(items.isNotEmpty())
        }

    @Test
    fun `insertToCart 호출시 isSuccess LiveData를 호출한다`() =
        runTest(testDispatcher) {
            val cart = Cart(goods = Goods(1, "Test", 1000, "url"), quantity = 1)

            viewModel.insertToCart(cart)

            testScheduler.advanceUntilIdle()

            val isSuccessValue = viewModel.isSuccess.getValue()
            assertNotNull(isSuccessValue)
        }

    @Test
    fun `removeFromCart 호출시 quantity 감소 및 LiveData를 변경한다`() =
        runTest(testDispatcher) {
            val cart = Cart(goods = Goods(1, "Test", 1000, "url"), quantity = 2)

            viewModel.insertToCart(cart)
            testScheduler.advanceUntilIdle()

            viewModel.removeFromCart(cart)
            testScheduler.advanceUntilIdle()

            val updatedCart =
                viewModel.items.value
                    ?.filterIsInstance<Cart>()
                    ?.find { it.goods.id == cart.goods.id }
            assertNotNull(updatedCart)
            assertTrue(updatedCart.quantity < cart.quantity)
        }
}
