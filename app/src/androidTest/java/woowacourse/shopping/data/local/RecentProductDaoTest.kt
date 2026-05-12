package woowacourse.shopping.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecentProductDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: RecentProductDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.recentProductDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `최근_본_상품을_추가하면_정상적으로_저장된다`() = runBlocking {
        val product = RecentProductEntity("1", "url1", "상품1", 1000L)
        dao.insert(product)

        val recentProducts = dao.getRecentProducts().first()
        assertEquals(1, recentProducts.size)
        assertEquals("1", recentProducts[0].id)
    }

    @Test
    fun 최근_본_상품은_최대_10개까지만_유지된다() = runBlocking {
        for (i in 1..15) {
            dao.insertWithLimit(RecentProductEntity(i.toString(), "url$i", "상품$i", i.toLong()))
        }

        val recentProducts = dao.getRecentProducts().first()
        assertEquals(10, recentProducts.size)
        assertEquals("15", recentProducts[0].id)
        assertEquals("6", recentProducts[9].id)
    }

    @Test
    fun 동일한_상품을_다시_볼_경우_순서가_최상단으로_갱신된다() = runBlocking {
        dao.insertWithLimit(RecentProductEntity("1", "url1", "상품1", 1000L))
        dao.insertWithLimit(RecentProductEntity("2", "url2", "상품2", 2000L))
        
        dao.insertWithLimit(RecentProductEntity("1", "url1", "상품1", 3000L))

        val recentProducts = dao.getRecentProducts().first()
        assertEquals(2, recentProducts.size)
        assertEquals("1", recentProducts[0].id)
        assertEquals("2", recentProducts[1].id)
    }
}
