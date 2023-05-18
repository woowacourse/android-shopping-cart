package woowacourse.shopping.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.data.database.contract.BasketContract
import woowacourse.shopping.data.database.contract.ProductContract
import woowacourse.shopping.data.database.contract.RecentProductContract

const val DATABASE_NAME = "ShoppingDatabase.db"
const val DATABASE_VERSION = 14

class ShoppingDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ProductContract.CREATE_TABLE_QUERY)
        db?.execSQL(RecentProductContract.CREATE_TABLE_QUERY)
        db?.execSQL(BasketContract.CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db?.execSQL(ProductContract.DELETE_TABLE_QUERY)
        db?.execSQL(RecentProductContract.DELETE_TABLE_QUERY)
        db?.execSQL(BasketContract.DELETE_TABLE_QUERY)
        onCreate(db)
    }
}
