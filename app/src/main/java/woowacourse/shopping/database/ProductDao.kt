package woowacourse.shopping.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDao(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${ProductDBContract.TABLE_NAME}(" +
                "${ProductDBContract.PRODUCT_ID} int," +
                "${ProductDBContract.PRODUCT_IMG} text," +
                "${ProductDBContract.PRODUCT_NAME} text," +
                "${ProductDBContract.PRODUCT_PRICE} int" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ProductDBContract.TABLE_NAME}")
    }

    companion object {
        private const val DB_NAME = "shopping.db"
    }
}
