package woowacourse.shopping.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.FIXTURE

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
        dao.insertProduct(FIXTURE.DUMMY_PRODUCT.toEntity())
    }

    @Test
    fun `상품_정보를_모두_불러온다`() {
        // when
        val result = dao.getAllProduct()

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("맥심 모카골드 마일드")
    }

    @Test
    fun `페이지_사이즈만큼_상품_정보를_불러온다`() {
        // when
        val pageSize = 1
        val result = dao.getPagedProduct(pageSize, 0)

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("맥심 모카골드 마일드")
    }

    @Test
    fun `상품을_추가할_수_있다`() {
        // when
        dao.insertProduct(FIXTURE.DUMMY_PRODUCT.toEntity())
        val result = dao.getAllProduct()

        // then
        assertThat(result).hasSize(2)
        assertThat(result[1].name).isEqualTo("맥심 모카골드 마일드")
    }

    @Test
    fun `상품을_삭제할_수_있다`() {
        // when
        val productId = 0L
        dao.deleteByProductId(productId)
        val result = dao.getAllProduct()

        // then
        assertThat(result).hasSize(0)
    }
}
