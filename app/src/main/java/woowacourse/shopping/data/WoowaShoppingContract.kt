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

    object RecentlyViewed : BaseColumns {
        const val TABLE_NAME = "recentlyViewed"
        const val TABLE_COLUMN_PRODUCT_ID = "productId"
        const val TABLE_COLUMN_VIEWED_DATE_TIME = "viewedDateTime"

        const val CREATE_RECENTLY_VIEWED_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_PRODUCT_ID INTEGER UNIQUE," +
            "  $TABLE_COLUMN_VIEWED_DATE_TIME TEXT" +
            ");"
        const val DELETE_RECENTLY_VIEWED_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object ShoppingCart : BaseColumns {
        const val TABLE_NAME = "shoppingCart"
        const val TABLE_COLUMN_PRODUCT_ID = "productId"
        const val TABLE_COLUMN_QUANTITY = "quantity"

        const val CREATE_SHOPPING_CART_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  $TABLE_COLUMN_PRODUCT_ID INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_QUANTITY INTEGER," +
            "  FOREIGN KEY($TABLE_COLUMN_PRODUCT_ID) REFERENCES ${Product.TABLE_NAME}(${BaseColumns._ID}) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");"
        const val DELETE_SHOPPING_CART_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
