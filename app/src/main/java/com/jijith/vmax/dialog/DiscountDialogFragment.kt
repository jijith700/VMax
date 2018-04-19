package com.jijith.vmax.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.vmax.R
import com.jijith.vmax.modules.chekout.CheckOutView
import com.jijith.vmax.utils.AppLog

class DiscountDialogFragment : AppCompatDialogFragment() {

    @BindView(R.id.tiet_name)
    lateinit var name: TextInputEditText
    @BindView(R.id.tiet_phone)
    lateinit var phone: TextInputEditText
    @BindView(R.id.tiet_discount)
    lateinit var discount: TextInputEditText
    @BindView(R.id.tiet_commission)
    lateinit var commission: TextInputEditText

    private lateinit var checkOutView: CheckOutView

    private var clientName: String = "";
    private var clientPhone: String = "";
    private var discountAmount: Int = 0;
    private var commissionAmount: Int = 0;


    override fun show(manager: FragmentManager, tag: String) {

        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            AppLog.e("ErrorSdDialog", e.toString())
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // request a window without the title
        dialog.getWindow()!!.requestFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discount_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        val btnOk = view.findViewById<Button>(R.id.btn_ok)
        btnOk.setOnClickListener(okHandler)

    }

    private var okHandler: View.OnClickListener = View.OnClickListener {

        if (!name.text.isNullOrBlank())
            clientName = name.getText().toString()
        else
            clientName = ""

        if (!phone.text.isNullOrBlank())
            clientPhone = phone.getText().toString()
        else
            clientPhone = ""

        if (!discount.text.isNullOrBlank())
            discountAmount = Integer.parseInt(discount.getText().toString())
        else
            discountAmount = 0

        if (!commission.text.isNullOrBlank())
            commissionAmount = Integer.parseInt(commission.getText().toString())
        else
            commissionAmount = 0

        checkOutView!!.onChangeCustomerDetails(clientName, clientPhone, discountAmount, commissionAmount)

        dismiss()

    }

    fun setCheckOutView(checkOutView: CheckOutView) {
        this.checkOutView = checkOutView
    }

    interface DiscountListener {
        fun onDiscountAmountChanged(price: Int)
        fun onCommissionAmountChanged(price: Int)

    }


}