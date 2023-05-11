package woowacourse.shopping.database.cart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_ID
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_IMAGE_URL
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_NAME
import woowacourse.shopping.database.cart.CartConstant.TABLE_COLUMN_PRODUCT_PRICE
import woowacourse.shopping.database.cart.CartConstant.TABLE_NAME

class CartDBHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                "$TABLE_COLUMN_PRODUCT_ID INTEGER PRIMARY KEY," +
                "$TABLE_COLUMN_PRODUCT_NAME TEXT," +
                "$TABLE_COLUMN_PRODUCT_PRICE INTEGER," +
                "$TABLE_COLUMN_PRODUCT_IMAGE_URL TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 4
        const val DATABASE_NAME = "shopping_db"
    }
}
