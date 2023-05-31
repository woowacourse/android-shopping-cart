package woowacourse.shopping.data.db.recentproduct

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecentProductDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE ${RecentProductContract.TABLE_NAME} (
                    ${RecentProductContract.TABLE_COLUMN_ID} INTEGER,
                    ${RecentProductContract.TABLE_COLUMN_IMAGE_URL} TEXT,
                    ${RecentProductContract.TABLE_COLUMN_NAME} TEXT, 
                    ${RecentProductContract.TABLE_COLUMN_PRICE} INTEGER
                )
            """.trimIndent(),
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${RecentProductContract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "recent_product.db"
    }
}
