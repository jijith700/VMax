package com.jijith.vmax.modules.chekout

import com.jijith.vmax.adapter.CheckOutAdapter

/**
 * Created by jijith on 12/25/17.
 */
interface CheckOutView {

    fun setAdapter(adapter: CheckOutAdapter)
    fun onChangeTotalPrice(price: Int)
    fun onChangeCustomerDetails(name: String, phone: String, discount: Int, commission: Int)
    fun onCompleteCheckOut()

    fun onErrorName(msg: String)
}