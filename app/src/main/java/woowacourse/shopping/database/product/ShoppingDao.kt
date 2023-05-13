package woowacourse.shopping.database.product

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.database.DBContract

class ShoppingDao(
    context: Context
) : SQLiteOpenHelper(context, DBContract.DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${ProductDBContract.TABLE_NAME}(" +
                "${ProductDBContract.PRODUCT_ID} int," +
                "${ProductDBContract.PRODUCT_IMG} text," +
                "${ProductDBContract.PRODUCT_NAME} text," +
                "${ProductDBContract.PRODUCT_PRICE} int" +
                ");"
        )

        db?.execSQL(
            "CREATE TABLE ${ShoppingCartDBContract.TABLE_NAME}(" +
                "${ShoppingCartDBContract.CART_PRODUCT_ID} int" +
                ");"
        )

        db?.execSQL(
            "CREATE TABLE ${RecentViewedDBContract.TABLE_NAME}(" +
                "${RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID} int" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ProductDBContract.TABLE_NAME}")
    }
}
