package woowacourse.shopping.model.db.cart

class CartRepositoryImpl private constructor(private val cartDao: CartDao) : CartRepository {
    override fun insert(cart: Cart): Long = cartDao.insert(cart)

    override fun find(id: Long): Cart = cartDao.find(id)

    override fun findAll(): List<Cart> = cartDao.findAll()

    override fun delete(id: Long) = cartDao.delete(id)

    override fun deleteByProductId(productId: Long) = cartDao.deleteByProductId(productId)

    override fun deleteAll() = cartDao.deleteAll()

    override fun itemSize(): Int = cartDao.itemSize()

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Cart> = cartDao.getProducts(page, pageSize)

    override fun plusQuantityByProductId(productId: Long) = cartDao.plusQuantityByProductId(productId)

    override fun minusQuantityByProductId(productId: Long) = cartDao.minusQuantityByProductId(productId)

    companion object {
        private var instance: CartRepository? = null

        fun get(cartDao: CartDao): CartRepository {
            return instance ?: synchronized(this) {
                instance ?: CartRepositoryImpl(cartDao).also { instance = it }
            }
        }
    }
}
