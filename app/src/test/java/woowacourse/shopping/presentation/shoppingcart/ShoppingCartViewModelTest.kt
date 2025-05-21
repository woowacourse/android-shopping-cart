package woowacourse.shopping.presentation.shoppingcart

import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.GoodsRepositoryImpl
import woowacourse.shopping.domain.model.ShoppingGoods
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.ICE_CREAM
import woowacourse.shopping.fixture.SUNDAE
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.model.toUiModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private val shoppingRepository: ShoppingRepository = mockk(relaxed = true)
    private val goods =
        listOf(
            SUNDAE,
            ICE_CREAM,
        )

    @BeforeEach
    fun setUp() {
        shoppingCartViewModel = ShoppingCartViewModel(GoodsRepositoryImpl, shoppingRepository)
    }

    @Test
    fun `장바구니의 물품을 삭제한다`() {
        // given
        goods.forEach { shoppingRepository.increaseItemQuantity(it.id) }

        // when
        shoppingCartViewModel.deleteGoods(SUNDAE.toUiModel())

        // then
        shoppingCartViewModel.goods.getOrAwaitValue().shouldNotContain(SUNDAE.toUiModel())
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
    fun `다음 상품 목록이 존재하면 다음 페이지는 존재한다`() {
        // given
        every { shoppingRepository.getPagedGoods(any(), any()) } returns listOf(ShoppingGoods(1, 1))
        shoppingCartViewModel = ShoppingCartViewModel(GoodsRepositoryImpl, shoppingRepository)

        // then
        shoppingCartViewModel.hasNextPage.getOrAwaitValue() shouldBe true
    }

    @Test
    fun `다음 상품 목록이 존재하지 않으면 다음 페이지는 존재하지 않는다`() {
        // given
        every { shoppingRepository.getPagedGoods(any(), any()) } returns listOf()
        shoppingCartViewModel = ShoppingCartViewModel(GoodsRepositoryImpl, shoppingRepository)

        // then
        shoppingCartViewModel.hasNextPage.getOrAwaitValue() shouldBe false
    }

    @Test
    fun `현재 페이지가 0페이지일 경우 이전 페이지가 존재하지 않는다`() {
        // then
        shoppingCartViewModel.hasPreviousPage.getOrAwaitValue() shouldBe false
    }

    @Test
    fun `현재 페이지가 0페이지가 아닐 경우 이전 페이지가 존재한다`() {
        // when
        shoppingCartViewModel.increasePage()

        // then
        shoppingCartViewModel.hasPreviousPage.getOrAwaitValue() shouldBe true
    }
}
