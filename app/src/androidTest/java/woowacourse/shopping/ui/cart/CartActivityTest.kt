package woowacourse.shopping.ui.cart

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Test
import woowacourse.shopping.database.ProductDBHelper

class CartActivityTest {
    @Test
    fun 장바구니_테이블이_데이터베이스에_존재하는지_확인() {
        val databaseHelper = ProductDBHelper(ApplicationProvider.getApplicationContext())
        val db: SQLiteDatabase = databaseHelper.readableDatabase

        assertTrue(db != null)

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM cart",
            null,
        )
        assertTrue(cursor != null && cursor.count > 0)

        cursor.close()
        db.close()
    }
}
