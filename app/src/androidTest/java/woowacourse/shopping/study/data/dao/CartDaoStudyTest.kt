package woowacourse.shopping.study.data.dao

import android.content.Context
import android.os.SystemClock
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.model.entity.CartProductEntity

@Suppress("ktlint:standard:function-naming")
@RunWith(AndroidJUnit4::class)
class CartDaoStudyTest {
    private lateinit var db: ShoppingDatabase
    private lateinit var cartDao: CartDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        cartDao = db.cartDao()

        val cartProducts =
            (1..100_000).map {
                CartProductEntity(
                    productId = it,
                    quantity = 1,
                )
            }
        cartProducts.forEach { cartDao.insertCartProduct(it) }
    }

    @Test
    fun 룸_계산_및_앱_내에서_계산_로직_성능_차이_테스트() {
        val pageSize = 5

        // 1. DB에서 페이지 수 계산
        val dbStart = SystemClock.elapsedRealtimeNanos()
        val pageCountFromDbMethod = cartDao.getTotalPageCount(pageSize)
        val dbEnd = SystemClock.elapsedRealtimeNanos()

        // 2. 앱 메모리에서 페이지 수 계산
        val memoryStart = SystemClock.elapsedRealtimeNanos()
        val totalCartItemCount = cartDao.getCartItemCount()
        val pageCountFromApplicationMethod = (totalCartItemCount + pageSize - 1) / pageSize
        val memoryEnd = SystemClock.elapsedRealtimeNanos()

        val dbDuration = (dbEnd - dbStart) / 1_000_000.0
        val memoryDuration = (memoryEnd - memoryStart) / 1_000_000.0

        println("DB 연산 시간: ${"%.4f".format(dbDuration)} ms")
        println("앱 메모리 연산 시간: ${"%.4f".format(memoryDuration)} ms")
        println("(DB 연산 시간 - 앱 메모리 연산 시간): ${"%.4f".format(dbDuration - memoryDuration)} ms")

        // 결과 검증
        assertThat(pageCountFromDbMethod).isEqualTo(pageCountFromApplicationMethod)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
