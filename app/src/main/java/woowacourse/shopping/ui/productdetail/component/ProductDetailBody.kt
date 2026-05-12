package woowacourse.shopping.ui.productdetail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.ShoppingTypography
import woowacourse.shopping.ui.common.component.cartcontrol.QuantityStepper

@Composable
fun ProductDetailBody(
    product: Product,
    quantity: Int,
    modifier: Modifier = Modifier,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = stringResource(R.string.content_description_image),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(360.dp),
            contentScale = ContentScale.Crop,
        )
        ProductLabel(
            product = product,
            quantity = quantity,
            onIncreaseQuantity = onIncreaseQuantity,
            onDecreaseQuantity = onDecreaseQuantity,
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ProductLabel(
    product: Product,
    quantity: Int,
    modifier: Modifier = Modifier,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    val totalPrice =
        if (quantity == 0) {
            product.price.value
        } else {
            product.price.value * quantity
        }
    val formatted = String.format("%,d", totalPrice)

    Column(modifier = modifier) {
        Text(
            text = product.name,
            style = ShoppingTypography.detailTitle,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 18.dp, top = 16.dp, bottom = 17.dp),
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.price_format, formatted),
                style = ShoppingTypography.detailPrice,
                color = Color.Black,
            )
            if (quantity > 0) {
                QuantityStepper(
                    quantity = quantity,
                    onIncreaseQuantity = onIncreaseQuantity,
                    onDecreaseQuantity = onDecreaseQuantity,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, name = "상품 유닛")
private fun ProductUnitPreview() {
    ProductDetailBody(
        product = InMemoryProductRepository.APPLE,
        quantity = 0,
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}

@Composable
@Preview(showBackground = true, name = "상품 이름만")
private fun ProductLabelPreview() {
    ProductLabel(
        product = InMemoryProductRepository.APPLE,
        quantity = 2,
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
