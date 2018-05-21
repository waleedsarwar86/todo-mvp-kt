package com.todo.ui.feature.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.todo.R
import com.todo.ui.base.BaseDialog

import butterknife.ButterKnife
import butterknife.OnClick

class SelectPriorityDialog : BaseDialog() {

    private var callback: SelectPriorityDialogCallback? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.select_priority_dialog, container)
        ButterKnife.bind(this, view)
        return view
    }

    /********* Butterknife Binded Methods   */

    @OnClick(R.id.select_priority_linear_layout_priority_low,
            R.id.select_priority_linear_layout_priority_normal,
            R.id.select_priority_linear_layout_priority_high,
            R.id.select_priority_linear_layout_priority_crcucial)
    fun onPrioritySelected(linearLayout: LinearLayout) {
        val priority = Integer.parseInt(linearLayout.tag.toString())
        callback!!.onPrioritySelected(priority)
        dismiss()
    }

    @OnClick(R.id.select_priority_button_cancel)
    fun onButtonCancelClick() {
        dismiss()
    }

    /********* Member Methods   */

    fun setSelectPriorityDialogCallback(callback: SelectPriorityDialogCallback) {
        this.callback = callback
    }

    interface SelectPriorityDialogCallback {

        fun onPrioritySelected(priority: Int)
    }

    companion object {

        const val TAG = "SelectPriorityDialog"

        /********* Static Methods   */

        fun newInstance(callback: SelectPriorityDialogCallback): SelectPriorityDialog {
            val args = Bundle()
            val fragment = SelectPriorityDialog()
            fragment.arguments = args
            fragment.setSelectPriorityDialogCallback(callback)
            return fragment
        }
    }

}
