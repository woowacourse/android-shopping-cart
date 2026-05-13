package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.ui.component.AddCircleButton
import woowacourse.shopping.ui.component.AmountController

@Composable
fun ProductCard(
    product: ProductUiModel,
    onClickItem: () -> Unit,
    onClickMinus: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable(onClick = onClickItem),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "${product.name} 이미지 입니다용",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            if (product.showAmountController) {
                AmountController(
                    amount = product.cartAmount,
                    onClickMinus = onClickMinus,
                    onClickAdd = onClickAdd,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                        .fillMaxWidth(),
                )
            } else {
                AddCircleButton(
                    onClickAdd = onClickAdd,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                )
            }
        }
        ProductInfoText(
            name = product.name,
            price = product.price,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}

@Composable
private fun ProductInfoText(
    name: String,
    price: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Text(
            text = price,
            color = Color(0xff555555),
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    ProductCard(
        product = ProductUiModel(
            id = "1",
            imageUrl =
                "https://cdn.eyesmag.com/content/uploads/posts/2024/10/23/shutterstock_250" +
                    "0953971-3c494ea8-0ac0-4f8d-a962-e47db09215a0.jpg",
            name = "고양이",
            price = "999,999,999원",
            cartAmount = "0",
            showAmountController = false,
        ),
        onClickItem = { },
        onClickMinus = { },
        onClickAdd = { },
        modifier = Modifier.padding(5.dp),
    )
}
