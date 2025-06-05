package woowacourse.shopping.feature.cart

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
import woowacourse.shopping.data.carts.repository.CartRepositoryImpl
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods

@Suppress("ktlint:standard:function-naming")
@RunWith(AndroidJUnit4::class)
class CartViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var viewModel: CartViewModel

    @Before
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    ShoppingDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        val cartRepository = CartRepositoryImpl(database)
        setupInitialData(cartRepository)

        viewModel = CartViewModel(cartRepository)
        Thread.sleep(300)
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun setupInitialData(repository: CartRepositoryImpl) {
        val goods1 = Goods("테스트상품1", 10000, "url1", 1)
        val goods2 = Goods("테스트상품2", 20000, "url2", 2)
        val goods3 = Goods("테스트상품3", 30000, "url3", 3)

        repository.addOrIncreaseQuantity(goods1, 1) { }
        repository.addOrIncreaseQuantity(goods2, 2) { }
        repository.addOrIncreaseQuantity(goods3, 3) { }

        Thread.sleep(300)
    }

    @Test
    fun 카트_아이템_추가_테스트() {
        // Given
        val newGoods = Goods("신규상품", 15000, "new-url", 999)
        val initialCart = getCurrentCart()
        val initialSize = initialCart.size

        // When
        viewModel.addCartItemOrIncreaseQuantity(CartItem(newGoods, 1))
        waitForCartUpdate()

        // Then
        val updatedCart = getCurrentCart()
        assertThat(updatedCart.size).isGreaterThan(initialSize)

        val addedItem = updatedCart.find { it.goods.id.toInt() == 999 }
        assertThat(addedItem).isNotNull()
        assertThat(addedItem!!.quantity).isEqualTo(1)
    }

    @Test
    fun 카트_아이템_수량_증가_테스트() {
        // Given
        val cartItems = getCurrentCart()
        assertThat(cartItems).isNotEmpty()
        val targetItem = cartItems.first()
        val originalQuantity = targetItem.quantity

        // When
        viewModel.addCartItemOrIncreaseQuantity(CartItem(targetItem.goods, 2))
        waitForCartUpdate()

        // Then
        val updatedCart = getCurrentCart()
        val updatedItem = updatedCart.find { it.goods.id == targetItem.goods.id }
        assertThat(updatedItem!!.quantity).isEqualTo(originalQuantity + 2)
    }

    @Test
    fun 카트_아이템_완전_삭제_테스트() {
        // Given
        val cartItems = getCurrentCart()
        assertThat(cartItems).isNotEmpty()
        val targetItem = cartItems.first()
        val initialSize = cartItems.size

        // When
        viewModel.delete(targetItem)
        waitForCartUpdate()

        // Then
        val updatedCart = getCurrentCart()
        assertThat(updatedCart.size).isEqualTo(initialSize - 1)

        val deletedItemExists = updatedCart.any { it.goods.id == targetItem.goods.id }
        assertThat(deletedItemExists).isFalse()
    }

    @Test
    fun 페이지_이동_테스트() {
        setupMultiplePageData()
        waitForUpdate(500)
        assertThat(viewModel.page.value).isEqualTo(1)
        assertThat(viewModel.isMultiplePages.value).isEqualTo(true)

        viewModel.plusPage()
        waitForUpdate()
        assertThat(viewModel.page.value).isEqualTo(2)

        viewModel.minusPage()
        waitForUpdate()
        assertThat(viewModel.page.value).isEqualTo(1)
    }

    @Test
    fun 수량이_1인_아이템_감소시_삭제() {
        val cartItems = getCurrentCart()
        assertThat(cartItems).isNotEmpty()

        val targetItem = cartItems.find { it.quantity == 1 }!!

        viewModel.removeCartItemOrDecreaseQuantity(CartItem(targetItem.goods, 1))
        waitForUpdate(200)

        val updatedCartItems = getCurrentCart()
        assertThat(updatedCartItems.size).isLessThan(cartItems.size)
    }

    @Test
    fun 카트_아이템_수량_감소_테스트() {
        val cartItems = getCurrentCart()
        val multiQuantityItem = cartItems.find { it.quantity > 1 }!!
        val originalQuantity = multiQuantityItem.quantity

        // When
        viewModel.removeCartItemOrDecreaseQuantity(CartItem(multiQuantityItem.goods, 1))
        waitForCartUpdate()

        // Then
        val updatedCart = getCurrentCart()
        val updatedItem = updatedCart.find { it.goods.id == multiQuantityItem.goods.id }
        assertThat(updatedItem).isNotNull()
        assertThat(updatedItem!!.quantity).isEqualTo(originalQuantity - 1)
    }

    @Test
    fun 페이지_버튼_상태_변경_테스트() {
        setupMultiplePageData()
        waitForUpdate(500)

        // Then - 첫 페이지일 때
        assertThat(viewModel.page.value).isEqualTo(1)
        assertThat(viewModel.isLeftPageEnable.value).isFalse() // 왼쪽 비활성

        assertThat(viewModel.isRightPageEnable.value).isTrue() // 오른쪽 활성

        // When - 페이지 증가 후
        viewModel.plusPage()
        waitForUpdate()

        // Then - 마지막 페이지일 때
        assertThat(viewModel.isLeftPageEnable.value).isTrue() // 왼쪽 활성
        assertThat(viewModel.isRightPageEnable.value).isFalse() // 오른쪽 비활성
    }

    @Test
    fun getPosition_존재하는_아이템_반환() {
        // Given
        val cartItems = getCurrentCart()
        assertThat(cartItems).isNotEmpty()
        val firstItem = cartItems.first()

        // When
        val position = viewModel.getPosition(firstItem)

        // Then
        assertThat(position).isEqualTo(0)
    }

    @Test
    fun getPosition_존재하지_않는_아이템_null_반환() {
        // Given
        val nonExistentItem =
            CartItem(
                Goods("존재하지않는상품", 99999, "nonexistent", 9999),
                1,
            )

        // When
        val position = viewModel.getPosition(nonExistentItem)

        // Then
        assertThat(position).isNull()
    }

    @Test
    fun 마지막_페이지_아이템_삭제시_페이지_조정() {
        // Given - 여러 페이지 데이터 설정
        setupMultiplePageData()
        waitForUpdate(500)

        viewModel.plusPage()
        waitForUpdate()

        val initialPage = viewModel.page.value ?: 1
        val currentPageItems = getCurrentCart()

        if (currentPageItems.isNotEmpty()) {
            val lastItem = currentPageItems.last()

            // When - 마지막 아이템 삭제
            viewModel.delete(lastItem)
            waitForCartUpdate(100)

            // Then - 페이지가 적절히 조정되어야 함
            val finalPage = viewModel.page.value ?: 1
            assertThat(finalPage).isLessThanOrEqualTo(initialPage)
        }
    }

    @Test
    fun 여러_페이지_존재_여부_테스트() {
        // Given - 5개 미만일 때
        val initialMultiplePages = viewModel.isMultiplePages.value ?: false

        // 아이템이 적으면 단일 페이지
        if (getCurrentCart().size <= 5) {
            assertThat(initialMultiplePages).isFalse()
        }

        // When - 충분한 아이템 추가
        setupMultiplePageData()
        waitForUpdate(500)

        // Then - 여러 페이지 존재
        assertThat(viewModel.isMultiplePages.value).isTrue()
    }

    @Test
    fun 빈_카트_상태_테스트() {
        // Given - 모든 아이템 삭제
        val cartItems = getCurrentCart()
        cartItems.forEach { item ->
            viewModel.delete(item)
            waitForUpdate(300)
        }

        // When & Then
        val emptyCart = getCurrentCart()
        assertThat(emptyCart).isEmpty()
        assertThat(viewModel.isMultiplePages.value).isFalse()
        assertThat(viewModel.page.value).isEqualTo(1)
        assertThat(viewModel.isLeftPageEnable.value).isFalse()
        assertThat(viewModel.isRightPageEnable.value).isFalse()
    }

    private fun getCurrentCart(): List<CartItem> = viewModel.cart.value ?: emptyList()

    private fun waitForUpdate(delayMs: Long = 400) {
        Thread.sleep(delayMs)
    }

    private fun waitForCartUpdate(delayMs: Long = 600) {
        Thread.sleep(delayMs)
    }

    private fun setupMultiplePageData() {
        val additionalGoods =
            listOf(
                Goods("추가상품4", 40000, "url4", 4),
                Goods("추가상품5", 50000, "url5", 5),
                Goods("추가상품6", 60000, "url6", 6),
                Goods("추가상품7", 70000, "url7", 7),
            )

        additionalGoods.forEach { goods ->
            viewModel.addCartItemOrIncreaseQuantity(CartItem(goods, 1))
            waitForUpdate(200)
        }
    }
}
