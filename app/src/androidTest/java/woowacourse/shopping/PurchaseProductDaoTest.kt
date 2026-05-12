package woowacourse.shopping

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.local.dao.PurchaseProductsDao
import woowacourse.shopping.data.local.database.DataBase
import woowacourse.shopping.data.local.entity.PurchaseProductEntity

@RunWith(AndroidJUnit4::class)
class PurchaseProductDaoTest {
    private lateinit var db: DataBase
    private lateinit var dao: PurchaseProductsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, DataBase::class.java).build()
        dao = db.purchaseProductsDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetProduct() =
        runBlocking {
            // given
            val entity =
                PurchaseProductEntity(
                    id = "1",
                    count = 1,
                )

            // when
            dao.insertAll(entity)

            // then
            val allProducts = dao.getAll().first()
            assert(allProducts.size == 1)
        }
}
