package woowacourse.shopping.presentation.cart.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeleteProductDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    titleText: String = "상품을 삭제할까요?",
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(
                text = titleText,
                fontSize = 16.sp,
            )
        },
        confirmButton = {
            DialogButton(
                onClick = onConfirm,
                text = "확인",
                containerColor = Color.Black,
                contentColor = Color.White,
            )
        },
        dismissButton = {
            DialogButton(
                onClick = onDismiss,
                text = "취소",
                containerColor = Color.White,
                contentColor = Color.Black,
            )
        },
    )
}

@Composable
private fun DialogButton(
    onClick: () -> Unit,
    text: String,
    containerColor: Color,
    contentColor: Color,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun DeleteProductDialogPreview() {
    DeleteProductDialog(
        onDismissRequest = {},
        onConfirm = {},
        onDismiss = {},
    )
}
