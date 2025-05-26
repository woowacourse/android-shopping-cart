package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.dto.HistoryProductDto
import woowacourse.shopping.data.entity.HistoryProductEntity

/**
 * # HistoryDao
 * HistoryDao는 검색 기록(search_history) 테이블에 접근하기 위한 DAO입니다.
 *
 * > ⚠️ Warning: 본 DAO에서는 `HistoryProductDto`를 반환하는 메서드가 포함되어 있습니다.
 *
 * ### 📦 HistoryProductDto Description
 * - `HistoryProductEntity` (search_history 테이블의 기본 정보)
 * - `ProductEntity` (products 테이블과 @Relation으로 연결)
 *
 * DTO 내부에서 Room의 `@Relation`을 통해 연관된 `ProductEntity` 정보를 가져옵니다.
 * 단순히 HistoryProductEntity만 필요할 때도 **불필요하게 ProductEntity까지 함께 조회**될 수 있습니다.
 *
 * ### ✔️ GuideLines
 * - **상세 정보가 필요한 경우에만 `HistoryProductDto`를 사용하세요.**
 * - 단순 조회, 삭제 등에는 `HistoryProductEntity`만 사용하는 것이 성능상 유리합니다.
 * - 필요 시 별도의 메서드를 구현하여 사용하는 것을 권장합니다.
 */
@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: HistoryProductEntity)

    @Transaction
    fun insertHistoryWithLimit(
        history: HistoryProductEntity,
        limit: Int,
    ) {
        insertHistory(history)

        val historyCount = getHistoryCount()
        if (historyCount > limit) {
            deleteOldestHistories(historyCount - limit)
        }
    }

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    fun getHistoryProducts(): List<HistoryProductDto>

    @Query("SELECT COUNT(*) FROM search_history")
    fun getHistoryCount(): Int

    @Query("DELETE FROM search_history WHERE productId = (SELECT productId FROM search_history ORDER BY timestamp ASC LIMIT :count)")
    fun deleteOldestHistories(count: Int)

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 1")
    fun getRecentHistoryProduct(): HistoryProductDto?
}
