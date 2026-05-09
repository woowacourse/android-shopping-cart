package woowacourse.shopping.feature.products.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import woowacourse.shopping.core.designsystem.component.AppImage
import woowacourse.shopping.core.designsystem.component.QuantityStepper

@Composable
fun ProductItem(
    productImageUrl: String,
    productName: String,
    formattedPrice: String,
    formattedQuantity: String,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable(onClick = onClick),
    ) {
        Box {
            AppImage(
                imageUrl = productImageUrl,
                modifier = Modifier.aspectRatio(1f),
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                if (formattedQuantity == "0") {
                    AddCartButton(onClick = onAddClick)
                } else {
                    QuantityStepper(
                        onPlusClick = onIncreaseClick,
                        onMinusClick = onDecreaseClick,
                        quantity = formattedQuantity,
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Text(
            text = productName,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
        )
        Text(
            text = formattedPrice,
            color = Color.Black,
            fontSize = 16.sp,
            maxLines = 1,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
        )
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    ProductItem(
        productImageUrl = "",
        productName = "리자몽은 강력한 불꽃과 비행 능력을 지닌 포켓몬으로, 전투에서 뛰어난 공격력과 카리스마를 발휘하며 많은 트레이너들에게 사랑받는 존재이다.",
        formattedPrice = "10,000원",
        onClick = {},
        formattedQuantity = "1",
        onAddClick = {},
        onIncreaseClick = {},
        onDecreaseClick = {},
    )
}
