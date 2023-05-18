package woowacourse.shopping.data.database.contract

import android.provider.BaseColumns

object BasketContract {
    internal const val TABLE_NAME = "BASKET_TABLE"
    internal const val BASKET_COUNT = "basket_count"

    internal val CREATE_TABLE_QUERY = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${ProductContract.TABLE_NAME}${BaseColumns._ID} INTEGER,
            $BASKET_COUNT INTEGER
        )
    """.trimIndent()

    internal val DELETE_TABLE_QUERY = """
        DROP TABLE IF EXISTS $TABLE_NAME
    """.trimIndent()
}
