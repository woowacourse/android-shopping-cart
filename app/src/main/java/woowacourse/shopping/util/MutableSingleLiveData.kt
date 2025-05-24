package woowacourse.shopping.util

/**
 * 출처: MVVM 피드백 강의자료
 */
class MutableSingleLiveData<T> : SingleLiveData<T> {
    constructor() : super()

    constructor(value: T) : super(value)

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    public override fun setValue(value: T) {
        super.setValue(value)
    }
}
