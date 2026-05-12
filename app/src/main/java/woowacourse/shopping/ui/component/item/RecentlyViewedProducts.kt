package woowacourse.shopping.ui.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products

@Composable
fun RecentlyViewedProducts(
    products: Products,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp),
    ) {
        Text(
            text = "최근 본 상품",
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            itemsIndexed(
                items = products.products,
                key = { index, product -> "${product.id}_i$index}" }
            ) { index, item ->
                RecentlyViewedProductItem(
                    product = item,
                    onClick = onClick,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentlyViewedProductsPreview() {
    RecentlyViewedProducts(
        Products(
            products =
                listOf(
                    Product(
                        id = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                        imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                        name = "딸기주스",
                        price = 1000,
                    ),
                    Product(
                        id = "550e8400-e29b-41d4-a716-446655440000",
                        imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                        name = "무엘사",
                        price = 1005,
                    ),
                    Product(
                        id = "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
                        imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                        name = "딸기주스 12개입",
                        price = 1000055,
                    ),
                    Product(
                        id = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                        imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                        name = "딸기주스",
                        price = 1000,
                    ),
                    Product(
                        id = "550e8400-e29b-41d4-a716-446655440000",
                        imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                        name = "무엘사",
                        price = 1005,
                    ),
                    Product(
                        id = "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
                        imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                        name = "딸기주스 12개입",
                        price = 1000055,
                    ),
                    Product(
                        id = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                        imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                        name = "딸기주스",
                        price = 1000,
                    ),
                    Product(
                        id = "550e8400-e29b-41d4-a716-446655440000",
                        imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                        name = "무엘사",
                        price = 1005,
                    ),
                ),
        ),
        onClick = { },
    )
}
