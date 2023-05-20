package woowacourse.shopping.data.sql.cart

object CartTableContract {
    const val TABLE_NAME = "cart_table"
    const val TABLE_COLUMN_CART_ID = "cart_id"
    const val TABLE_COLUMN_PRODUCT_ID = "product_id"
    const val TABLE_COLUMN_PRODUCT_COUNT = "product_count"
    const val TABLE_COLUMN_PRODUCT_CHECKED = "product_checked"

    fun createSQL(): String {
        return "CREATE TABLE $TABLE_NAME(" +
            "  $TABLE_COLUMN_CART_ID INTEGER primary key autoincrement," +
            "  $TABLE_COLUMN_PRODUCT_ID int," +
            "  $TABLE_COLUMN_PRODUCT_COUNT int," +
            "  $TABLE_COLUMN_PRODUCT_CHECKED int" +
            ");"
    }
}
