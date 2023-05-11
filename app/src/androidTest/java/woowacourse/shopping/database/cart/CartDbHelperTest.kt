package woowacourse.shopping.database.cart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test

class CartDbHelperTest {
    private lateinit var context: Context
    private lateinit var sut: CartDbHelper
    private lateinit var database: SQLiteDatabase

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        sut = CartDbHelper(context)
        database = sut.writableDatabase
    }

    @Test
    fun name() {
        sut.onCreate(database)
    }
}
