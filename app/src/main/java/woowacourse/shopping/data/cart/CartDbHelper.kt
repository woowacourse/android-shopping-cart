package woowacourse.shopping.data.cart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CartDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${CartDbContract.TABLE_NAME}(" +
                "${CartDbContract.PRODUCT_ID} int PRIMARY KEY," +
                "${CartDbContract.COUNT} int," +
                "${CartDbContract.TIMESTAMP} int" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${CartDbContract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "cart.db"
    }
}
