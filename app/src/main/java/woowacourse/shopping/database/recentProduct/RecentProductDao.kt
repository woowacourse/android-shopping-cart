package woowacourse.shopping.database.recentProduct

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.domain.model.Product
import woowacourse.shopping.database.cart.CartDao.Companion.DATABASE_NAME
import woowacourse.shopping.database.cart.CartDao.Companion.DATABASE_VERSION

class RecentProductDao(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            RecentProductContract.createSQL(),
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${RecentProductContract.TABLE_NAME}")
        onCreate(db)
    }

    fun insert(product: Product) {
        findById(product.id)?.let {
            delete(it.id)
        }
        val values = ContentValues()
        values.put(RecentProductContract.TABLE_COLUMN_ID, product.id)
        values.put(RecentProductContract.TABLE_COLUMN_NAME, product.name)
        values.put(RecentProductContract.TABLE_COLUMN_PRICE, product.price)
        values.put(RecentProductContract.TABLE_COLUMN_IMAGE_URL, product.imageUrl)
        values.put(
            RecentProductContract.TABLE_COLUMN_SAVE_TIME,
            System.currentTimeMillis(),
        )
        writableDatabase.insert(RecentProductContract.TABLE_NAME, null, values)
    }

    fun getRecent(size: Int): List<Product> {
        val query =
            "SELECT * FROM ${RecentProductContract.TABLE_NAME} ORDER BY ${RecentProductContract.TABLE_COLUMN_SAVE_TIME} DESC LIMIT $size"
        val cursor = readableDatabase.rawQuery(query, null)
        val products = mutableListOf<Product>()
        while (cursor.moveToNext()) {
            val id =
                cursor.getInt(cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_ID))
            val name =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_NAME),
                )
            val price =
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRICE),
                )
            val imageUrl =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_IMAGE_URL),
                )
            products.add(Product(id, name, price, imageUrl))
        }
        cursor.close()
        return products
    }

    fun findById(id: Int): Product? {
        val query =
            "SELECT * FROM ${RecentProductContract.TABLE_NAME} WHERE ${RecentProductContract.TABLE_COLUMN_ID} = $id LIMIT 1"
        val cursor = readableDatabase.rawQuery(query, null)
        var product: Product? = null
        if (cursor.count > 0) {
            cursor.moveToFirst()
            val id =
                cursor.getInt(cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_ID))
            val name =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_NAME),
                )
            val price =
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRICE),
                )
            val imageUrl =
                cursor.getString(
                    cursor.getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_IMAGE_URL),
                )
            product = Product(id, name, price, imageUrl)
        }
        cursor.close()
        return product
    }

    fun delete(id: Int) {
        val whereClause = "${RecentProductContract.TABLE_COLUMN_ID} = ?"
        val whereArgs = arrayOf(id.toString())
        writableDatabase.delete(
            RecentProductContract.TABLE_NAME,
            whereClause,
            whereArgs,
        )
    }
}
