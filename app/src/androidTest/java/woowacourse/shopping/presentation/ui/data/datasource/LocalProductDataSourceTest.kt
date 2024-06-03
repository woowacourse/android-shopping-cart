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
import woowacourse.shopping.data.db.dao.ProductDao
import woowacourse.shopping.data.db.mapper.toEntity

class LocalProductDataSourceTest {
    private lateinit var dao: ProductDao
    private lateinit var db: AppDatabase

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.productDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Product를_쓰고_읽기`() {
        dao.putProduct(STUB_PRODUCT_1.toEntity())
        val actual = dao.findById(1)
        val expected = STUB_PRODUCT_1.toEntity()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `offset과_size로_product_list_찾기`() {
        val stubA = STUB_PRODUCT_1.toEntity()
        val stubB = STUB_PRODUCT_2.toEntity()
        val stubC = STUB_PRODUCT_3.toEntity()
        dao.putProduct(stubA)
        dao.putProduct(stubB)
        dao.putProduct(stubC)

        val actual = dao.findByOffsetAndSize(0, 3)
        val expected = listOf(stubA, stubB, stubC)
        assertThat(actual).isEqualTo(expected)
    }
}
