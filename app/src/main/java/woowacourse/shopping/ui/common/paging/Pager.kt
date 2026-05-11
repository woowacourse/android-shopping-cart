package woowacourse.shopping.ui.common.paging

import kotlin.math.ceil

class Pager(
    private val pageSize: Int,
) {
    /**
     * 특정 페이지의 시작 위치(Offset)를 계산합니다.
     */
    fun getOffset(page: Int): Int {
        require(page >= 1) { "$page 번호는 1부터 시작해야 합니다." }
        return (page - 1) * pageSize
    }

    /**
     * 전체 아이템 개수를 바탕으로 총 페이지 수를 계산합니다.
     */
    fun getTotalPages(totalCount: Int): Int {
        require(totalCount >= 0) { "전체 아이템 개수($totalCount)는 0 이상의 정수여야 합니다." }
        return if (totalCount == 0) 1 else ceil(totalCount.toDouble() / pageSize).toInt()
    }

    /**
     * 현재 페이지를 기준으로 다음 페이지가 존재하는지 확인합니다.
     */
    fun hasNext(
        currentPage: Int,
        totalCount: Int,
    ): Boolean = currentPage < getTotalPages(totalCount)

    /**
     * 현재 페이지를 기준으로 이전 페이지가 존재하는지 확인합니다.
     */
    fun hasPrevious(currentPage: Int): Boolean = currentPage > 1

    /**
     * 현재 크기와 전체 아이템 개수를 기준으로 다음 아이템이 있는지 확인합니다.
     */
    fun canLoadMore(
        currentSize: Int,
        totalCount: Int,
    ): Boolean = currentSize < totalCount
}
