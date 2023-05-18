package woowacourse.shopping.data.database.contract

object BasketContract {
    internal const val TABLE_NAME = "BASKET_TABLE"
    internal const val BASKET_ID = "basket_id"
    internal const val PRODUCT_ID = "product_id"
    internal const val COLUMN_CREATED = "created"
    internal const val COLUMN_COUNT = "count"

    internal val CREATE_TABLE_QUERY = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            $BASKET_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $PRODUCT_ID INTEGER,
            $COLUMN_CREATED LONG,
            $COLUMN_COUNT INTEGER
        )
    """.trimIndent()

    internal val DELETE_TABLE_QUERY = """
        DROP TABLE IF EXISTS $TABLE_NAME
    """.trimIndent()
}
