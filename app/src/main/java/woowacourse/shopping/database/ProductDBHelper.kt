package woowacourse.shopping.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDBHelper(
    context: Context,
) : SQLiteOpenHelper(
    context,
    ProductContract.DATABASE_NAME,
    null,
    ProductContract.DATABASE_VERSION,
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_PRODUCT_ENTRIES)
        db?.execSQL(SQL_CREATE_RECENTLY_VIEWED_PRODUCTS_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ProductContract.CartEntry.TABLE_NAME};")
        db?.execSQL("DROP TABLE IF EXISTS ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME};")
        onCreate(db)
    }

    companion object {
        private const val SQL_CREATE_PRODUCT_ENTRIES =
            """
                CREATE TABLE ${ProductContract.CartEntry.TABLE_NAME} 
                (${ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID} int PRIMARY KEY
                );
            """

        private const val SQL_CREATE_RECENTLY_VIEWED_PRODUCTS_ENTRIES =
            """
                CREATE TABLE ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME} 
                (${ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} int PRIMARY KEY
                );
            """
    }
}
