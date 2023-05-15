package woowacourse.shopping.common.model.mapper

interface Mapper<T, R> {
    fun T.toViewModel(): R

    fun R.toDomainModel(): T
}
