package woowacourse.shopping.ui.shopping.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.common.component.QuantityControlButton
import woowacourse.shopping.ui.common.component.ShoppingImage
import woowacourse.shopping.ui.shopping.ProductUiModel

@SuppressLint("DefaultLocale")
@Composable
fun ProductUnit(
    model: ProductUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onIncreaseClick: (Product) -> Unit,
    onDecreaseClick: (Product) -> Unit,
) {
    val product = model.product
    val price = product.price.value
    val formatted = String.format("%,d", price)
    Column(
        modifier =
            modifier
                .width(154.dp)
                .height(206.dp)
                .clickable(onClick = onClick),
    ) {
        Box {
            ShoppingImage(
                model = product.imageUrl,
                contentDescription = "상품 미리보기",
                modifier = Modifier.size(154.dp),
            )

            if (model.isAddedToCart) {
                QuantityControlButton(
                    count = model.cartQuantity,
                    onIncreaseClick = { onIncreaseClick(product) },
                    onDecreaseClick = { onDecreaseClick(product) },
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(8.dp),
                )
            } else {
                AddButton(
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                    onClick = { onIncreaseClick(product) },
                )
            }
        }

        Spacer(Modifier.size(6.dp))
        Text(
            text = product.name,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 6.dp, end = 9.dp),
        )
        Text(
            text = "$formatted 원",
            color = Color.DarkGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Composable
@Preview(showBackground = true, name = "상품 2개")
private fun ProductUnitPreview() {
    val product =
        ProductUiModel(
            product =
                Product(
                    name = "연금복권",
                    price = Money(1000),
                    imageUrl = "",
                ),
            cartQuantity = 2,
        )
    ProductUnit(
        model = product,
        onClick = {},
        onIncreaseClick = {},
        onDecreaseClick = {},
    )
}

@Composable
@Preview(showBackground = true, name = "상품 0개 & 긴 이름을 가진 상품")
private fun ProductUnitPreview2() {
    ProductUnit(
        model =
            ProductUiModel(
                product =
                    Product(
                        name = "정말정말 엄청나게 긴 이름을 가지고 있는 상품",
                        price = Money(1000),
                        imageUrl = "",
                    ),
            ),
        onClick = {},
        onIncreaseClick = {},
        onDecreaseClick = {},
    )
}
