package woowacourse.shopping.presentation.ui.shopping

import android.view.View
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var repository: ShoppingItemsRepository

    @BeforeEach
    fun setup() {
        repository = mockk<ShoppingItemsRepository>()
        viewModel = ShoppingViewModel(repository)
    }

    @Test
    fun `loadProducts should update products LiveData`() {
        // Given
        val products =
            listOf(
                Product.of("Product 1", 1000, "testUrl"),
                Product.of("Product 2", 2000, "testUrl"),
            )

        every { repository.getAllProducts() } returns products

        // When
        viewModel.loadProducts()

        // Then
        assertEquals(products, viewModel.products.value)
    }

    @Test
    fun `updateVisibility should update visibility LiveData`() {
        // When
        viewModel.updateVisibility(View.GONE)

        // Then
        assertEquals(View.GONE, viewModel.visibility.value)
    }

    @Test
    fun `getProducts should return a sublist of productsData`() {
        // Given
        val productsData =
            listOf(
                Product(1, "Product 1", 10.0),
                Product(2, "Product 2", 20.0),
                Product(3, "Product 3", 30.0),
                Product(4, "Product 4", 40.0),
                Product(5, "Product 5", 50.0),
            )

        // When
        val result1 = viewModel.getProducts()
        viewModel.offset = 2
        val result2 = viewModel.getProducts()

        // Then
        assertEquals(productsData.subList(0, 10), result1)
        assertEquals(productsData.subList(2, 12), result2)
    }

    @Test
    fun `init should load products and update visibility`() {
        // Given
        val products =
            listOf(
                Product(1, "Product 1", 10.0),
                Product(2, "Product 2", 20.0),
            )

        // When
        viewModel = ShoppingViewModel(repository)

        // Then
        assertEquals(products, viewModel.products.value)
        assertEquals(View.GONE, viewModel.visibility.value)
    }
}
