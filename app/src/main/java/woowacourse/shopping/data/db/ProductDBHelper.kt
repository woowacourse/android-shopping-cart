package woowacourse.shopping.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_QUERY)
    }

    companion object {
        const val DB_NAME = "ProductDB"
        private const val DB_VERSION: Int = 1
        const val TABLE_NAME = "products"
        const val KEY_NAME = "name"
        const val KEY_IMAGE = "image"
        const val KEY_PRICE = "price"

        private const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$KEY_NAME text," +
            "$KEY_IMAGE text," +
            "$KEY_PRICE text" +
            ");"
        private const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
