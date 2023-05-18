package woowacourse.shopping.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.domain.CartProduct

class CartDBHelper(context: Context) : SQLiteOpenHelper(context, TABLE_TITLE, null, 3) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${CartContract.TABLE_NAME} (" +
                "  ${CartContract.TABLE_COLUMN_ID} Int PRIMARY KEY not null," +
                "  ${CartContract.TABLE_COLUMN_COUNT} Int not null," +
                "  ${CartContract.TABLE_COLUMN_CHECK} Int not null" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${CartContract.TABLE_NAME}")
        onCreate(db)
    }

    fun insert(id: Int, count: Int, check: Boolean) {
        val values = ContentValues()
        values.put(CartContract.TABLE_COLUMN_ID, id)
        values.put(CartContract.TABLE_COLUMN_COUNT, count)
        values.put(CartContract.TABLE_COLUMN_CHECK, check.compareTo(false))
        writableDatabase.insert(CartContract.TABLE_NAME, null, values)
    }

    fun update(id: Int, count: Int) {
        writableDatabase.execSQL("UPDATE ${CartContract.TABLE_NAME} SET ${CartContract.TABLE_COLUMN_COUNT}=$count WHERE ${CartContract.TABLE_COLUMN_ID}=$id")
    }

    fun remove(id: Int) {
        writableDatabase.execSQL("DELETE FROM ${CartContract.TABLE_NAME} WHERE ${CartContract.TABLE_COLUMN_ID}=$id")
    }

    fun selectAll(): List<CartProduct> {
        val products = mutableListOf<CartProduct>()
        val sql = "select * from ${CartContract.TABLE_NAME}"
        val cursor = readableDatabase.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_ID))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_COUNT))
            val check = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_CHECK))
            val isChecked = check == 1
            products.add(CartProduct(id, count, isChecked))
        }
        cursor.close()
        return products
    }

    fun selectWhereId(id: Int): CartProduct? {
        val sql =
            "select * from ${CartContract.TABLE_NAME} where ${CartContract.TABLE_COLUMN_ID}=$id"
        val cursor = readableDatabase.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_ID))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_COUNT))
            val check = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_CHECK))
            val isChecked = check == 1
            cursor.close()
            return CartProduct(id, count, isChecked)
        }
        return null
    }

    fun selectRange(mark: Int, rangeSize: Int): List<CartProduct> {
        val products = mutableListOf<CartProduct>()
        val sql = "select * from ${CartContract.TABLE_NAME} limit $rangeSize offset $mark;"
        val cursor = readableDatabase.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_ID))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_COUNT))
            val check = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.TABLE_COLUMN_CHECK))
            val isChecked = check == 1
            products.add(CartProduct(id, count, isChecked))
        }
        cursor.close()
        return products
    }

    fun getSize(mark: Int): Boolean {
        val itemsSize = selectAll().size
        return mark in 0 until itemsSize
    }

    companion object {
        private const val TABLE_TITLE = "cart"
    }
}
