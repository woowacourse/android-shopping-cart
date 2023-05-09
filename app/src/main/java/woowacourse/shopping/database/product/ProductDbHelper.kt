package woowacourse.shopping.database.product

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract

class ProductDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ProductContract.ProductEntry.TABLE_NAME};")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "product.db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${ProductContract.ProductEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} int PRIMARY KEY," +
                "${ProductContract.ProductEntry.COLUMN_NAME_NAME} text," +
                "${ProductContract.ProductEntry.COLUMN_NAME_PRICE} int," +
                "${ProductContract.ProductEntry.COLUMN_NAME_IMAGE_URL} text" +
                ");"
    }
}
