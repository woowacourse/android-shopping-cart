package woowacourse.shopping.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.Fixture
import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartDatabase

class CartDaoTest {
    private lateinit var db: CartDatabase
    private lateinit var dao: CartDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, CartDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = db.cartDao()
        dao.insertProduct(Fixture.dummyCartEntity)
    }

    @Test
    fun `상품_정보를_모두_불러온다`() {
        // when
        val result = dao.getAllProducts()

        // then
        assertThat(result).hasSize(1)
    }

    @Test
    fun `페이지_사이즈만큼_상품_정보를_불러온다`() {
        // when
        val pageSize = 1
        val result = dao.getPagedProducts(pageSize, 0)

        // then
        assertThat(result).hasSize(1)
    }

    @Test
    fun `상품을_추가할_수_있다`() {
        // when
        dao.insertProduct(Fixture.dummyCartEntity)
        val result = dao.getAllProducts()

        // then
        assertThat(result).hasSize(2)
    }

    @Test
    fun `상품을_삭제할_수_있다`() {
        // when
        val productId = 0L
        dao.deleteProductById(productId)
        val result = dao.getAllProducts()

        // then
        assertThat(result).hasSize(0)
    }
}
