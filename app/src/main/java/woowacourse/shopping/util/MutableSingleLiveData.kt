package woowacourse.shopping.util

class MutableSingleLiveData<T> : SingleLiveData<T> {
    constructor() : super()

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    public override fun setValue(value: T) {
        super.setValue(value)
    }
}
