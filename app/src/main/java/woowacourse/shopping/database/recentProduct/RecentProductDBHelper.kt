package woowacourse.shopping.database.recentProduct

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.database.cart.CartDBHelper.Companion.DATABASE_NAME
import woowacourse.shopping.database.cart.CartDBHelper.Companion.DATABASE_VERSION

class RecentProductDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${RecentProductConstant.TABLE_NAME} (" +
                "  ${RecentProductConstant.TABLE_COLUMN_ID} int not null," +
                "  ${RecentProductConstant.TABLE_COLUMN_NAME} varchar(100) not null," +
                "  ${RecentProductConstant.TABLE_COLUMN_PRICE} int not null," +
                "  ${RecentProductConstant.TABLE_COLUMN_IMAGE_URL} varchar(255) not null," +
                "  ${RecentProductConstant.TABLE_COLUMN_SAVE_TIME} long not null" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${RecentProductConstant.TABLE_NAME}")
        onCreate(db)
    }
}
