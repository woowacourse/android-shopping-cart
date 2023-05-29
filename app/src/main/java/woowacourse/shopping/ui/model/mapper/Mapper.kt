package woowacourse.shopping.ui.model.mapper

interface Mapper<T, R> {
    fun T.toView(): R

    fun R.toDomain(): T
}
