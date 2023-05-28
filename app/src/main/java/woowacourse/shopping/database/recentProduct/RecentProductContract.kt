package woowacourse.shopping.database.recentProduct

import android.provider.BaseColumns

object RecentProductContract : BaseColumns {
    const val TABLE_NAME = "recent_product"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_PRICE = "price"
    const val TABLE_COLUMN_IMAGE_URL = "image_url"
    const val TABLE_COLUMN_SAVE_TIME = "time"

    fun createSQL(): String {
        return "CREATE TABLE $TABLE_NAME (" +
            "  $TABLE_COLUMN_ID int not null," +
            "  $TABLE_COLUMN_NAME varchar(100) not null," +
            "  $TABLE_COLUMN_PRICE int not null," +
            "  $TABLE_COLUMN_IMAGE_URL varchar(255) not null," +
            "  $TABLE_COLUMN_SAVE_TIME long not null" +
            ");"
    }
}
