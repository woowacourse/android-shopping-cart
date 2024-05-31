package woowacourse.shopping.presentation.ui.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_1
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_2
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_3
import woowacourse.shopping.data.db.AppDatabase
import woowacourse.shopping.data.db.dao.OrderDao
import woowacourse.shopping.data.db.mapper.toEntity
import woowacourse.shopping.data.db.model.OrderEntity
import woowacourse.shopping.domain.model.Order

class LocalOrderDataSourceTest {
    private lateinit var dao: OrderDao
    private lateinit var db: AppDatabase

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dao = db.orderDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Order를_읽고_쓰기`() {
        val order = Order(1, 3, STUB_PRODUCT_1).toEntity()
        dao.putOrder(order)
        val actual = dao.getOrderByProductId(STUB_PRODUCT_1.id)
        val expected = listOf(order)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `여러_Order를_한번에_불러온다`() {
        val stubA = OrderEntity(1, 1, STUB_PRODUCT_1.toEntity())
        val stubB = OrderEntity(2, 1, STUB_PRODUCT_2.toEntity())
        val stubC = OrderEntity(3, 1, STUB_PRODUCT_3.toEntity())
        dao.putOrder(stubA)
        dao.putOrder(stubB)
        dao.putOrder(stubC)

        val actual = dao.getOrders()
        val expected = listOf(stubA, stubB, stubC)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은_주문을_다시_넣을_경우_새로운_주문_갱신된다`() {
        // given
        val oldOrder = Order(1, 1, STUB_PRODUCT_1).toEntity()
        val newOrder = Order(1, 5, STUB_PRODUCT_1).toEntity()
        dao.putOrder(oldOrder)

        // when
        dao.putOrder(newOrder)

        val actual = dao.getOrders()[0]
        val expected = newOrder
        assertThat(actual).isEqualTo(expected)
    }
}
