package woowacourse.shopping.feature.goods

import FakeGoodsRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.data.carts.repository.CartRepositoryImpl
import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.domain.model.CartItem
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Suppress("ktlint:standard:function-naming")
@RunWith(AndroidJUnit4::class)
class GoodsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var cartRepository: CartRepository
    private lateinit var fake30ItemGoodsRepository: GoodsRepository
    private lateinit var viewModel: GoodsViewModel

    @Before
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    ShoppingDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        cartRepository = CartRepositoryImpl(database)

        fake30ItemGoodsRepository = FakeGoodsRepository()

        viewModel = GoodsViewModel(cartRepository, fake30ItemGoodsRepository)
        Thread.sleep(600)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 앱_진입시_첫_20개의_상품을_로딩한다() {
        // When
        val loadedGoods = getCurrentGoodsWithCartQuantity()

        // Then
        assertThat(loadedGoods).hasSize(20)
        assertThat(loadedGoods).allMatch { it.quantity == 0 }
        assertThat(loadedGoods.first().goods.name).contains("Re: 제로")
        assertThat(loadedGoods[1].goods.name).contains("봇치 더 록")
    }

    @Test
    fun addPage_호출시_더_많은_상품을_보여준다() {
        // Given
        val beforeGoods = getCurrentGoodsWithCartQuantity()
        val beforeCount = beforeGoods.size

        // When
        viewModel.addPage()
        waitForUpdate(400)

        // Then
        val afterGoods = getCurrentGoodsWithCartQuantity()
        val afterCount = afterGoods.size

        assertThat(afterCount).isGreaterThan(beforeCount)
        assertThat(afterCount).isEqualTo(30)
    }

    @Test
    fun 모든_상품을_로딩하면_isFullLoaded가_true가_된다() {
        // When
        viewModel.addPage()
        waitForUpdate()

        // Then
        val isFullLoaded = viewModel.isFullLoaded.value ?: false
        assertThat(isFullLoaded).isTrue()

        val allGoods = getCurrentGoodsWithCartQuantity()
        assertThat(allGoods).hasSize(30)
    }

    @Test
    fun 초기_장바구니_아이템_크기는_0이다() {
        // When
        val totalCartItemSize = viewModel.totalCartItemSize.value

        // Then
        assertThat(totalCartItemSize).isEqualTo("0")
    }

    @Test
    fun 장바구니_아이템_추가시_실제_Room_DB에_저장되고_수량_업데이트() {
        // Given
        val initGoodsListWithCartQuantity = getCurrentGoodsWithCartQuantity()
        val firstItem = initGoodsListWithCartQuantity.first()

        // When
        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 1))
        waitForCartUpdate()

        // Then - ViewModel 상태 확인
        val totalCartItemSize = viewModel.totalCartItemSize.value
        assertThat(totalCartItemSize).isEqualTo("1")

        val updatedGoodsListWithCartQuantity = getCurrentGoodsWithCartQuantity()
        val updatedItem = updatedGoodsListWithCartQuantity.find { it.goods.name == firstItem.goods.name }
        assertThat(updatedItem?.quantity).isEqualTo(1)

        // Then - 실제 DB 확인
        val dbCartItems = getCartItemsFromDB()
        assertThat(dbCartItems).hasSize(1)
        assertThat(dbCartItems.first().quantity).isEqualTo(1)
        assertThat(dbCartItems.first().goods.name).isEqualTo(firstItem.goods.name)
    }

    @Test
    fun 장바구니_아이템_제거시_실제_Room_DB에서_수량_감소() {
        assertThat(viewModel.totalCartItemSize.value).isEqualTo("0")
        // Given
        val goods = getCurrentGoodsWithCartQuantity()
        val firstItem = goods.first()

        // 1번 아이템 수량 3개 추가
        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 3))
        waitForCartUpdate()

        // When
        // 1번 아이템 수량 1개 감소
        viewModel.removeCartItemOrDecreaseQuantity(firstItem.copy(quantity = 1))
        waitForCartUpdate()

        // Then
        val totalCartItemSize = viewModel.totalCartItemSize.value
        assertThat(totalCartItemSize).isEqualTo("2")

        val updatedCartItems = getCurrentGoodsWithCartQuantity()
        val updatedItem = updatedCartItems.find { it.goods.name == firstItem.goods.name }
        assertThat(updatedItem?.quantity).isEqualTo(2)

        val dbCartItems = getCartItemsFromDB()
        assertThat(dbCartItems).hasSize(1)
        assertThat(dbCartItems.first().quantity).isEqualTo(2)
    }

    @Test
    fun 장바구니_수량이_99개_초과시_99plus로_표시된다() {
        // Given
        val goods = getCurrentGoodsWithCartQuantity()
        val firstItem = goods.first()

        // When
        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 100))
        waitForCartUpdate()

        // Then
        val totalCartItemSize = viewModel.totalCartItemSize.value
        assertThat(totalCartItemSize).isEqualTo("99+")

        val dbCartItems = getCartItemsFromDB()
        assertThat(dbCartItems.first().quantity).isEqualTo(100)
    }

    @Test
    fun onCartClicked_호출시_navigateToCart_이벤트_발생() {
        // Given
        val beforeEvent = viewModel.navigateToCart.getValue()
        assertThat(beforeEvent).isNull()

        // When
        viewModel.onCartClicked()

        // Then
        val afterEvent = viewModel.navigateToCart.getValue()
        assertThat(afterEvent).isNotNull()
    }

    @Test
    fun updateRecentlyViewedGoods_호출시_Repository_연동_확인() {
        // Given
        val goods = getCurrentGoodsWithCartQuantity()
        val goods1 = goods[0].goods
        val goods2 = goods[1].goods
        val goods3 = goods[2].goods

        // 최근 본 상품으로 등록
        fake30ItemGoodsRepository.loggingRecentGoods(goods1) { }
        Thread.sleep(100)
        fake30ItemGoodsRepository.loggingRecentGoods(goods2) { }
        Thread.sleep(100)
        fake30ItemGoodsRepository.loggingRecentGoods(goods3) { }
        Thread.sleep(100)

        // When
        viewModel.updateRecentlyViewedGoods()
        waitForUpdate()

        // Then
        val recentlyViewedGoods = viewModel.recentlyViewedGoods.value ?: emptyList()
        assertThat(recentlyViewedGoods).hasSize(3)
        assertThat(recentlyViewedGoods.first().name).isEqualTo(goods3.name)
    }

    @Test
    fun 여러_아이템_장바구니_추가시_실제_DB와_ViewModel_동기화() {
        // Given
        val cartItems = getCurrentGoodsWithCartQuantity()
        val item1 = cartItems[0]
        val item2 = cartItems[1]
        val item3 = cartItems[2]

        // When
        viewModel.addCartItemOrIncreaseQuantity(item1.copy(quantity = 2))
        waitForCartUpdate(200)
        viewModel.addCartItemOrIncreaseQuantity(item2.copy(quantity = 3))
        waitForCartUpdate(200)
        viewModel.addCartItemOrIncreaseQuantity(item3.copy(quantity = 1))
        waitForCartUpdate(200)

        // Then - ViewModel 확인
        val totalCartItemSize = viewModel.totalCartItemSize.value
        assertThat(totalCartItemSize).isEqualTo("6")

        // Then - 실제 DB 확인
        val dbCartItems = getCartItemsFromDB()
        assertThat(dbCartItems).hasSize(3)
        assertThat(dbCartItems.sumOf { it.quantity }).isEqualTo(6)
    }

    @Test
    fun 경계값_테스트_99개와_100개_실제_DB_연동() {
        // Given
        val goods = getCurrentGoodsWithCartQuantity()
        val firstItem = goods.first()

        // When - 99개 추가
        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 99))
        waitForCartUpdate()

        // Then - "99" 표시
        var totalCartItemSize = viewModel.totalCartItemSize.value
        assertThat(totalCartItemSize).isEqualTo("99")

        var dbCartItems = getCartItemsFromDB()
        assertThat(dbCartItems.first().quantity).isEqualTo(99)

        // When - 1개 더 추가 (총 100개)
        viewModel.addCartItemOrIncreaseQuantity(firstItem.copy(quantity = 1))
        waitForCartUpdate()

        // Then - "99+" 표시, DB에는 100개
        totalCartItemSize = viewModel.totalCartItemSize.value
        assertThat(totalCartItemSize).isEqualTo("99+")

        dbCartItems = getCartItemsFromDB()
        assertThat(dbCartItems.first().quantity).isEqualTo(100)
    }

    @Test
    fun FakeRepository_데이터_일관성_확인() {
        // Given - FakeGoodsRepository의 데이터 구조 확인
        val goods = getCurrentGoodsWithCartQuantity()

        // Then - fixtureGoodsItem 기반으로 20개 로딩 확인
        assertThat(goods).hasSize(20)

        // 패턴 확인: 3개씩 반복되는 구조
        assertThat(goods[0].goods.name).contains("아크릴피규어/렘")
        assertThat(goods[1].goods.name).contains("봇치 더 록")
        assertThat(goods[2].goods.name).contains("아크릴피규어/람")

        // 모든 아이템이 quantity 0으로 시작
        assertThat(goods).allMatch { it.quantity == 0 }
    }

    @Test
    fun 최근_본_상품_비어있는_초기_상태_확인() {
        // When
        val recentlyViewedGoods = viewModel.recentlyViewedGoods.value ?: emptyList()

        // Then
        assertThat(recentlyViewedGoods).isEmpty()
    }

    private fun getCurrentGoodsWithCartQuantity(): List<CartItem> = viewModel.goodsWithCartQuantity.value ?: emptyList()

    private fun getCartItemsFromDB(): List<CartItem> {
        val latch = CountDownLatch(1)
        var result: List<CartItem> = emptyList()

        cartRepository.fetchAllCartItems { items ->
            result = items
            latch.countDown()
        }

        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()
        return result
    }

    private fun waitForUpdate(delayMs: Long = 300) {
        Thread.sleep(delayMs)
    }

    private fun waitForCartUpdate(delayMs: Long = 500) {
        Thread.sleep(delayMs)
    }
}
