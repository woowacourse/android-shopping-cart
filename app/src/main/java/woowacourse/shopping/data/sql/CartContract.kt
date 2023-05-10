package woowacourse.shopping.data.sql

object CartContract {
    val TABLE_NAME = "cart_table"
    const val TABLE_COLUMN_CART_ID = "cart_id"
    const val TABLE_COLUMN_PRODUCT_ID = "product_id"
    const val TABLE_COLUMN_PRODUCT_COUNT = "product_count"

    fun allColumn() = listOf(
        TABLE_COLUMN_CART_ID,
        TABLE_COLUMN_PRODUCT_ID,
        TABLE_COLUMN_PRODUCT_COUNT
    )

    fun createSQL(): String {
        return "CREATE TABLE $TABLE_NAME(" +
                "  $TABLE_COLUMN_CART_ID INTEGER primary key autoincrement," +
                "  $TABLE_COLUMN_PRODUCT_ID int," +
                "  $TABLE_COLUMN_PRODUCT_COUNT int" +
                ");"
    }
}