package woowacourse.shopping.data

import android.provider.BaseColumns

object WoowaShoppingContract : BaseColumns {
    object Product : BaseColumns {
        const val TABLE_NAME = "product"
        const val TABLE_COLUMN_NAME = "name"
        const val TABLE_COLUMN_ITEM_IMAGE = "itemImage"
        const val TABLE_COLUMN_PRICE = "price"

        const val CREATE_PRODUCT_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_NAME TEXT," +
            "  $TABLE_COLUMN_ITEM_IMAGE TEXT," +
            "  $TABLE_COLUMN_PRICE INTEGER" +
            ");"
        const val DELETE_PRODUCT_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
