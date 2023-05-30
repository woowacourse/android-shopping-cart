package woowacourse.shopping.data.database.product

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.domain.Product
import woowacourse.shopping.data.mock.getProductDummyData

class ProductDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE ${ProductContract.TABLE_NAME} (
                    ${ProductContract.TABLE_COLUMN_ID} INTEGER,
                    ${ProductContract.TABLE_COLUMN_IMAGE_URL} TEXT,
                    ${ProductContract.TABLE_COLUMN_NAME} TEXT, 
                    ${ProductContract.TABLE_COLUMN_PRICE} INTEGER
                )
            """.trimIndent(),
        )

        val products = getProductDummyData()
        products.forEach { product ->
            val value = makeValue(product)
            db.insert(ProductContract.TABLE_NAME, null, value)
        }
    }

    private fun makeValue(product: Product): ContentValues {
        return ContentValues().apply {
            put(ProductContract.TABLE_COLUMN_ID, product.id)
            put(ProductContract.TABLE_COLUMN_IMAGE_URL, product.imageUrl)
            put(ProductContract.TABLE_COLUMN_NAME, product.name)
            put(ProductContract.TABLE_COLUMN_PRICE, product.price)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductContract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "product.db"
    }
}
