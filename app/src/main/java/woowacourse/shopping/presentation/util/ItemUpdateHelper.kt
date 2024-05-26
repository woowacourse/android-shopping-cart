package woowacourse.shopping.presentation.util

import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 의 데이터 업데이트를 도와주는 클래스 (더 좋은 네이밍이 뭐가 있을까나)
 *
 * @param T RecyclerView 에서 사용하는 아이템 타입
 * @property adapter RecyclerView Adapter
 * @property areItemsTheSame 아이템이 같은지 비교하는 함수
 * @property areContentsTheSame areItemsTheSame 가 true 일 때, 내용이 같은지 비교하는 함수
 */
class ItemUpdateHelper<T>(
    private val adapter: RecyclerView.Adapter<*>,
    private val areItemsTheSame: (T, T) -> Boolean,
    private val areContentsTheSame: (T, T) -> Boolean,
) {
    /**
     * RecyclerView 의 데이터를 업데이트한다.
     *
     * @param oldList 이전 데이터 리스트
     * @param newList 새로운 데이터 리스트
     *
     * * @sample
     * ```kotlin
     * private val updateHelper = ItemUpdateHelper(..)
     * // Adapter
     *fun updateProducts(newProducts: List<ShoppingUiModel>) {
     *      val oldProducts = products.toList() // 방어적 복사
     *      products = newProducts
     *      updateHelper.update(oldProducts, newProducts)
     * }
     * ```
     */
    fun update(
        oldList: List<T>,
        newList: List<T>,
    ) {
        if (oldList === newList) return
        if (oldList.isEmpty()) return adapter.notifyItemRangeInserted(0, newList.size)
        if (newList.isEmpty()) return adapter.notifyItemRangeRemoved(0, oldList.size)

        val originalSize = oldList.size
        val newSize = newList.size

        // Case 1: Size 가 같은 경우 - 각 아이템을 비교하여 변경된 아이템만 업데이트
        if (originalSize == newSize) {
            return newList.forEachIndexed { index, newItem ->
                if (shouldUpdate(oldList[index], newItem)) adapter.notifyItemChanged(index)
            }
        }

        // Case 2: NewList 가 더 작은 경우
        if (newSize < originalSize) {
            // 기존 리스트 아이템 삭제
            adapter.notifyItemRangeRemoved(newSize, originalSize - newSize)
            // 변경된 아이템 업데이트
            return newList.forEachIndexed { index, newItem ->
                if (shouldUpdate(oldList[index], newItem)) adapter.notifyItemChanged(index)
            }
        }

        // case 3) NewList 가 더 큰 경우
        // 기존 리스트 아이템을 업데이트하고, 새로운 아이템을 추가
        newList.forEachIndexed { index, newItem ->
            // 새로운 아이템 추가
            if (index >= originalSize) {
                return@forEachIndexed adapter.notifyItemInserted(index)
            }
            // 변경된 아이템 업데이트
            if (shouldUpdate(newItem, oldList[index])) {
                adapter.notifyItemChanged(index)
            }
        }
    }

    private fun shouldUpdate(
        oldItem: T,
        newItem: T,
    ): Boolean {
        return !areItemsTheSame(oldItem, newItem) || !areContentsTheSame(oldItem, newItem)
    }
}
