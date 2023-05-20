package woowacourse.shopping.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract.CartItemEntry
import woowacourse.shopping.database.ProductContract.DATABASE_NAME
import woowacourse.shopping.database.ProductContract.RecentlyViewedProductEntry

class DbHelper private constructor(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, ProductContract.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            execSQL(SQL_CART_CREATE_ENTRIES)
            execSQL(SQL_RECENTLY_VIEWED_PRODUCT_CREATE_ENTRIES)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.run {
            execSQL(SQL_CART_DELETE_ENTRIES)
            execSQL(SQL_RECENTLY_VIEWED_PRODUCT_DELETE_ENTRIES)
        }
        onCreate(db)
    }

    companion object {
        private const val SQL_CART_CREATE_ENTRIES = """
            CREATE TABLE ${CartItemEntry.TABLE_NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY,
            ${CartItemEntry.COLUMN_NAME_PRODUCT_ID} INT,
            ${CartItemEntry.COLUMN_NAME_ADDED_TIME} TEXT,
            ${CartItemEntry.COLUMN_NAME_COUNT} INT
            );
        """

        private const val SQL_CART_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${CartItemEntry.TABLE_NAME};"

        private const val SQL_RECENTLY_VIEWED_PRODUCT_CREATE_ENTRIES = """
            CREATE TABLE ${RecentlyViewedProductEntry.TABLE_NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY,
            ${RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} INTEGER,
            ${RecentlyViewedProductEntry.COLUMN_NAME_VIEWED_TIME} TEXT
            );
        """

        private const val SQL_RECENTLY_VIEWED_PRODUCT_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${RecentlyViewedProductEntry.TABLE_NAME};"

        private lateinit var instance: DbHelper

        private lateinit var dbInstance: SQLiteDatabase

        private fun getInstance(context: Context): DbHelper {
            if (::instance.isInitialized.not()){
                instance = DbHelper(context)
            }
            return instance
        }

        fun getDbInstance(context: Context): SQLiteDatabase {
            if (::dbInstance.isInitialized.not()) {
                dbInstance = getInstance(context).writableDatabase
            }
            return dbInstance
        }
    }
}