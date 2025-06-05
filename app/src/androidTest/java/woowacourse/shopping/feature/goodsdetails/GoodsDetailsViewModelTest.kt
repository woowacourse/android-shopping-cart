package woowacourse.shopping.feature.goods

import FakeGoodsRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import fixtureGoodsItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.carts.repository.CartRepositoryImpl
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsViewModel
import woowacourse.shopping.util.toDomain
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Suppress("ktlint:standard:function-naming")
@RunWith(AndroidJUnit4::class)
class GoodsDetailsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var cartRepository: CartRepositoryImpl
    private lateinit var viewModel: GoodsDetailsViewModel
    private lateinit var goodsUiModel: GoodsUiModel
    private lateinit var fixtureGoodsRepository: FakeGoodsRepository

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
        fixtureGoodsRepository = FakeGoodsRepository()

        goodsUiModel =
            GoodsUiModel(
                id = 1,
                name = "테스트 상품",
                price = 15000,
                thumbnailUrl = "https://test.com/image.jpg",
            )

        viewModel =
            GoodsDetailsViewModel(
                goodsUiModel = goodsUiModel,
                cartRepository = cartRepository,
                goodsRepository = fixtureGoodsRepository,
            )
        Thread.sleep(100)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 초기_CartItem_상태_확인() {
        // When
        val cartItem = viewModel.cartItem.value

        // Then
        assertThat(cartItem).isNotNull()
        assertThat(cartItem!!.goods.name).isEqualTo("테스트 상품")
        assertThat(cartItem.goods.price).isEqualTo(15000)
        assertThat(cartItem.quantity).isEqualTo(1)
    }

    @Test
    fun increaseSelectorQuantity_호출시_수량이_증가한다() {
        // Given
        val initialQuantity = viewModel.cartItem.value?.quantity ?: 0

        // When
        viewModel.increaseSelectorQuantity()

        // Then
        val updatedQuantity = viewModel.cartItem.value?.quantity ?: 0
        assertThat(updatedQuantity).isEqualTo(initialQuantity + 1)
    }

    @Test
    fun decreaseSelectorQuantity_호출시_수량이_감소한다() {
        // Given
        viewModel.increaseSelectorQuantity() // 수량 2
        val initialQuantity = viewModel.cartItem.value?.quantity ?: 0

        // When
        viewModel.decreaseSelectorQuantity()

        // Then
        val updatedQuantity = viewModel.cartItem.value?.quantity ?: 0
        assertThat(updatedQuantity).isEqualTo(initialQuantity - 1)
    }

    @Test
    fun decreaseSelectorQuantity_수량이_1일때_감소하지_않는다() {
        // Given
        val initialQuantity = viewModel.cartItem.value?.quantity ?: 0
        assertThat(initialQuantity).isEqualTo(1)

        // When
        viewModel.decreaseSelectorQuantity()

        // Then
        val updatedQuantity = viewModel.cartItem.value?.quantity ?: 0
        assertThat(updatedQuantity).isEqualTo(1)
    }

    @Test
    fun addToCart_호출시_장바구니에_추가되고_알림_이벤트_발생() {
        // Given
        repeat(2) { viewModel.increaseSelectorQuantity() } // 수량 3
        val cartItem = viewModel.cartItem.value!!
        assertThat(cartItem.quantity).isEqualTo(3)

        // Given - 이벤트 발생 전 상태 확인
        val beforeAlert = viewModel.alertEvent.getValue()
        assertThat(beforeAlert).isNull()

        // When
        viewModel.addToCart()
        waitForCartUpdate()

        // Then - SingleLiveData getValue()로 이벤트 확인
        val afterAlert = viewModel.alertEvent.getValue()
        assertThat(afterAlert).isNotNull()
        assertThat(afterAlert!!.resourceId).isEqualTo(R.string.goods_detail_cart_insert_complete_toast_message)
        assertThat(afterAlert.quantity).isEqualTo(3)

        // Then - DB 확인 및 수량 초기화 확인
        val dbCartItems = getCartItemsFromDB()
        assertThat(dbCartItems).hasSize(1)
        assertThat(dbCartItems.first().quantity).isEqualTo(3)

        val resetCartItem = viewModel.cartItem.value!!
        assertThat(resetCartItem.quantity).isEqualTo(1)
    }

    @Test
    fun initMostRecentlyViewedGoods_최근_본_상품이_현재_상품과_다르면_설정됨() {
        // Given
        val otherGoods = fixtureGoodsItem[1] // id=2인 fixture 상품
        fixtureGoodsRepository.loggingRecentGoods(otherGoods) { }
        Thread.sleep(100)

        // When & Then - Observer로 값 변경 감지
        val latch = CountDownLatch(1)
        var observedGoods: Goods? = null

        val observer =
            Observer<Goods?> { goods ->
                if (goods != null) {
                    observedGoods = goods
                    latch.countDown()
                }
            }

        viewModel.mostRecentlyViewedGoods.observeForever(observer)

        try {
            viewModel.initMostRecentlyViewedGoods()

            // postValue 처리 대기
            assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()
            assertThat(observedGoods).isNotNull()
            assertThat(observedGoods!!.id).isEqualTo(2L)
        } finally {
            viewModel.mostRecentlyViewedGoods.removeObserver(observer)
        }
    }

    @Test
    fun initMostRecentlyViewedGoods_최근_본_상품이_현재_상품과_같으면_null() {
        // Given
        val currentGoods = goodsUiModel.toDomain()
        fixtureGoodsRepository.loggingRecentGoods(currentGoods) { }
        Thread.sleep(100)

        // When
        viewModel.initMostRecentlyViewedGoods()

        // Then - 재시도 로직으로 안정적 확인
        waitUntilValueEquals(
            getter = { viewModel.mostRecentlyViewedGoods.value },
            expectedValue = null,
            timeoutMs = 2000,
        )

        val mostRecentGoods = viewModel.mostRecentlyViewedGoods.value
        assertThat(mostRecentGoods).isNull()
    }

    @Test
    fun onClickMostRecentlyGoodsSection_클릭_이벤트_발생() {
        // Given
        val recentGoods = fixtureGoodsItem[2] // id=3인 fixture 상품
        fixtureGoodsRepository.loggingRecentGoods(recentGoods) { }
        Thread.sleep(100)

        // 최근 본 상품 설정 대기
        val setupLatch = CountDownLatch(1)
        val setupObserver =
            Observer<Goods?> { goods ->
                if (goods != null) setupLatch.countDown()
            }

        viewModel.mostRecentlyViewedGoods.observeForever(setupObserver)
        viewModel.initMostRecentlyViewedGoods()

        try {
            assertThat(setupLatch.await(3, TimeUnit.SECONDS)).isTrue()

            // When
            viewModel.onClickMostRecentlyGoodsSection()

            // Then
            val clickEvent = viewModel.clickMostRecentlyGoodsEvent.getValue()
            assertThat(clickEvent).isNotNull()
            assertThat(clickEvent!!.id).isEqualTo(3L)
        } finally {
            viewModel.mostRecentlyViewedGoods.removeObserver(setupObserver)
        }
    }

    @Test
    fun loggingRecentViewedGoods_현재_상품_기록() {
        // Given
        val currentGoods = goodsUiModel.toDomain()

        // When
        viewModel.loggingRecentViewedGoods(currentGoods)
        Thread.sleep(200)

        // Then
        val latch = CountDownLatch(1)
        var recentIds: List<String> = emptyList()

        fixtureGoodsRepository.fetchRecentGoodsIds { ids ->
            recentIds = ids
            latch.countDown()
        }

        assertThat(latch.await(2, TimeUnit.SECONDS)).isTrue()
        assertThat(recentIds).contains("1")
    }

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

    private fun waitForCartUpdate(delayMs: Long = 500) {
        Thread.sleep(delayMs)
    }

    // 재시도 로직 헬퍼 메서드
    private fun <T> waitUntilValueEquals(
        getter: () -> T?,
        expectedValue: T?,
        timeoutMs: Long = 3000,
        intervalMs: Long = 50,
    ) {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            val currentValue = getter()
            if (currentValue == expectedValue) {
                return
            }
            Thread.sleep(intervalMs)
        }
        // 타임아웃 시에도 마지막 값 확인
        val finalValue = getter()
        assertThat(finalValue).isEqualTo(expectedValue)
    }
}
