package woowacourse.shopping.database.recentlyviewedproduct

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.database.ProductDBHelper
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository

class RecentlyViewedProductRepositoryImpl(
    context: Context,
    private val productRepository: ProductRepository,
) : RecentlyViewedProductRepository {
    private val db = ProductDBHelper(context).writableDatabase

    override fun findAll(): List<RecentlyViewedProduct> {
        val products = mutableListOf<RecentlyViewedProduct>()
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME}",
            null,
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            productRepository.findById(id)?.let {
                products.add(
                    RecentlyViewedProduct(it.id, it.imageUrl, it.name, it.price),
                )
            }
        }

        cursor.close()
        return products
    }

    override fun save(product: RecentlyViewedProduct) {
        deleteExistingItem(product)
        insertItem(product)
    }

    private fun deleteExistingItem(product: RecentlyViewedProduct) {
        val selection = "${ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(product.id.toString())
        db.delete(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, selection, selectionArgs)
    }

    private fun insertItem(product: RecentlyViewedProduct) {
        val value = ContentValues().apply {
            put(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID, product.id)
        }
        db.insert(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, null, value)
    }
}
