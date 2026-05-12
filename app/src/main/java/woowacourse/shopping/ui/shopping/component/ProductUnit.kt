package woowacourse.shopping.ui.shopping.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.ShoppingTypography
import woowacourse.shopping.ui.common.component.cartcontrol.AddToCartButton
import woowacourse.shopping.ui.common.component.cartcontrol.QuantityStepper
import woowacourse.shopping.ui.shopping.ShoppingProductUiState
import woowacourse.shopping.ui.theme.ShoppingColors

@SuppressLint("DefaultLocale")
@Composable
fun ProductUnit(
    product: ShoppingProductUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    val price = product.product.price.value
    val formatted = String.format("%,d", price)
    Column(
        modifier =
            modifier
                .width(154.dp)
                .height(206.dp)
                .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier.size(154.dp),
        ) {
            AsyncImage(
                model = product.product.imageUrl,
                contentDescription = stringResource(R.string.content_description_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            if (product.isInCart) {
                QuantityStepper(
                    quantity = product.quantity,
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(8.dp),
                    onIncreaseQuantity = onIncreaseQuantity,
                    onDecreaseQuantity = onDecreaseQuantity,
                )
            } else {
                AddToCartButton(
                    isEnabled = true,
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                    onAddToCart = onAddToCart,
                )
            }
        }
        Spacer(Modifier.size(6.dp))
        Text(
            text = product.product.name,
            color = Color.Black,
            style = ShoppingTypography.productName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 6.dp, end = 9.dp),
        )
        Text(
            text = stringResource(R.string.price_format, formatted),
            color = ShoppingColors.Gray4,
            style = ShoppingTypography.productPrice,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Composable
@Preview(showBackground = true, name = "장바구니 담기 버튼")
private fun ProductUnitAddToCartPreview() {
    ProductUnit(
        product = ShoppingProductUiState(product = InMemoryProductRepository.APPLE, quantity = 0),
        onClick = {},
        onAddToCart = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}

@Composable
@Preview(showBackground = true, name = "수량 스테퍼")
private fun ProductUnitQuantityPreview() {
    ProductUnit(
        product = ShoppingProductUiState(product = InMemoryProductRepository.APPLE, quantity = 2),
        onClick = {},
        onAddToCart = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}

@Composable
@Preview(showBackground = true, name = "긴 이름 상품")
private fun ProductUnitLongNamePreview() {
    ProductUnit(
        product =
            ShoppingProductUiState(
                product =
                    Product(
                        name = "정말정말 엄청나게 긴 이름을 가지고 있는 상품",
                        price = Money(1000),
                        imageUrl = "",
                    ),
                quantity = 1,
            ),
        onClick = {},
        onAddToCart = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
