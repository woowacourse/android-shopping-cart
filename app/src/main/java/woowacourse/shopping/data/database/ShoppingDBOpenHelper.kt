package woowacourse.shopping.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.data.database.table.SqlCart
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.database.table.SqlRecentProduct
import woowacourse.shopping.data.database.table.SqlTable

class ShoppingDBOpenHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val tables: List<SqlTable> = listOf(SqlProduct, SqlCart, SqlRecentProduct)

    override fun onCreate(db: SQLiteDatabase?) {
        tables.forEach {
            val columns =
                it.scheme.joinToString { scheme -> "${scheme.name} ${scheme.type} ${scheme.constraint}" }
            db?.execSQL("CREATE TABLE ${it.name} ($columns ${it.constraint})")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        tables.reversed().forEach {
            db?.execSQL("DROP TABLE IF EXISTS ${it.name}")
        }
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "shopping"
        const val DB_VERSION = 1
    }
}
