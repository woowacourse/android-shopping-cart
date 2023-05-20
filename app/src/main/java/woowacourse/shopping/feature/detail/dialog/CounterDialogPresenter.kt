package woowacourse.shopping.feature.detail.dialog

import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUiModel

class CounterDialogPresenter(
    private val view: CounterDialogContract.View,
    private val cartRepository: CartRepository,
    product: ProductUiModel,
    count: Int? = null
) : CounterDialogContract.Presenter {
    override var product: ProductUiModel = product
        private set

    // 담기 버튼을 누르기 전까지 현재까지 바뀐 값을 담고 있는 변수
    override var changeCount: Int = product.count
        private set

    init {
        count?.let {
            changeCount = it // 만약 개수만 조정하고 담기를 아직 누르지 않은 상태로 화면 회전한 경우를 대비하기 위해
        }
    }

    override fun initPresenter() {
        view.setCountState(changeCount)
    }

    override fun changeCount(count: Int) {
        changeCount = count
    }

    override fun addCart() {
        product.count = changeCount // 적용
        cartRepository.changeCartProductCount(product.toDomain(), changeCount)
        view.notifyChangeApplyCount(changeCount)
        view.exit()
    }
}
