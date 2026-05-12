package woowacourse.shopping

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.local.dao.PurchaseProductsDao
import woowacourse.shopping.data.local.database.DataBase
import woowacourse.shopping.data.local.entity.PurchaseProductEntity

@RunWith(AndroidJUnit4::class)
class PurchaseProductDaoTest {
    private lateinit var db: DataBase
    private lateinit var dao: PurchaseProductsDao

    @Test
    fun `상품을_저장하고_불러올_수_있다`() = runBlocking {
        // given
        createDb()
        val entity =
            PurchaseProductEntity(
                id = "1",
                count = 1,
            )

        // when
        dao.upsert(entity)

        // then
        val allProducts = dao.getAll().first()
        assertEquals(1, allProducts?.size)
        closeDb()
    }

    @Test
    fun `동일한_ID를_갖는_상품을_저장하면_count가_증가한다`() = runBlocking {
        // given
        createDb()
        val entity =
            PurchaseProductEntity(
                id = "1",
                count = 1,
            )
        dao.upsert(entity)

        // when
        dao.upsert(entity)

        // then
        val allProducts = dao.getAll().first()
        assertEquals(2, allProducts?.first()?.count)
        closeDb()
    }

    @Test
    fun `ID를통해_장바구니에_담긴_상품을_삭제할_수_있다`() = runBlocking {
        createDb()
        val entity =
            PurchaseProductEntity(
                id = "1",
                count = 1,
            )
        dao.upsert(entity)

        // when
        dao.deleteWithId(entity.id)

        // then
        val allProducts = dao.getAll().first()
        assertEquals(0, allProducts?.size)
        closeDb()
    }

    private fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, DataBase::class.java).build()
        dao = db.purchaseProductsDao()
    }

    private fun closeDb() {
        db.close()
    }
}
