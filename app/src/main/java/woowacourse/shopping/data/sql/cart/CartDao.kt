package woowacourse.shopping.data.sql.cart

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import woowacourse.shopping.data.model.CartEntity
import woowacourse.shopping.data.model.CartEntity.Companion.CHECK
import woowacourse.shopping.data.model.CartEntity.Companion.NON_CHECK

class CartDao(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CartTableContract.createSQL())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${CartTableContract.TABLE_NAME}")
        onCreate(db)
    }

    fun selectAll(): List<CartProduct> {
        val cursor = readableDatabase.query(
            CartTableContract.TABLE_NAME,
            arrayOf(
                CartTableContract.TABLE_COLUMN_CART_ID,
                CartTableContract.TABLE_COLUMN_PRODUCT_ID,
                CartTableContract.TABLE_COLUMN_PRODUCT_COUNT,
                CartTableContract.TABLE_COLUMN_PRODUCT_CHECKED
            ),
            null, null, null, null, null
        )

        val cart = mutableListOf<CartProduct>()
        while (cursor.moveToNext()) {
            val data = CartEntity(
                cursor.getLong(cursor.getColumnIndexOrThrow(CartTableContract.TABLE_COLUMN_CART_ID)),
                cursor.getLong(cursor.getColumnIndexOrThrow(CartTableContract.TABLE_COLUMN_PRODUCT_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(CartTableContract.TABLE_COLUMN_PRODUCT_COUNT)),
                cursor.getInt(cursor.getColumnIndexOrThrow(CartTableContract.TABLE_COLUMN_PRODUCT_CHECKED))
            )
            val product: Product = productsDatasource.find { it.id == data.productId } ?: continue
            cart.add(CartProduct(data.cartId, product, data.count, data.checked))
        }

        cursor.close()
        return cart
    }

    fun insertProduct(product: Product, count: Int = 1) {
        if (count <= ZERO) return
        val values = ContentValues().apply {
            put(CartTableContract.TABLE_COLUMN_PRODUCT_ID, product.id)
            put(CartTableContract.TABLE_COLUMN_PRODUCT_COUNT, count) // 일단 1로 고정
            put(CartTableContract.TABLE_COLUMN_PRODUCT_CHECKED, ZERO) // 처음은 체크 안함
        }
        writableDatabase.insert(CartTableContract.TABLE_NAME, null, values)
    }

    fun deleteCartProduct(cartProduct: CartProduct) {
        val selection = "${CartTableContract.TABLE_COLUMN_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf("${cartProduct.product.id}")
        writableDatabase.delete(CartTableContract.TABLE_NAME, selection, selectionArgs)
    }

    fun deleteAllCheckedCartProduct() {
        val selection = "${CartTableContract.TABLE_COLUMN_PRODUCT_CHECKED} = ?"
        val selectionArgs = arrayOf("$CHECK")
        writableDatabase.delete(CartTableContract.TABLE_NAME, selection, selectionArgs)
    }

    fun updateCartProductCount(product: Product, newCount: Int) {
        val findCartProduct =
            selectAll().find { it.product.id == product.id } ?: return insertProduct(
                product,
                newCount
            )

        if (newCount <= ZERO) return deleteCartProduct(findCartProduct)

        val updateSql = "UPDATE ${CartTableContract.TABLE_NAME} " +
            "SET ${CartTableContract.TABLE_COLUMN_PRODUCT_COUNT}=$newCount " +
            "WHERE ${CartTableContract.TABLE_COLUMN_PRODUCT_ID}=${product.id}"
        writableDatabase.execSQL(updateSql)
    }

    fun updateCartProductChecked(product: Product, checked: Boolean) {
        val findCartProduct =
            selectAll().find { it.product.id == product.id } ?: return
        val checkedState = if (checked) CHECK else NON_CHECK

        val updateSql = "UPDATE ${CartTableContract.TABLE_NAME} " +
            "SET ${CartTableContract.TABLE_COLUMN_PRODUCT_CHECKED}=$checkedState " +
            "WHERE ${CartTableContract.TABLE_COLUMN_CART_ID}=${findCartProduct.cartId}"
        writableDatabase.execSQL(updateSql)
    }

    fun updateAllChecked(cartIds: List<Long>, checked: Boolean) {
        val checkedState = if (checked) CHECK else NON_CHECK

        val updateSql = "UPDATE ${CartTableContract.TABLE_NAME} " +
            "SET ${CartTableContract.TABLE_COLUMN_PRODUCT_CHECKED}=$checkedState " +
            "WHERE ${CartTableContract.TABLE_COLUMN_CART_ID} IN ${
            cartIds.joinToString(
                ", ",
                "(",
                ")"
            )
            }"
        writableDatabase.execSQL(updateSql)
    }

    companion object {
        private const val DB_NAME = "cart_db"
        private const val VERSION = 4
        private const val ZERO = 0
    }
}
