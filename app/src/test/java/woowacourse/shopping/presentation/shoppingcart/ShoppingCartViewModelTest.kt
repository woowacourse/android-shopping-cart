package woowacourse.shopping.presentation.shoppingcart

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.FakeShoppingCartRepository
import woowacourse.shopping.fixture.SUNDAE
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private lateinit var repository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        repository = FakeShoppingCartRepository()
        shoppingCartViewModel = ShoppingCartViewModel(repository)
    }

    @Test
    fun `장바구니에 담긴 물품의 수량을 증가시킨다`() {
        // when
        shoppingCartViewModel.increaseQuantity(SUNDAE)
        val expected = shoppingCartViewModel.items.getOrAwaitValue().first()

        // then
        expected.quantity shouldBe 1
    }

    @Test
    fun `장바구니에 담긴 물품의 수량을 감소시킨다`() {
        // given
        shoppingCartViewModel.increaseQuantity(SUNDAE)
        shoppingCartViewModel.increaseQuantity(SUNDAE)

        // when
        shoppingCartViewModel.decreaseQuantity(SUNDAE)
        val expected = shoppingCartViewModel.items.getOrAwaitValue().first()

        // then
        expected.quantity shouldBe 1
    }

    @Test
    fun `장바구니에 담긴 물품의 수량이 0이 되면 물품을 삭제시킨다`() {
        // when
        shoppingCartViewModel.decreaseQuantity(SUNDAE)

        // then
        shoppingCartViewModel.items.getOrAwaitValue().shouldNotContain(SUNDAE)
    }

    @Test
    fun `장바구니의 물품을 삭제한다`() {
        // given
        shoppingCartViewModel.increaseQuantity(SUNDAE)

        // when
        shoppingCartViewModel.deleteItem(SUNDAE)

        // then
        shoppingCartViewModel.items.getOrAwaitValue().shouldNotContain(SUNDAE)
    }

    @Test
    fun `페이지의 초기값은 0이다`() {
        // then
        shoppingCartViewModel.page.getOrAwaitValue() shouldBe 0
    }

    @Test
    fun `페이지를 증가시킬 수 있다`() {
        // when
        shoppingCartViewModel.increasePage()

        // then
        shoppingCartViewModel.page.getOrAwaitValue() shouldBe 1
    }

    @Test
    fun `페이지를 감소시킬 수 있다`() {
        // given
        shoppingCartViewModel.increasePage()

        // when
        shoppingCartViewModel.decreasePage()

        // then
        shoppingCartViewModel.page.getOrAwaitValue() shouldBe 0
    }

    @Test
    fun `다음 페이지의 데이터가 존재할 경우 true를 반환한다`() {
        // when
        shoppingCartViewModel.increasePage()

        // then
        shoppingCartViewModel.hasNextPage.getOrAwaitValue() shouldBe true
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
