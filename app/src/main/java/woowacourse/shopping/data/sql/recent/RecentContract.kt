package woowacourse.shopping.data.sql.recent

object RecentContract {
    const val TABLE_NAME = "recent_table"
    const val TABLE_COLUMN_RECENT_PRODUCT_ID = "recent_product_id"
    const val TABLE_COLUMN_DATE_TIME = "recent_date_time"

    fun allColumn() = listOf(
        TABLE_COLUMN_RECENT_PRODUCT_ID,
        TABLE_COLUMN_DATE_TIME
    )

    fun createSQL(): String {
        return "CREATE TABLE $TABLE_NAME(" +
            "  $TABLE_COLUMN_RECENT_PRODUCT_ID int," +
            "  $TABLE_COLUMN_DATE_TIME int" +
            ");"
    }
}
