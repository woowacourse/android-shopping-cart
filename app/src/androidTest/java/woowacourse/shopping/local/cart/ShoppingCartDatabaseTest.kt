package woowacourse.shopping.local.cart

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith
import woowacourse.shopping.data.model.ProductIdsCountData

@RunWith(AndroidJUnit4::class)
class ShoppingCartDatabaseTest {
    private lateinit var dao: ShoppingCartDao
    private lateinit var db: ShoppingCartDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ShoppingCartDatabase::class.java).build()
        dao = db.dao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsert() {
        // given
        val productIdsCountData = ProductIdsCountData(1, 1)

        // when
        val id = dao.insert(productIdsCountData)

        // then
        assertEquals(1, id)
    }

    @Test
    fun testFindById() {
        // given
        val productIdsCountData = ProductIdsCountData(1, 1)
        dao.insert(productIdsCountData)

        // when
        val found = dao.findById(1)

        // then
        assertEquals(productIdsCountData, found)
    }

    @Test
    fun testFindAll() {
        // given
        dao.insert(ProductIdsCountData(1, 1))
        dao.insert(ProductIdsCountData(2, 2))
        dao.insert(ProductIdsCountData(3, 3))

        // when
        val findAll = dao.findAll()

        // then
        assertAll(
            { assertEquals(ProductIdsCountData(1, 1), findAll[0]) },
            { assertEquals(ProductIdsCountData(2, 2), findAll[1]) },
            { assertEquals(ProductIdsCountData(3, 3), findAll[2]) },
        )
    }

    @Test
    fun testCountAll() {
        // given
        dao.insert(ProductIdsCountData(1, 1))
        dao.insert(ProductIdsCountData(2, 2))

        // when
        val count = dao.countAll()

        // then
        assertEquals(2, count)
    }

    @Test
    fun testDelete() {
        // given
        dao.insert(ProductIdsCountData(1, 1))
        dao.insert(ProductIdsCountData(2, 2))

        // when
        dao.delete(1)

        // then
        val findById = dao.findById(1)
        assertEquals(null, findById)
    }

    @Test
    fun testUpdate() {
        // given
        dao.insert(ProductIdsCountData(1, 1))
        dao.insert(ProductIdsCountData(2, 2))

        // when
        dao.update(ProductIdsCountData(1, 3))

        // then
        val findById = dao.findById(1)
        assertEquals(ProductIdsCountData(1, 3), findById)
    }
}
