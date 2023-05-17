package woowacourse.shopping.ui.products

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Test
import woowacourse.shopping.database.ProductDBHelper

class ProductListActivityTest {

    @Test
    fun 최근_조회한_항목_테이블이_데이터베이스에_존재하는지_확인() {
        val databaseHelper = ProductDBHelper(ApplicationProvider.getApplicationContext())
        val db: SQLiteDatabase = databaseHelper.readableDatabase

        assertTrue(db != null)

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM recently_viewed_product",
            null,
        )
        assertTrue(cursor != null && cursor.count > 0)

        cursor.close()
        db.close()
    }
}
