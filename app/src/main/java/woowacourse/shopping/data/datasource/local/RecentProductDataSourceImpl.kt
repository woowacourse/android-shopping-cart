package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.dao.RecentlyProductDao
import woowacourse.shopping.data.entity.RecentlyViewedProduct

class RecentProductDataSourceImpl(
    private val dao: RecentlyProductDao,
) : RecentProductDataSource {
    override fun getProducts(): Result<List<RecentlyViewedProduct>> =
        try {
            Result.success(dao.getProducts())
        } catch (e: Exception) {
            Result.failure(Exception("최근 본 상품 목록 조회에 실패했습니다: $e"))
        }

    override fun getMostRecentProduct(): Result<RecentlyViewedProduct?> =
        try {
            Result.success(dao.getMostRecentProduct())
        } catch (e: Exception) {
            Result.failure(Exception("최근 본 상품 목록 조회에 실패했습니다: $e"))
        }

    override fun getOldestProduct(): Result<RecentlyViewedProduct> =
        try {
            Result.success(dao.getOldestProduct())
        } catch (e: Exception) {
            Result.failure(Exception("최근 본 상품 목록 조회에 실패했습니다: $e"))
        }

    override fun getCount(): Result<Int> =
        try {
            Result.success(dao.getCount())
        } catch (e: Exception) {
            Result.failure(Exception("최근 본 상품 목록 개수 조회에 실패했습니다: $e"))
        }

    override fun insert(product: RecentlyViewedProduct): Result<Unit> =
        try {
            Result.success(dao.insertProduct(product))
        } catch (e: Exception) {
            Result.failure(Exception("최근 본 상품 목록 추가에 실패했습니다: $e"))
        }

    override fun delete(product: RecentlyViewedProduct): Result<Unit> =
        try {
            Result.success(dao.delete(product))
        } catch (e: Exception) {
            Result.failure(Exception("최근 본 상품 목록 제거에 실패했습니다: $e"))
        }
}
