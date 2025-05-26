package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.model.entity.CartProductEntity
import woowacourse.shopping.data.model.response.CartProductDetailResponse

/**
 * # CartDao
 * CartDao는 장바구니(cart_products) 테이블에 접근하기 위한 DAO입니다.
 *
 * > ⚠️ Warning: 본 DAO에서는 `CartProductDetailResponse`를 반환하는 메서드가 포함되어 있습니다.
 *
 * ### 📦 CartProductDetailResponse Description
 * - `CartProductEntity` (cart_products 테이블의 기본 정보)
 * - `ProductEntity` (products 테이블과 @Relation으로 연결)
 *
 * Response 내부에서 `@Relation`을 통해 연관된 Product 정보를 자동으로 로드하게 되며,
 * 단순히 CartProductEntity만 필요할 때도 **불필요하게 ProductEntity까지 함께 조회**될 수 있습니다.
 *
 * ### ✔️ GuideLines
 * - **상세 정보가 필요한 경우에만 `CartProductDetailResponse`를 사용하세요.**
 * - 단순 조회, 삭제 등에는 `CartProductEntity`만 사용하는 것이 성능상 유리합니다.
 * - 필요 시 별도의 메서드를 구현하여 사용하는 것을 권장합니다.
 */
@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartProduct(cartProductEntity: CartProductEntity)

    @Query("DELETE FROM cart_products WHERE productId = :productId")
    fun deleteCartProduct(productId: Int)

    @Query("SELECT * FROM cart_products ORDER BY productId ASC LIMIT :size OFFSET (:page - 1) * :size")
    fun getCartProductDetails(
        page: Int,
        size: Int,
    ): List<CartProductDetailResponse>

    @Query("SELECT COUNT(*) FROM cart_products")
    fun getCartItemCount(): Int

    @Query("SELECT (COUNT(*) + :size - 1) / :size FROM cart_products")
    fun getTotalPageCount(size: Int): Int

    @Query("SELECT * FROM cart_products WHERE productId = :productId")
    fun getCartProductDetailById(productId: Int): CartProductDetailResponse?
}
