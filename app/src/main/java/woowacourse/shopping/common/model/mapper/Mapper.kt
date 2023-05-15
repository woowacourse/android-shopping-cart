package woowacourse.shopping.common.model.mapper

interface Mapper<T, R> {
    fun T.toView(): R

    fun R.toDomain(): T
}
