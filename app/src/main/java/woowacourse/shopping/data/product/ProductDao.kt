package woowacourse.shopping.data.product

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.domain.Product

class ProductDao(context: Context) {

    private val db: SQLiteDatabase = ProductDbHelper(context).writableDatabase

    private fun getCursor(selection: String? = ""): Cursor {
        return db.query(
            ProductContract.TABLE_NAME,
            arrayOf(
                ProductContract.TABLE_COLUMN_ID,
                ProductContract.TABLE_COLUMN_IMAGE_URL,
                ProductContract.TABLE_COLUMN_NAME,
                ProductContract.TABLE_COLUMN_PRICE
            ),
            selection, arrayOf(), null, null, ""
        )
    }

    fun getProduct(productId: Int): Product? {
        val cursor = getCursor("${ProductContract.TABLE_COLUMN_ID} = $productId")
        var product: Product? = null

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_ID))
                val imageUrl = getString(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_IMAGE_URL))
                val name = getString(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_NAME))
                val price = getInt(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_PRICE))
                product = Product(id, imageUrl, name, price)
            }
        }
        cursor.close()
        return product
    }

    fun getAll(): List<Product> {
        val cursor = getCursor()
        val list = mutableListOf<Product>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_ID))
                val imageUrl = getString(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_IMAGE_URL))
                val name = getString(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_NAME))
                val price = getInt(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_PRICE))

                list.add(Product(id, imageUrl, name, price))
            }
        }
        cursor.close()
        return list
    }

    fun addColumn(product: Product) {
        val values = ContentValues().apply {
            put(ProductContract.TABLE_COLUMN_ID, product.id)
            put(ProductContract.TABLE_COLUMN_IMAGE_URL, product.imageUrl)
            put(ProductContract.TABLE_COLUMN_NAME, product.name)
            put(ProductContract.TABLE_COLUMN_PRICE, product.price)
        }

        db.insert(ProductContract.TABLE_NAME, null, values)
    }

    fun deleteColumn(product: Product) {
        db.delete(
            ProductContract.TABLE_NAME,
            ProductContract.TABLE_COLUMN_ID + "=" + product.id, null
        )
    }
}
