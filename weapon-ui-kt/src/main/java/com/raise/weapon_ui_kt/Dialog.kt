package com.raise.weapon_ui_kt

import androidx.fragment.app.FragmentActivity
import com.raise.weapon_ui.dialog.IBuilder
import com.raise.weapon_ui.dialog.IVCDialog
import com.raise.weapon_ui.dialog.VcDialog
import com.raise.weapon_ui.dialog.VcMaterialDialogBuilder

/**
 * val dialog = showVcDialog {
 *     setTitle("提示")
 *     setMessage("内有恶犬")
 *     setOnCancelListener {
 *         ToastUtil.show("我消失了")
 *     }
 * }
 */
fun FragmentActivity.showVcDialog(
    show: Boolean = true,
    block: IBuilder.() -> Unit
): IVCDialog {
    val builder = VcDialog.Builder(this)
    block(builder)
    val vcDialog = builder.create()
    if (show) {
        vcDialog.show()
    }
    return vcDialog
}

fun FragmentActivity.showMaterialDialog(
    show: Boolean = true,
    block: IBuilder.() -> Unit
): IVCDialog {
    val builder = VcMaterialDialogBuilder(this)
    block(builder)
    val vcDialog = builder.create()
    if (show) {
        vcDialog.show()
    }
    return vcDialog
}

