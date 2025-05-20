package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.cart.CartViewModel
import woowacourse.shopping.fixture.FakeCartRepository
import woowacourse.shopping.util.InstantTaskExecutorExtension
import woowacourse.shopping.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@Suppress("ktlint:standard:function-naming")
class CartViewModelTest {
    private lateinit var fakeRepository: FakeCartRepository
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setup() {
        fakeRepository = FakeCartRepository()
        viewModel = CartViewModel(fakeRepository)
    }

    @Test
    fun plusPage_호출시_페이지_번호가_증가한다() {
        assertEquals(1, viewModel.page.getOrAwaitValue())
        viewModel.plusPage()
        assertEquals(2, viewModel.page.getOrAwaitValue())
    }

    @Test
    fun minusPage_호출시_페이지_번호가_감소한다() {
        viewModel.plusPage()
        assertEquals(2, viewModel.page.getOrAwaitValue())
        viewModel.minusPage()
        assertEquals(1, viewModel.page.getOrAwaitValue())
    }

    @Test
    fun 첫_페이지에서_updatePageButton_호출시_왼쪽버튼_비활성_오른쪽버튼_활성화된다() {
        repeat(10) {
            fakeRepository.insert(Goods(id = it.toLong(), name = "item $it", price = 100, thumbnailUrl = "url"))
        }
        viewModel.updatePageButtonStates()

        assertFalse(viewModel.isLeftPageEnable.getOrAwaitValue())
        assertTrue(viewModel.isRightPageEnable.getOrAwaitValue())
    }

    @Test
    fun 마지막_페이지의_마지막_아이템_삭제시_페이지가_이전_페이지로_이동한다() {
        repeat(6) {
            fakeRepository.insert(Goods(id = it.toLong(), name = "item $it", price = 100, thumbnailUrl = "url"))
        }
        viewModel.plusPage()
        assertEquals(2, viewModel.page.getOrAwaitValue())

        viewModel.delete(Goods(id = 999L, name = "dummy", price = 0, thumbnailUrl = "dummy"))
        assertEquals(1, viewModel.page.getOrAwaitValue())
    }
}
