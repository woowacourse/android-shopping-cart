package woowacourse.shopping.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecentViewedDBHelper(context: Context) : SQLiteOpenHelper(context, "recent_viewed", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${RecentViewedContract.TABLE_NAME} (" +
                "  ${RecentViewedContract.TABLE_COLUMN_ID} Int PRIMARY KEY not null" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${RecentViewedContract.TABLE_NAME}")
        onCreate(db)
    }

    fun insert(id: Int) {
        val values = ContentValues()
        values.put(RecentViewedContract.TABLE_COLUMN_ID, id)
        writableDatabase.insert(RecentViewedContract.TABLE_NAME, null, values)
    }

    fun remove(id: Int) {
        writableDatabase.execSQL("DELETE FROM ${RecentViewedContract.TABLE_NAME} WHERE ${RecentViewedContract.TABLE_COLUMN_ID}=$id")
    }

    fun selectWhereId(id: Int): Int? {
        val sql = "select * from ${RecentViewedContract.TABLE_NAME} WHERE ${RecentViewedContract.TABLE_COLUMN_ID}=$id"
        val cursor = readableDatabase.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(RecentViewedContract.TABLE_COLUMN_ID))
            cursor.close()
            return id
        }
        return null
    }

    fun selectAll(): List<Int> {
        val viewedProducts = mutableListOf<Int>()
        val sql = "select * from ${RecentViewedContract.TABLE_NAME}"
        val cursor = readableDatabase.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(RecentViewedContract.TABLE_COLUMN_ID))
            viewedProducts.add(id)
        }
        cursor.close()
        return viewedProducts
    }

    fun removeOldest() {
        writableDatabase.execSQL("DELETE FROM ${RecentViewedContract.TABLE_NAME} WHERE rowid = (SELECT MIN(rowid) FROM ${RecentViewedContract.TABLE_NAME});")
    }
}
