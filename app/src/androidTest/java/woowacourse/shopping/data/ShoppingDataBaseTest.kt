package woowacourse.shopping.data

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.dao.ShoppingCartItemDao
import woowacourse.shopping.data.database.ShoppingDataBase
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.entity.ShoppingCartItemEntity
import woowacourse.shopping.fixture.context
import java.io.IOException
import java.time.LocalDateTime

class ShoppingDataBaseTest {
    private lateinit var productDao: ProductDao
    private lateinit var recentProductDao: RecentProductDao
    private lateinit var shoppingCartItemDao: ShoppingCartItemDao

    private lateinit var db: ShoppingDataBase

    @Before
    fun setUp() {
        db =
            Room.inMemoryDatabaseBuilder(
                context, ShoppingDataBase::class.java,
            ).build()
        productDao = db.productDao()
        recentProductDao = db.recentProductDao()
        shoppingCartItemDao = db.shoppingCartItemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @DisplayName("저장한 장바구니 아이템을 다시 꺼낼 수 있다.")
    fun can_get_cart_item_After_save_the_item() {
        // when
        shoppingCartItemDao.insert(CART_ITEM_STUB)

        // given
        val cartItems = shoppingCartItemDao.shoppingCartItems()

        // then
        assertThat(cartItems).contains(CART_ITEM_STUB)
    }

    @Test
    @DisplayName("장바구니 상품의 개수가 1일 때, 2를 증가시키면 3가 된다.")
    fun increase_to_3_When_cart_Item_quantity_is_1() {
        // when
        shoppingCartItemDao.insert(CART_ITEM_STUB)
        assertThat(CART_ITEM_STUB.totalQuantity).isEqualTo(1)

        // given
        shoppingCartItemDao.increaseCount(CART_ITEM_STUB.product.id, 2)

        // then
        val actual = shoppingCartItemDao.shoppingCartItems().first()
        assertThat(actual.totalQuantity).isEqualTo(3)
    }

    @Test
    @DisplayName("가장 마지막에 저장한 recentProduct를 꺼낼 수 있다.")
    fun can_get_recent_product_that_last_saved() {
        // when
        recentProductDao.insert(RECENT_PRODUCT_STUB_A)
        recentProductDao.insert(RECENT_PRODUCT_STUB_B)

        // given
        val recentProducts = recentProductDao.lastRecentProduct()

        // then
        assertThat(recentProducts).isEqualTo(RECENT_PRODUCT_STUB_B)
    }

    companion object {
        private val CART_ITEM_STUB = ShoppingCartItemEntity(1, ProductEntity.STUB, 1)
        private val RECENT_PRODUCT_STUB_A =
            RecentProductEntity(1, ProductEntity.STUB, LocalDateTime.now())
        private val RECENT_PRODUCT_STUB_B =
            RecentProductEntity(2, ProductEntity.STUB, LocalDateTime.now())
    }
}
