package com.raise.weapon_ui_kt

import androidx.fragment.app.FragmentActivity
import com.raise.weapon_ui.dialog.IBuilder
import com.raise.weapon_ui.dialog.IVCDialog
import com.raise.weapon_ui.dialog.VcDialog

/**
 * val dialog = showVcDialog(this) {
 *     setTitle("提示")
 *     setMessage("内有恶犬")
 *     setOnCancelListener {
 *         ToastUtil.show("我消失了")
 *     }
 * }
 */
fun showVcDialog(
    context: FragmentActivity,
    show: Boolean = true,
    block: IBuilder.() -> Unit
): IVCDialog {
    val builder = VcDialog.Builder(context)
    block(builder)
    val vcDialog = builder.create()
    if (show) {
        vcDialog.show()
    }
    return vcDialog
}