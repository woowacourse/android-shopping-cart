package woowacourse.shopping.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.MockProductRepository
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.domain.repository.ProductRepository

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ProductRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        repository = MockProductRepository()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `offset을_기준으로_상품_리스트를_요청하면_상품_목록을_정해진_개수만큼_반환해야_한다`(){
        viewModel.loadPagingProduct(3)
        val result = viewModel.products.getOrAwaitValue()
        assertThat(result.size).isEqualTo(3)
    }

//    @Test
//    fun `상품아이디로_상품을_요청하면_아이디와_일치하는_상품_목록을_반환해야_한다`(){
//
//    }
//
//    @Test
//    fun `상품_목록을_추가하면_전체_상품에_상품이_추가되어야_한다`(){
//
//    }
//
//    @Test
//    fun `off을_기준으로_장바구니_리스트를_요청하면_장바구니_정해진_개수만큼_반환해야_한다`(){
//
//    }
//
//    @Test
//    fun `장바구니_id로_장바구니_목록을_삭제하면_전체_상품에서_해당_id와_일치하는_아이템이_삭제되어야_한다`(){
//
//    }

}
