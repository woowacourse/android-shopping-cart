package woowacourse.shopping.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.domain.CartProduct

class CartDBHelper(context: Context) : SQLiteOpenHelper(context, TABLE_TITLE, null, 3) {
    private fun Int.toBoolean(): Boolean = this == 1

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
        writableDatabase.execSQL(
            "UPDATE ${CartContract.TABLE_NAME} " +
                "SET ${CartContract.TABLE_COLUMN_COUNT}=$count " +
                "WHERE ${CartContract.TABLE_COLUMN_ID}=$id",
        )
    }

    fun remove(id: Int) {
        writableDatabase.execSQL(
            "DELETE FROM ${CartContract.TABLE_NAME}" +
                " WHERE ${CartContract.TABLE_COLUMN_ID}=$id",
        )
    }

    fun findAll(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        writableDatabase.use {
            val cursor = it.rawQuery("select * from ${CartContract.TABLE_NAME}", null)
            while (cursor.moveToNext()) {
                val cartProduct = CartProduct(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2).toBoolean())
                cartProducts.add(cartProduct)
            }
            cursor.close()
        }
        return cartProducts
    }

    fun findWhereById(id: Int): CartProduct? {
        writableDatabase.use {
            val cursor = readableDatabase.rawQuery(
                "select * from ${CartContract.TABLE_NAME}" +
                    " where ${CartContract.TABLE_COLUMN_ID}=$id",
                null,
            )
            while (cursor.moveToNext()) {
                return CartProduct(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2).toBoolean())
            }
            cursor.close()
            return null
        }
    }

    fun findRange(mark: Int, rangeSize: Int): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        writableDatabase.use {
            val cursor = readableDatabase.rawQuery(
                "select * from ${CartContract.TABLE_NAME} " +
                    "limit $rangeSize offset $mark;",
                null,
            )
            while (cursor.moveToNext()) {
                val cartProduct = CartProduct(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2).toBoolean())
                cartProducts.add(cartProduct)
            }
            cursor.close()
        }
        return cartProducts
    }

    fun plusCount(id: Int) {
        var count = 1
        writableDatabase.use {
            val cursor = readableDatabase.rawQuery(
                "select * from ${CartContract.TABLE_NAME} " +
                    "where ${CartContract.TABLE_COLUMN_ID}=$id",
                null,
            )
            while (cursor.moveToNext()) {
                count = cursor.getInt(1)
                count += 1
                cursor.close()
            }
        }
        writableDatabase.execSQL(
            "UPDATE ${CartContract.TABLE_NAME} " +
                "SET ${CartContract.TABLE_COLUMN_COUNT}=$count" +
                " WHERE ${CartContract.TABLE_COLUMN_ID}=$id",
        )
    }

    fun subCount(id: Int) {
        var count = 1
        writableDatabase.use {
            val cursor = readableDatabase.rawQuery(
                "select * from ${CartContract.TABLE_NAME}" +
                    " where ${CartContract.TABLE_COLUMN_ID}=$id",
                null,
            )
            while (cursor.moveToNext()) {
                count = cursor.getInt(1)
                count -= 1
                cursor.close()
            }
        }
        writableDatabase.execSQL(
            "UPDATE ${CartContract.TABLE_NAME}" +
                " SET ${CartContract.TABLE_COLUMN_COUNT}=$count" +
                " WHERE ${CartContract.TABLE_COLUMN_ID}=$id",
        )
    }

    fun findByChecked(): List<CartProduct> {
        val cartProducts = mutableListOf<CartProduct>()
        writableDatabase.use {
            val cursor = readableDatabase.rawQuery(
                "select * from ${CartContract.TABLE_NAME}" +
                    " where ${CartContract.TABLE_COLUMN_CHECK}=1",
                null,
            )
            while (cursor.moveToNext()) {
                val cartProduct = CartProduct(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2).toBoolean())
                cartProducts.add(cartProduct)
            }
            cursor.close()
        }
        return cartProducts
    }

    fun updateCheck(id: Int, checked: Boolean) {
        writableDatabase.execSQL(
            "UPDATE ${CartContract.TABLE_NAME}" +
                " SET ${CartContract.TABLE_COLUMN_CHECK}=$checked" +
                " WHERE ${CartContract.TABLE_COLUMN_ID}=$id",
        )
    }

    fun getSize(mark: Int): Boolean {
        val itemsSize = findAll().size
        return mark in 0 until itemsSize
    }

    companion object {
        private const val TABLE_TITLE = "cart"
    }
}
