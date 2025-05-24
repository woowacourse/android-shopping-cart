package woowacourse.shopping.feature.goods.adapter

enum class ItemViewType(
    val type: Int,
) {
    HISTORY(0),
    GOODS(1),
    LOAD_MORE(2),
    ;

    companion object {
        fun from(type: Int): ItemViewType = entries.first { it.type == type }
    }
}
