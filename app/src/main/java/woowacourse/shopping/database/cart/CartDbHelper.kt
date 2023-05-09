package woowacourse.shopping.database.cart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.database.ProductContract.DATABASE_NAME
import woowacourse.shopping.database.ProductContract.DATABASE_VERSION

class CartDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ProductContract.CartEntry.TABLE_NAME};")
        onCreate(db)
    }

    companion object {
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${ProductContract.CartEntry.TABLE_NAME} (" +
                "${ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID} int PRIMARY KEY" +
                ");"
    }
}
