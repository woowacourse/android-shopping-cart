package woowacourse.shopping.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.productdetail.ProductUiModel

class ShoppingDBAdapter(productDao: ProductDao) {

    private val writableDB: SQLiteDatabase = productDao.writableDatabase
    private val productCursor = writableDB.query(
        ProductDBContract.TABLE_NAME,
        arrayOf(
            ProductDBContract.PRODUCT_ID,
            ProductDBContract.PRODUCT_IMG,
            ProductDBContract.PRODUCT_NAME,
            ProductDBContract.PRODUCT_PRICE,
        ),
        null, null, null, null, null
    )

    fun addProduct(product: ProductUiModel) {
        val values = ContentValues().apply {
            put(ProductDBContract.PRODUCT_ID, product.id)
            put(ProductDBContract.PRODUCT_IMG, product.imageUrl)
            put(ProductDBContract.PRODUCT_NAME, product.name)
            put(ProductDBContract.PRODUCT_PRICE, product.price)
        }

        writableDB.insert(ProductDBContract.TABLE_NAME, null, values)
    }

    fun loadProduct(): List<ProductUiModel> {
        val products = mutableListOf<ProductUiModel>()
        while (productCursor.moveToNext()) {
            products.add(productCursor.getProduct())
        }
        return products
    }

    fun Cursor.getProduct(): ProductUiModel {
        val id = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_ID))
        val img = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_IMG))
        val name = getString(getColumnIndexOrThrow(ProductDBContract.TABLE_NAME))
        val price = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_PRICE))
        return ProductUiModel(id, name, img, price)
    }
}
