package woowacourse.shopping.presentation.shoppingcart

import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.SUNDAE
import woowacourse.shopping.fixture.createGoods
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.model.toUiModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private lateinit var repository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        shoppingCartViewModel = ShoppingCartViewModel(repository)
    }

    @Test
    fun `장바구니의 물품을 삭제한다`() {
        // given
        repository.addGoods(SUNDAE)

        // when
        shoppingCartViewModel.deleteGoods(SUNDAE.toUiModel())

        // then
        shoppingCartViewModel.goodsUiModels.getOrAwaitValue().shouldNotContain(SUNDAE.toUiModel())
    }

    @Test
    fun `페이지의 초기값은 1이다`() {
        // then
        shoppingCartViewModel.page.getOrAwaitValue() shouldBe 1
    }

    @Test
    fun `페이지를 증가시킬 수 있다`() {
        // when
        shoppingCartViewModel.increasePage()

        // then
        shoppingCartViewModel.page.getOrAwaitValue() shouldBe 2
    }

    @Test
    fun `페이지를 감소시킬 수 있다`() {
        // given
        shoppingCartViewModel.increasePage()

        // when
        shoppingCartViewModel.decreasePage()

        // then
        shoppingCartViewModel.page.getOrAwaitValue() shouldBe 1
    }

    @Test
    fun `다음 페이지의 데이터가 존재할 경우 true를 반환한다`() {
        // given
        every { repository.getGoods(any(), any()) } returns listOf(createGoods())

        // when
        shoppingCartViewModel.increasePage()

        // then
        shoppingCartViewModel.hasNextPage.getOrAwaitValue() shouldBe true
    }

    @Test
    fun `다음 페이지의 데이터가 존재하지 않을 경우 false를 반환한다`() {
        // given
        every { repository.getGoods(any(), any()) } returns emptyList()

        // then
        shoppingCartViewModel.hasNextPage.getOrAwaitValue() shouldBe false
    }

    @Test
    fun `현재 페이지가 1페이지일 경우 이전 페이지가 존재하지 않는다`() {
        // then
        shoppingCartViewModel.hasPreviousPage.getOrAwaitValue() shouldBe false
    }

    @Test
    fun `현재 페이지가 1페이지가 아닐 경우 이전 페이지가 존재한다`() {
        // when
        shoppingCartViewModel.increasePage()

        // then
        shoppingCartViewModel.hasPreviousPage.getOrAwaitValue() shouldBe true
    }
}
