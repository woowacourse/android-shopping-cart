package woowacourse.shopping.presentation.goods.detail

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.GOODS_SUNDAE
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsDetailViewModelTest {
    private lateinit var goodsDetailViewModel: GoodsDetailViewModel
    private val goodsRepository: GoodsRepository = mockk(relaxed = true)
    private val shoppingRepository: ShoppingRepository = mockk(relaxed = true)
    private val latestGoodsRepository: LatestGoodsRepository = mockk(relaxed = true)
    private val goods = GOODS_SUNDAE

    @BeforeEach
    fun setUp() {
        every { goodsRepository.getById(1) } returns GOODS_SUNDAE
        goodsDetailViewModel =
            GoodsDetailViewModel(goodsRepository, shoppingRepository, latestGoodsRepository)
        goodsDetailViewModel.setGoods(1)
    }

    @Test
    fun `장바구니에 항목을 추가할 수 있다`() {
        // given
        val id = slot<Int>()
        val count = slot<Int>()
        every { shoppingRepository.increaseGoodsQuantity(capture(id), capture(count)) } just Runs

        // when
        goodsDetailViewModel.addToShoppingCart()

        // then
        verify { shoppingRepository.increaseGoodsQuantity(any(), any()) }

        id.captured shouldBe goods.id
        count.captured shouldBe 1
    }

    @Test
    fun `장바구니에 추가할 상품 수량의 기본값은 1이다`() {
        // then
        goodsDetailViewModel.count.getOrAwaitValue() shouldBe 1
    }

    @Test
    fun `장바구니에 추가할 상품 수량을 증가할 수 있다`() {
        // when
        goodsDetailViewModel.increaseCount()

        // then
        goodsDetailViewModel.count.getOrAwaitValue() shouldBe 2
    }

    @Test
    fun `장바구니에 추가할 상품 수량을 감소할 수 있다`() {
        // given
        goodsDetailViewModel.increaseCount()

        // when
        goodsDetailViewModel.tryDecreaseCount()

        // then
        goodsDetailViewModel.count.getOrAwaitValue() shouldBe 1
    }

    @Test
    fun `마지막으로 본 상품이 없으면 마지막으로 본 상품에 대한 정보가 없다`() {
        // then
        goodsDetailViewModel.lastGoods shouldBe null
    }

    @Test
    fun `마지막으로 본 상품이 있으면 마지막으로 본 상품에 대한 정보가 있다`() {
        // given
        goodsDetailViewModel.setLastGoods(5)

        // then
        goodsDetailViewModel.lastGoods shouldNotBe null
    }

    @Test
    fun `마지막으로 본 상품을 갱신한다`() {
        // given
        val id = slot<Int>()
        every { latestGoodsRepository.insertLatestGoods(capture(id)) } just Runs

        // when
        goodsDetailViewModel.updateLatestGoods(1)

        // then
        verify { latestGoodsRepository.insertLatestGoods(capture(id)) }

        id.captured shouldBe 1
    }
}
