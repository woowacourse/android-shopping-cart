package woowacourse.shopping.view.uimodel

import androidx.lifecycle.MutableLiveData

class QuantityInfo<T>(
    private var map: Map<T, MutableLiveData<Int>> = mapOf(),
) {
    init {
        require(map.keys.distinct().size == map.size) { ERR_DUPLICATE_KEY }
        require(map.values.distinct().size == map.size) { ERR_DUPLICATE_VALUE }
    }

    operator fun get(key: T): MutableLiveData<Int> {
        return map[key] ?: throw IllegalArgumentException(ERR_CANNOT_FIND_LIVEDATA)
    }

    operator fun plus(other: QuantityInfo<T>): QuantityInfo<T> {
        return QuantityInfo(map + other.map)
    }

    fun clear() {
        map = mapOf()
    }

    fun forEach(block: (T, Int) -> Unit) {
        map.forEach {
            block(it.key, it.value.value ?: throw IllegalStateException(ERR_CANNOT_FIND_LIVEDATA))
        }
    }

    fun <R> map(block: (T, Int) -> R): List<R> {
        return map.map {
            block(it.key, it.value.value ?: throw IllegalStateException(ERR_CANNOT_FIND_LIVEDATA))
        }
    }

    companion object {
        private const val ERR_CANNOT_FIND_LIVEDATA = "라이브데이터를 찾을 수 없습니다"
        private const val ERR_DUPLICATE_KEY = "중복된 키가 있습니다"
        private const val ERR_DUPLICATE_VALUE = "중복된 값이 있습니다"
    }
}
