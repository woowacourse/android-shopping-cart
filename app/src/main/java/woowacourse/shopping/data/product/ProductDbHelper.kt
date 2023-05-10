package woowacourse.shopping.data.product

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDbHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE ${ProductContract.TABLE_NAME} (
                    ${ProductContract.TABLE_COLUMN_ID} INTEGER,
                    ${ProductContract.TABLE_COLUMN_IMAGE_URL} TEXT,
                    ${ProductContract.TABLE_COLUMN_NAME} TEXT, 
                    ${ProductContract.TABLE_COLUMN_PRICE} INTEGER
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductContract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "product.db"
    }
}
