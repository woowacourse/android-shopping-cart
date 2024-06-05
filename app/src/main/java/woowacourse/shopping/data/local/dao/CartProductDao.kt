package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.local.entity.CartEntity
import woowacourse.shopping.data.local.entity.CartProductEntity
import woowacourse.shopping.data.local.entity.ProductEntity

@Dao
interface CartProductDao {
    @Query(
        "SELECT productentity.id AS productId, productentity.name, productentity.imgUrl, productentity.price, cartentity.quantity " +
            "FROM productentity LEFT JOIN cartentity ON productentity.id = cartentity.productId " +
            "LIMIT :pageSize OFFSET :offset * :pageSize",
    )
    fun findProductByPaging(
        offset: Int,
        pageSize: Int,
    ): List<CartProductEntity>

    @Query(
        "SELECT productentity.id AS productId, productentity.name, productentity.imgUrl, productentity.price, cartentity.quantity " +
            "FROM cartentity LEFT JOIN productentity ON cartentity.productId = productentity.id " +
            "LIMIT :pageSize OFFSET :offset * :pageSize",
    )
    fun findCartByPaging(
        offset: Int,
        pageSize: Int,
    ): List<CartProductEntity>

    @Query(
        "SELECT COUNT(*) " +
            "FROM cartentity LEFT JOIN productentity ON cartentity.productId = productentity.id ",
    )
    fun getMaxCartCount(): Int

    @Query(
        "SELECT productentity.id AS productId, productentity.name, productentity.imgUrl, productentity.price, cartentity.quantity " +
            "FROM productentity LEFT JOIN cartentity ON productentity.id = cartentity.productId " +
            "WHERE id = :id",
    )
    fun findProductById(id: Long): CartProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(productEntity: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cartEntity: CartEntity)

    @Query("DELETE FROM cartentity WHERE productId = :productId")
    fun deleteCart(productId: Long)
}
