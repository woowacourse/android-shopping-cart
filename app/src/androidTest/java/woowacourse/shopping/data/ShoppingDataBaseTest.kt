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
        shoppingCartItemDao.insert(ShoppingCartItemEntity.STUB)

        // given
        val cartItems = shoppingCartItemDao.shoppingCartItems()

        // then
        assertThat(cartItems).contains(ShoppingCartItemEntity.STUB)
    }

    @Test
    @DisplayName("장바구니 상품의 개수가 1일 때, 2를 증가시키면 3가 된다.")
    fun increase_to_3_When_cart_Item_quantity_is_1() {
        // when
        shoppingCartItemDao.insert(ShoppingCartItemEntity.STUB)
        assertThat(ShoppingCartItemEntity.STUB.totalQuantity).isEqualTo(1)

        // given
        shoppingCartItemDao.increaseCount(ShoppingCartItemEntity.STUB.product.id, 2)

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

    @Test
    @DisplayName("총 장바구니 아이템의 수는 7개이고, 한 페이지당 아이템이 5개일 때, pageIndex 위치로부터 5개까지의 데이터를 불러올 수 있다")
    fun can_get_items_When_page_size_is_5_and_cart_item_count_is_7() {
        // when
        shoppingCartItemDao.insertAll(ShoppingCartItemEntity.STUB_LIST)

        val pageSize = 5

        // given
        val firstActual = shoppingCartItemDao.shoppingCartItems(pageSize, 0)
        val secondActual = shoppingCartItemDao.shoppingCartItems(pageSize, pageSize)

        // then
        assertThat(firstActual).hasSize(5)
        assertThat(secondActual).hasSize(2)
        assertThat(firstActual).doesNotContainAnyElementsOf(secondActual)
    }

    companion object {
        private val RECENT_PRODUCT_STUB_A =
            RecentProductEntity(1, ProductEntity.STUB, LocalDateTime.now())
        private val RECENT_PRODUCT_STUB_B =
            RecentProductEntity(2, ProductEntity.STUB, LocalDateTime.now())
    }
}
