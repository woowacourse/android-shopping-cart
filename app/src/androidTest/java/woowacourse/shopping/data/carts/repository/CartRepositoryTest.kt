package woowacourse.shopping.data.carts.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Suppress("ktlint:standard:function-naming")
@RunWith(AndroidJUnit4::class)
class CartRepositoryTest {
    private lateinit var database: ShoppingDatabase
    private lateinit var cartRepository: CartRepositoryImpl

    private val goods1 = Goods("상품1", 10000, "url1", 1)
    private val goods2 = Goods("상품2", 20000, "url2", 2)

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
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 카트_아이템_추가_및_조회() {
        // When
        addGoods(goods1)
        val result = fetchAllCartItems()

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0].goods.name).isEqualTo("상품1")
        assertThat(result[0].quantity).isEqualTo(1)
    }

    @Test
    fun 여러_아이템_추가_및_조회() {
        // When
        addGoods(goods1)
        addGoods(goods2)
        val result = fetchAllCartItems()

        // Then
        assertThat(result).hasSize(2)
    }

    @Test
    fun 수량_증가_테스트() {
        // Given
        addGoods(goods1) // 초기 수량 1

        // When
        increaseQuantity(goods1, 2) // 2개 추가
        val result = fetchAllCartItems()

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0].quantity).isEqualTo(3) // 1 + 2 = 3
    }

    @Test
    fun 수량_감소_테스트() {
        // Given
        addGoods(goods1)
        increaseQuantity(goods1, 4) // 총 5개

        // When
        decreaseQuantity(goods1, 2) // 2개 감소
        val result = fetchAllCartItems()

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0].quantity).isEqualTo(3) // 5 - 2 = 3
    }

    @Test
    fun 아이템_삭제_테스트() {
        // Given
        addGoods(goods1)
        addGoods(goods2)

        // When
        deleteGoods(goods1)
        val result = fetchAllCartItems()

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0].goods.name).isEqualTo("상품2")
    }

    @Test
    fun 전체_아이템_개수_조회() {
        // Given
        addGoods(goods1)
        addGoods(goods2)

        // When
        val size = fetchItemsSize()

        // Then
        assertThat(size).isEqualTo(2)
    }

    @Test
    fun 페이징_조회_테스트() {
        // Given
        addGoods(goods1)
        addGoods(goods2)

        // When
        val result = fetchPageCartItems(1, 0) // 첫 번째 아이템만

        // Then
        assertThat(result).hasSize(1)
    }

    @Test
    fun 빈_카트_상태_테스트() {
        // When
        val result = fetchAllCartItems()
        val size = fetchItemsSize()

        // Then
        assertThat(result).isEmpty()
        assertThat(size).isEqualTo(0)
    }

    private fun addGoods(goods: Goods) {
        val latch = CountDownLatch(1)
        cartRepository.addOrIncreaseQuantity(goods, 1) {
            latch.countDown()
        }
        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()
    }

    private fun increaseQuantity(
        goods: Goods,
        quantity: Int,
    ) {
        val latch = CountDownLatch(1)
        cartRepository.addOrIncreaseQuantity(goods, quantity) {
            latch.countDown()
        }
        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()
    }

    private fun decreaseQuantity(
        goods: Goods,
        quantity: Int,
    ) {
        val latch = CountDownLatch(1)
        cartRepository.removeOrDecreaseQuantity(goods, quantity) {
            latch.countDown()
        }
        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()
    }

    private fun deleteGoods(goods: Goods) {
        val latch = CountDownLatch(1)
        cartRepository.delete(goods) {
            latch.countDown()
        }
        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()
    }

    private fun fetchAllCartItems(): List<CartItem> {
        val latch = CountDownLatch(1)
        var result: List<CartItem>? = null

        cartRepository.fetchAllCartItems { cartItems ->
            result = cartItems
            latch.countDown()
        }
        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()

        return result!!
    }

    private fun fetchPageCartItems(
        limit: Int,
        offset: Int,
    ): List<CartItem> {
        val latch = CountDownLatch(1)
        var result: List<CartItem>? = null

        cartRepository.fetchPageCartItems(limit, offset) { cartItems ->
            result = cartItems
            latch.countDown()
        }
        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()

        return result!!
    }

    private fun fetchItemsSize(): Int {
        val latch = CountDownLatch(1)
        var result: Int? = null

        cartRepository.getAllItemsSize { size ->
            result = size
            latch.countDown()
        }
        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue()

        return result!!
    }
}
