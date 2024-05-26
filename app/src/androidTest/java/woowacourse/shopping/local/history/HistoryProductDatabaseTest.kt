package woowacourse.shopping.local.history

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import woowacourse.shopping.data.model.HistoryProduct

@RunWith(AndroidJUnit4::class)
class HistoryProductDatabaseTest {
    private lateinit var db: HistoryProductDatabase
    private lateinit var dao: HistoryProductDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, HistoryProductDatabase::class.java).build()
        dao = db.dao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @DisplayName("새로운 HistoryProduct를 추가하면 id를 반환한다.")
    fun testInsert1() {
        // when
        val insertedId = dao.insert(HistoryProduct(1))

        // then
        assertEquals(1, insertedId)
    }

    @Test
    @DisplayName("이미 존재하는 HistoryProduct를 추가하면? 예외 던진다.")
    fun testInsert2() {
        // given
        dao.insert(HistoryProduct(1))
        dao.insert(HistoryProduct(2))

        // when & then
        assertThrows<SQLiteConstraintException> { dao.insert(HistoryProduct(1)) }
    }

    @Test
    fun testDelete() {
        // given
        dao.insert(HistoryProduct(1))
        dao.insert(HistoryProduct(2))

        // when
        dao.delete(HistoryProduct(1))

        // then
        assertEquals(1, dao.findAll().size)
    }

    @Test
    fun testFindById() {
        // given
        dao.insert(HistoryProduct(1L))
        dao.insert(HistoryProduct(2L))

        // when
        val found = dao.findById(2L)

        // then
        assertEquals(2L, found?.id)
    }

    @Test
    fun testFindLatest() {
        // given
        dao.insert(HistoryProduct(1))
        dao.insert(HistoryProduct(3))
        dao.insert(HistoryProduct(2))

        // when
        val latest = dao.findLatest()

        // then
        assertEquals(2L, latest?.id)
    }

    @Test
    fun testFindAll() {
        // given
        dao.insert(HistoryProduct(1))
        dao.insert(HistoryProduct(7))
        dao.insert(HistoryProduct(4))
        dao.insert(HistoryProduct(3))
        dao.insert(HistoryProduct(2))
        dao.insert(HistoryProduct(8))

        // when
        val all = dao.findAll()

        // then
        assertEquals(6, all.size)
    }
}
