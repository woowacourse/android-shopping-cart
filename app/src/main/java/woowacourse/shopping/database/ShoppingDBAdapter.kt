package woowacourse.shopping.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.database.product.MockProduct
import woowacourse.shopping.database.product.ProductDBContract
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.database.recentviewed.RecentViewedDBContract
import woowacourse.shopping.database.shoppingcart.ShoppingCartDBContract
import woowacourse.shopping.productdetail.ProductUiModel

class ShoppingDBAdapter(
    shoppingDao: ShoppingDao,
) : ShoppingRepository {

    private val shoppingDB: SQLiteDatabase = shoppingDao.writableDatabase
    private val productCursor = shoppingDB.query(
        ProductDBContract.TABLE_NAME,
        arrayOf(
            ProductDBContract.PRODUCT_ID,
            ProductDBContract.PRODUCT_IMG,
            ProductDBContract.PRODUCT_NAME,
            ProductDBContract.PRODUCT_PRICE,
        ),
        null, null, null, null, null
    )

    private val shoppingCartCursor = shoppingDB.query(
        ShoppingCartDBContract.TABLE_NAME,
        arrayOf(
            ShoppingCartDBContract.CART_PRODUCT_ID
        ),
        null, null, null, null, null
    )

    private val recentViewedCursor = shoppingDB.query(
        RecentViewedDBContract.TABLE_NAME,
        arrayOf(
            RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID
        ),
        null, null, null, null, null
    )

    private fun addProduct(product: ProductUiModel) {
        val values = ContentValues().apply {
            put(ProductDBContract.PRODUCT_ID, product.id)
            put(ProductDBContract.PRODUCT_IMG, product.imageUrl)
            put(ProductDBContract.PRODUCT_NAME, product.name)
            put(ProductDBContract.PRODUCT_PRICE, product.price)
        }

        shoppingDB.insert(ProductDBContract.TABLE_NAME, null, values)
    }

    override fun selectProducts(): List<ProductUiModel> {
        val products = mutableListOf<ProductUiModel>()
        while (productCursor.moveToNext()) {
            products.add(productCursor.getProduct())
        }
        return products
    }

    override fun selectShoppingCartProducts(): List<ProductUiModel> {
        val shoppingCartProducts = mutableListOf<ProductUiModel>()

        with(shoppingCartCursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ShoppingCartDBContract.CART_PRODUCT_ID))
                val product = selectProductById(id)

                shoppingCartProducts.add(product)
            }
        }

        return shoppingCartProducts.toList()
    }

    private fun Cursor.getProduct(): ProductUiModel {
        val id = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_ID))
        val img = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_IMG))
        val name = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_NAME))
        val price = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_PRICE))
        return ProductUiModel(id, name, img, price)
    }

    override fun selectProductById(id: Int): ProductUiModel {
        val cursor = shoppingDB.rawQuery(
            "select * from ${ProductDBContract.TABLE_NAME} where ${ProductDBContract.PRODUCT_ID} = ?",
            arrayOf(id.toString())
        ).apply {
            moveToNext()
        }

        val product = cursor.getProduct()
        cursor.close()

        return product
    }

    override fun insertToShoppingCart(id: Int) {
        val values = ContentValues().apply {
            put(ShoppingCartDBContract.CART_PRODUCT_ID, id)
        }

        shoppingDB.insert(ShoppingCartDBContract.TABLE_NAME, null, values)
    }

    override fun deleteFromShoppingCart(id: Int) {
        shoppingDB.delete(
            ShoppingCartDBContract.TABLE_NAME,
            "${ShoppingCartDBContract.CART_PRODUCT_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun insertToRecentViewedProducts(id: Int) {
        val values = ContentValues().apply {
            put(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID, id)
        }

        shoppingDB.insert(RecentViewedDBContract.TABLE_NAME, null, values)
    }

    override fun selectRecentViewedProducts(): List<ProductUiModel> {
        val recentViewedProducts = mutableListOf<ProductUiModel>()

        with(recentViewedCursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID))
                val product = selectProductById(id)

                recentViewedProducts.add(product)
            }
        }

        return recentViewedProducts.toList()
    }

    /**
     * 테스트를 위해 가짜 데이터 insert를 위한 함수
     */
    fun setUpDB() {
        MockProduct.products.forEach {
            addProduct(it)
        }
    }
}
