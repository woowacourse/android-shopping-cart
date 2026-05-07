package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.theme.topAppBarColor
import woowacourse.shopping.util.intFormatter
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ProductItem(
    product: Product,
    quantity: Int,
    onClick: () -> Unit,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        shape = RectangleShape,
        modifier = modifier.clickable { onClick() },
    ) {
        Column {
            ProductItemImage(
                product = product,
                quantity = quantity,
                onQuantityIncrease = onQuantityIncrease,
                onQuantityDecrease = onQuantityDecrease,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(6.dp),
            ) {
                ProductItemTitle(
                    productName = product.productName,
                )
                ProductItemPrice(
                    productPrice = product.price.value,
                )
            }
        }
    }
}

@Composable
private fun ProductItemImage(
    product: Product,
    quantity: Int,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.productName,
            modifier = Modifier.fillMaxWidth(),
        )
        if (quantity == 0) {
            AddButton(
                onClick = onQuantityIncrease,
                modifier =
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
            )
        } else {
            QuantitySelector(
                quantity = quantity,
                onDecrease = onQuantityDecrease,
                onIncrease = onQuantityIncrease,
                modifier =
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun ProductItemTitle(
    productName: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = productName,
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@Composable
private fun ProductItemPrice(
    productPrice: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "${intFormatter(productPrice)}원",
        color = topAppBarColor,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        modifier = modifier,
    )
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductItemPreview() {
    ProductItem(
        product =
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                productName = "PET보틀-정사각형(370ml)",
                price = Price(10000),
            ),
        quantity = 1,
        onClick = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
        modifier = Modifier.fillMaxWidth(),
    )
}
