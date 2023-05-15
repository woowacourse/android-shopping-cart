package woowacourse.shopping.data.database.contract

import android.provider.BaseColumns

object RecentProductContract {
    internal const val TABLE_NAME = "RECENT_PRODUCT_TABLE"

    internal val CREATE_TABLE_QUERY = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${ProductContract.TABLE_NAME}${BaseColumns._ID} INTEGER
        )
    """.trimIndent()

    internal val DELETE_TABLE_QUERY = """
        DROP TABLE IF EXISTS $TABLE_NAME
    """.trimIndent()
}
