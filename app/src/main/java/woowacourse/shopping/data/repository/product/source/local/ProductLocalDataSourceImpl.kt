package woowacourse.shopping.data.repository.product.source.local

import android.content.Context
import android.database.Cursor
import com.example.domain.Product
import woowacourse.shopping.data.database.product.ProductContract
import woowacourse.shopping.data.database.product.ProductDbHelper

class ProductLocalDataSourceImpl(
    context: Context,
) : ProductLocalDataSource {
    private val db = ProductDbHelper(context).writableDatabase

    override fun getAll(): List<Product> {
        val cursor = getCursor()
        val list = mutableListOf<Product>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_ID))
                val imageUrl = getString(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_IMAGE_URL))
                val name = getString(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_NAME))
                val price = getInt(getColumnIndexOrThrow(ProductContract.TABLE_COLUMN_PRICE))

                list.add(
                    Product(
                        id,
                        imageUrl,
                        name,
                        price,
                    ),
                )
            }
        }

        cursor.close()
        return list
    }

    private fun getCursor(): Cursor {
        return db.query(
            ProductContract.TABLE_NAME,
            arrayOf(
                ProductContract.TABLE_COLUMN_ID,
                ProductContract.TABLE_COLUMN_IMAGE_URL,
                ProductContract.TABLE_COLUMN_NAME,
                ProductContract.TABLE_COLUMN_PRICE,
            ),
            "",
            arrayOf(),
            null,
            null,
            "",
        )
    }

    override fun deleteColumn(product: Product) {
        db.delete(
            ProductContract.TABLE_NAME,
            ProductContract.TABLE_COLUMN_ID + "=" + product.id,
            null,
        )
    }
}
