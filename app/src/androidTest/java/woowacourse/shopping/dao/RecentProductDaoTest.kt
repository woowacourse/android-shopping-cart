package woowacourse.shopping.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.Fixture
import woowacourse.shopping.data.db.CartDatabase
import woowacourse.shopping.data.db.RecentlyProductDao

class RecentProductDaoTest {
    private lateinit var db: CartDatabase
    private lateinit var dao: RecentlyProductDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, CartDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = db.recentlyProductDao()
        Fixture.dummyRecentlyViewedProductList.forEach { product ->
            dao.insertProduct(product)
        }
    }

    @Test
    fun `최근_본_상품_정보를_모두_불러온다`() {
        // when
        val result = dao.getCount()

        // then
        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `페이지_사이즈만큼_상품_정보를_불러온다`() {
        // when
        // viewedAt은 System.currentTimeMillis()로 저장, 숫자가 작은 id 0인 아이템이 더 오래 됨
        Fixture.dummyRecentlyViewedProductList.forEach { product ->
            dao.insertProduct(product)
        }
        val result = dao.getOldestProduct()

        // then
        assertThat(result.productId).isEqualTo(0)
    }

    @Test
    fun `상품을_추가할_수_있다`() {
        // when
        Fixture.dummyRecentlyViewedProductList.forEach { product ->
            dao.insertProduct(product)
        }
        val result = dao.getMostRecentProduct()

        // then
        assertThat(result!!.productId).isEqualTo(2)
    }

    @Test
    fun `상품을_삭제할_수_있다`() {
        // when
        dao.delete(Fixture.dummyRecentlyViewedProduct)
        val result = dao.getCount()

        // then
        assertThat(result).isEqualTo(2)
    }
}
