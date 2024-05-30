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
import org.junit.jupiter.api.assertDoesNotThrow
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
        insertHistories(1, 2)

        // when & then
        assertThrows<SQLiteConstraintException> { dao.insert(HistoryProduct(1)) }
    }

    @Test
    @DisplayName("상품 내역에 존재하는 상품 내역을 제거한다")
    fun testDelete() {
        // given
        insertHistories(1, 2)

        // when
        dao.delete(HistoryProduct(1))

        // then
        assertEquals(1, dao.findAll().size)
    }

    @Test
    @DisplayName("상품 내역에 존재하는 상품 id 를 찾는다")
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
    @DisplayName("상품 내역에 존재하는 가장 최근 상품을 찾는다")
    fun testFindLatest() {
        // given
        insertHistories(1, 3, 2)

        // when
        val latest = dao.findLatest()

        // then
        assertEquals(2L, latest?.id)
    }

    @Test
    @DisplayName("상품 내역에 존재하는 모든 상품을 찾는다")
    fun testFindAll() {
        // given
        insertHistories(1, 7, 4, 3, 2, 8)

        // when
        val all = dao.findAll()

        // then
        assertEquals(6, all.size)
        assertDoesNotThrow {
            dao.findById(1)
            dao.findById(7)
            dao.findById(4)
            dao.findById(3)
            dao.findById(2)
            dao.findById(8)
        }
    }

    /**
     * 상품 내역에 id 값들을 넣는다
     * @param ids 상품 내역에 저장할 id 값들
     */
    private fun insertHistories(vararg ids: Long) {
        ids.forEach { id ->
            dao.insert(HistoryProduct(id = id))
        }
    }
}
