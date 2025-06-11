package woowacourse.shopping.presentation.goods.detail

import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.goods.repository.GoodsRepositoryImpl
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.RecentGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.fixture.SUNDAE
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class GoodsDetailViewModelTest {
    private lateinit var goodsDetailViewModel: GoodsDetailViewModel
    private lateinit var goodsRepository: GoodsRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var recentGoodsRepository: RecentGoodsRepository

    @BeforeEach
    fun setUp() {
        goodsRepository = GoodsRepositoryImpl()
        shoppingCartRepository = mockk(relaxed = true)
        recentGoodsRepository = mockk(relaxed = true)
        goodsDetailViewModel = GoodsDetailViewModel(goodsRepository, shoppingCartRepository, recentGoodsRepository)
    }

    @Test
    fun `상품 정보가 저장된다`() {
        // when
        goodsDetailViewModel.setGoods(SUNDAE.goods.id)

        // then
        goodsDetailViewModel.item.getOrAwaitValue() shouldBe SUNDAE
    }

    @Test
    fun `장바구니에 상품을 저장한다`() {
        // when
        goodsDetailViewModel.addToShoppingCart()

        // then
        verify { shoppingCartRepository.addOrIncreaseQuantity(any()) {} }
    }
}
