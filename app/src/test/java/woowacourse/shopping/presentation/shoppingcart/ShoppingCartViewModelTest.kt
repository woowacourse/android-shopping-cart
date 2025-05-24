package woowacourse.shopping.presentation.shoppingcart

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.model.ShoppingGoods
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.GOODS_SUNDAE
import woowacourse.shopping.fixture.SHOPPING_GOODS_SUNDAE
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private val goodsRepository: GoodsRepository = mockk(relaxed = true)
    private val shoppingRepository: ShoppingRepository = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        every { shoppingRepository.getPagedGoods(any(), any()) } returns
            listOf(
                SHOPPING_GOODS_SUNDAE,
            )
        every { goodsRepository.getById(1) } returns GOODS_SUNDAE
        shoppingCartViewModel = ShoppingCartViewModel(goodsRepository, shoppingRepository)
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
        every { shoppingRepository.getPagedGoods(any(), any()) } returns listOf(ShoppingGoods(1, 2))
        shoppingCartViewModel = ShoppingCartViewModel(goodsRepository, shoppingRepository)

        // then
        shoppingCartViewModel.hasNextPage.getOrAwaitValue() shouldBe true
    }

    @Test
    fun `다음 상품 목록이 존재하지 않으면 다음 페이지는 존재하지 않는다`() {
        // given
        every { shoppingRepository.getPagedGoods(any(), any()) } returns listOf()
        shoppingCartViewModel = ShoppingCartViewModel(goodsRepository, shoppingRepository)

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

    @Test
    fun `구매할 물품 수량을 증가할 수 있다`() {
        // when
        shoppingCartViewModel.increaseGoodsCount(0)

        // then
        shoppingCartViewModel.goods.getOrAwaitValue()[0].quantity shouldBe 3

        verify {
            shoppingRepository.increaseItemQuantity(1)
        }
    }

    @Test
    fun `구매할 물품 수량을 감소할 수 있다`() {
        // when
        shoppingCartViewModel.decreaseGoodsCount(0)

        // then
        shoppingCartViewModel.goods.getOrAwaitValue()[0].quantity shouldBe 1

        verify {
            shoppingRepository.decreaseItemQuantity(1)
        }
    }
}
