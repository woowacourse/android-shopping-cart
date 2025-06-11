package woowacourse.shopping.presentation.goods.list

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.RecentGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.FakeGoodsRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsViewModelTest {
    private lateinit var goodsViewModel: GoodsViewModel
    private lateinit var fakeGoodsRepository: GoodsRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var recentGoodsRepository: RecentGoodsRepository

    @BeforeEach
    fun setUp() {
        fakeGoodsRepository = FakeGoodsRepository()
        shoppingCartRepository = mockk(relaxed = true)
        recentGoodsRepository = mockk(relaxed = true)
        goodsViewModel = GoodsViewModel(fakeGoodsRepository, shoppingCartRepository, recentGoodsRepository)
        goodsViewModel.initGoods()
    }

    @Test
    fun `상품 목록을 20개씩 가져온다`() {
        // then
        goodsViewModel.items.getOrAwaitValue().size shouldBe 20
    }

    @Test
    fun `상품 목록을 추가한다`() {
        // given
        val before = goodsViewModel.items.getOrAwaitValue().size

        // when
        goodsViewModel.addGoods()
        val actual = goodsViewModel.items.getOrAwaitValue().size

        // then
        actual shouldBeGreaterThan before
    }

    @Test
    fun `데이터가 존재할 경우 데이터를 추가할 수 있다`() {
        // when
        goodsViewModel.determineLoadMoreVisibility(false)

        // then
        goodsViewModel.showLoadMore.getOrAwaitValue() shouldBe true
    }

    @Test
    fun `데이터가 존재하지 않을 경우 데이터를 추가할 수 없다`() {
        // given
        val emptyRepository = FakeGoodsRepository(emptyList())
        val emptyViewModel = GoodsViewModel(emptyRepository, shoppingCartRepository, recentGoodsRepository)

        // when
        emptyViewModel.determineLoadMoreVisibility(false)

        // then
        emptyViewModel.showLoadMore.getOrAwaitValue() shouldBe false
    }
}
