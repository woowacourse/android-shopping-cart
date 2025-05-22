package woowacourse.shopping.presentation.goods.list

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.unmockkObject
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.GoodsRepositoryImpl
import woowacourse.shopping.domain.model.LatestGoods
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.createGoods
import woowacourse.shopping.fixture.createShoppingGoods
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsViewModelTest {
    private lateinit var goodsViewModel: GoodsViewModel
    private val shoppingRepository: ShoppingRepository = mockk(relaxed = true)
    private val latestGoodsRepository: LatestGoodsRepository = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        mockkObject(GoodsRepositoryImpl)
        goodsViewModel =
            GoodsViewModel(GoodsRepositoryImpl, shoppingRepository, latestGoodsRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(GoodsRepositoryImpl)
    }

    @Test
    fun `상품 목록을 정해진 수량만큼 가져온다`() {
        // then
        goodsViewModel.goods.getOrAwaitValue().size shouldBe 20
    }

    @Test
    fun `상품 목록을 추가한다`() {
        // given
        val before = goodsViewModel.goods.getOrAwaitValue().size

        // when
        goodsViewModel.loadMoreGoods()
        val actual = goodsViewModel.goods.getOrAwaitValue().size

        // then
        actual shouldBeGreaterThan before
    }

    @Test
    fun `데이터가 존재할 경우 로딩이 가능하다`() {
        // given
        every { GoodsRepositoryImpl.getPagedGoods(any(), any()) } returns listOf(createGoods())

        // when
        goodsViewModel.updateShouldShowLoadMore()
        val actual = goodsViewModel.shouldShowLoadMore.value!!

        // then
        actual shouldBe true
    }

    @Test
    fun `데이터가 존재하지 않을 경우 로딩이 불가능하다`() {
        // given
        every { GoodsRepositoryImpl.getPagedGoods(any(), any()) } returns emptyList()

        // when
        goodsViewModel.updateShouldShowLoadMore()
        val actual = goodsViewModel.shouldShowLoadMore.value!!

        // then
        actual shouldBe false
    }

    @Test
    fun `구매할 상품 수량을 증가할 수 있다`() {
        // when
        goodsViewModel.increaseGoodsCount(1)
        val actual = goodsViewModel.goods.getOrAwaitValue()[1].quantity

        // then
        actual shouldBe 1
    }

    @Test
    fun `구매할 상품 수량을 감소할 수 있다`() {
        // given
        goodsViewModel.increaseGoodsCount(1)

        // when
        goodsViewModel.decreaseGoodsCount(1)
        val actual = goodsViewModel.goods.getOrAwaitValue()[1].quantity

        // then
        actual shouldBe 0
    }

    @Test
    fun `장바구니에 담긴 상품 개수를 복원한다`() {
        // given
        every { shoppingRepository.getAllGoods() } returns setOf(createShoppingGoods(1, 2))

        // when
        goodsViewModel.restoreGoods()
        val actual = goodsViewModel.goods.getOrAwaitValue()[0].quantity

        // then
        actual shouldBe 2
    }

    @Test
    fun `최근 본 상품 목록을 가져온다`() {
        // given
        every { latestGoodsRepository.getAll() } returns listOf(LatestGoods(1), LatestGoods(2))

        // when
        goodsViewModel.setLatestGoods()

        // then
        goodsViewModel.latestGoods.getOrAwaitValue().size shouldBe 2
    }

    @Test
    fun `상품이 선택될 때 최근 본 상품 목록을 갱신한다`() {
        // given
        val id = slot<Int>()
        every { latestGoodsRepository.insertLatestGoods(capture(id)) } just Runs

        // when
        goodsViewModel.moveToDetail(1, { _, _ -> })

        // then
        verify { latestGoodsRepository.insertLatestGoods(any()) }

        id.captured shouldBe 1
    }
}
