package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `Increment test`() {
        viewModel.increment()
        assertEquals(viewModel.count.getOrAwaitValue(), 1)
    }

    @Test
    fun `Decrement test`() {
        viewModel.decrement()
        assertEquals(viewModel.count.getOrAwaitValue(), -1)
    }
}
