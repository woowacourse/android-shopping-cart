package woowacourse.shopping.data.database.contract

object BasketContract {
    internal const val TABLE_NAME = "BASKET_TABLE"
    internal const val BASKET_ID = "basket_id"
    internal const val PRODUCT_ID = "product_id"
    internal const val COLUMN_CREATED = "created"
    internal const val COLUMN_COUNT = "count"
    internal const val COLUMN_CHECKED = "checked"

    internal val CREATE_TABLE_QUERY = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            $BASKET_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $PRODUCT_ID INTEGER,
            $COLUMN_CREATED LONG,
            $COLUMN_COUNT INTEGER,
            $COLUMN_CHECKED INTEGER DEFAULT 1
        )
    """.trimIndent()

    internal val DELETE_TABLE_QUERY = """
        DROP TABLE IF EXISTS $TABLE_NAME
    """.trimIndent()
}
