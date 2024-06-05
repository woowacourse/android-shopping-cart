package study.repository

import study.ProductDataSourceStudy
import study.ShoppingCartProductIdDataSourceStudy
import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.domain.model.Product

class DefaultShoppingProductRepositoryStudy(
    private val productsSource: ProductDataSourceStudy,
    private val cartSource: ShoppingCartProductIdDataSourceStudy,
) : ShoppingProductsRepositoryStudy {
    override fun loadAllProducts(page: Int): List<Product> {
        val productsData = productsSource.findByPaged(page)

        return productsData.map { productData ->
            productData.toDomain(productQuantity(productData.id))
        }
    }

    override fun loadProductsInCart(page: Int): List<Product> {
        val allProductIdsInCart = cartSource.loadPaged(page)
        return allProductIdsInCart.map { productIdsCountData ->
            productsSource.findById(productIdsCountData.productId).toDomain(productIdsCountData.quantity)
        }
    }

    override fun loadProduct(id: Long): Product = productsSource.findById(id).toDomain(productQuantity(id))

    override fun isFinalPage(page: Int): Boolean = productsSource.isFinalPage(page)

    override fun isCartFinalPage(page: Int): Boolean = cartSource.isFinalPage(page)

    override fun shoppingCartProductQuantity(): Int = cartSource.loadAll().sumOf { it.quantity }

    private fun productQuantity(productId: Long): Int {
        return try {
            cartSource.findByProductId(productId)?.quantity ?: 0
        } catch (e: NoSuchElementException) {
            0
        }
    }

    override fun increaseShoppingCartProduct(id: Long) {
        if (cartSource.findByProductId(id) == null) {
            addShoppingCartProduct(id)
            return
        }

        cartSource.plusProductsIdCount(id)
    }

    override fun decreaseShoppingCartProduct(id: Long) {
        val data = cartSource.findByProductId(id) ?: return

        if (data.quantity == 1) {
            removeShoppingCartProduct(id)
            return
        }

        cartSource.minusProductsIdCount(id)
    }

    override fun addShoppingCartProduct(id: Long) {
        cartSource.addedNewProductsId(ProductIdsCountData(id, FIRST_QUANTITY))
    }

    override fun removeShoppingCartProduct(id: Long) {
        cartSource.removedProductsId(id)
    }

    // async function with callback
    override fun loadAllProductsAsync(
        page: Int,
        callback: (List<Product>) -> Unit,
    ) {
        productsSource.findByPagedAsync(page) { productsData ->
            val products =
                productsData.map { productData ->
                    productData.toDomain(productQuantity(productData.id))
                }
            callback(products)
        }
    }

    override fun loadProductsInCartAsync(
        page: Int,
        callback: (List<Product>) -> Unit,
    ) {
        cartSource.loadPagedAsync(page = page) {
            it.map { productIdsCountData ->
                productsSource.findById(productIdsCountData.productId).toDomain(productIdsCountData.quantity)
            }.let(callback)
        }
    }

    override fun loadProductAsync(
        id: Long,
        callback: (Product) -> Unit,
    ) {
        productsSource.findByIdAsync(id) { productData ->
            val product = productData.toDomain(productQuantity(id))
            callback(product)
        }
    }

    override fun isFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    ) {
        productsSource.isFinalPageAsync(page, callback)
    }

    override fun isCartFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    ) {
        cartSource.isFinalPageAsync(page, callback)
    }

    override fun shoppingCartProductQuantityAsync(callback: (Int) -> Unit) {
        cartSource.loadAllAsync { productIdsCountData ->
            val quantity = productIdsCountData.sumOf { it.quantity }
            callback(quantity)
        }
    }

    override fun increaseShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    ) {
        cartSource.findByProductIdAsync(id) {
            if (it == null) {
                addShoppingCartProductAsync(id) {
                    callback(true)
                }
                return@findByProductIdAsync
            }

            cartSource.plusProductsIdCountAsync(id) {
                callback(true)
            }
        }
    }

    override fun decreaseShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    ) {
        cartSource.findByProductIdAsync(id) {
            if (it == null) {
                callback(false)
                return@findByProductIdAsync
            }

            if (it.quantity == 1) {
                removeShoppingCartProductAsync(id) {
                    callback(true)
                }
                return@findByProductIdAsync
            }

            cartSource.minusProductsIdCountAsync(id) { callback(true) }
        }
    }

    override fun addShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    ) {
        cartSource.addedNewProductsIdAsync(ProductIdsCountData(id, FIRST_QUANTITY)) {
            callback(true)
        }
    }

    override fun removeShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    ) {
        cartSource.removedProductsIdAsync(id) {
            callback(true)
        }
    }

    override fun loadAllProductsAsyncResult(
        page: Int,
        callback: (Result<List<Product>>) -> Unit,
    ) {
        productsSource.findByPagedAsyncResult(page) { result ->
            val products =
                result.map { productsData ->
                    productsData.map { productData ->
                        productData.toDomain(productQuantity(productData.id))
                    }
                }
            callback(products)
        }
    }

    override fun loadProductsInCartAsyncResult(
        page: Int,
        callback: (Result<List<Product>>) -> Unit,
    ) {
        cartSource.loadPagedAsyncResult(page = page) { result ->
            val products =
                result.map { productIdsCountDatas ->
                    productIdsCountDatas.map { productIdsCountData ->
                        productsSource.findById(productIdsCountData.productId).toDomain(productIdsCountData.quantity)
                    }
                }

            callback(products)
        }
    }

    override fun loadProductAsyncResult(
        id: Long,
        callback: (Result<Product>) -> Unit,
    ) {
        productsSource.findByIdAsyncResult(id) { result ->
            val product =
                result.map { productData ->
                    productData.toDomain(productQuantity(id))
                }

            callback(product)
        }
    }

    override fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    ) {
        productsSource.isFinalPageAsyncResult(page, callback)
    }

    override fun isCartFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    ) {
        cartSource.isFinalPageAsyncResult(page, callback)
    }

    override fun shoppingCartProductQuantityAsyncResult(callback: (Result<Int>) -> Unit) {
        cartSource.loadAllAsyncResult { result ->
            val quantity =
                result.map { productIdsCountData ->
                    productIdsCountData.sumOf { it.quantity }
                }

            callback(quantity)
        }
    }

    override fun increaseShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        cartSource.plusProductsIdCountAsyncResult(id) { result ->
            callback(result)
        }
    }

    override fun putItemInCartAsyncResult(
        id: Long,
        quantity: Int,
        callback: (Result<Unit>) -> Unit,
    ) {
        cartSource.findByProductIdAsyncResultNonNull(id) { result ->
            result.onSuccess {
                cartSource.plusProductIdCountAsyncResult(id, quantity) {
                    callback(it)
                }
            }
            result.onFailure {
                cartSource.addedNewProductsIdAsyncResult(ProductIdsCountData(id, quantity)) {
                    callback(Result.success(Unit))
                }
            }
        }
    }

    override fun decreaseShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        cartSource.findByProductIdAsyncResult(id) {
            it.onSuccess { data ->
                if (data!!.quantity == 1) {
                    cartSource.removedProductsIdAsyncResult(id) {
                        callback(it)
                    }
                } else {
                    cartSource.minusProductsIdCountAsyncResult(id) {
                        callback(it)
                    }
                }
            }
                .onFailure {
                    callback(Result.failure(it))
                }
        }
    }

    override fun addShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Long>) -> Unit,
    ) {
        cartSource.addedNewProductsIdAsyncResult(ProductIdsCountData(id, FIRST_QUANTITY)) {
            callback(it)
        }
    }

    override fun removeShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        cartSource.removedProductsIdAsyncResult(id) {
            callback(it)
        }
    }

    companion object {
        private const val FIRST_QUANTITY = 1
        private const val TAG = "DefaultShoppingProductR"
    }
}
