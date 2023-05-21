package woowacourse.shopping.data.recentproduct

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecentProductDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${RecentProductDbContract.TABLE_NAME}(" +
                "${RecentProductDbContract.PRODUCT_ID} int PRIMARY KEY," +
                "${RecentProductDbContract.TIMESTAMP} int" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${RecentProductDbContract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "recentProduct.db"
    }
}
