package woowacourse.shopping.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecentViewedDBHelper(context: Context) : SQLiteOpenHelper(context, TABLE_TITLE, null, 2) {
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
        writableDatabase.execSQL(
            "DELETE FROM ${RecentViewedContract.TABLE_NAME} " +
                "WHERE ${RecentViewedContract.TABLE_COLUMN_ID}=$id",
        )
    }

    fun selectWhereId(id: Int): Int {
        var result = 0
        readableDatabase.use {
            val cursor = it.rawQuery(
                "select * from ${RecentViewedContract.TABLE_NAME} " +
                    "WHERE ${RecentViewedContract.TABLE_COLUMN_ID}=$id",
                null,
            )
            while (cursor.moveToNext()) {
                result = cursor.getInt(0)
            }
            cursor.close()
        }
        return result
    }

    fun selectAll(): List<Int> {
        val viewedProducts = mutableListOf<Int>()
        readableDatabase.use {
            val cursor = it.rawQuery("select * from ${RecentViewedContract.TABLE_NAME}", null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                viewedProducts.add(id)
            }
            cursor.close()
        }
        return viewedProducts
    }

    fun selectMostRecent(): Int {
        var id = 0
        readableDatabase.use {
            val cursor = it.rawQuery(
                "select * from ${RecentViewedContract.TABLE_NAME} " +
                    "WHERE rowid = (SELECT MAX(rowid)" +
                    " FROM ${RecentViewedContract.TABLE_NAME});",
                null,
            )
            while (cursor.moveToNext()) {
                id = cursor.getInt(0)
            }
            cursor.close()
        }
        return id
    }

    fun removeOldest() {
        writableDatabase.execSQL(
            "DELETE FROM ${RecentViewedContract.TABLE_NAME}" +
                " WHERE rowid = (SELECT MIN(rowid)" +
                " FROM ${RecentViewedContract.TABLE_NAME});",
        )
    }

    companion object {
        private const val TABLE_TITLE = "recent_viewed"
    }
}
