package com.jijith.vmax.modules

import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.jijith.vmax.R
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.bluetooth.BTDeviceList
import java.io.IOException
import java.io.OutputStream


class InvoiceActivity : AppCompatActivity() {

    private var btsocket: BluetoothSocket? = null
    private var btoutputstream: OutputStream? = null
    var FONT_TYPE: Byte = 0

//    override fun getLayout(): Int {
//        return R.layout.activity_invoice;
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.fab_print)
    fun onClickFabPrint() {
        connect()
    }

    fun connect() {
        if (btsocket == null) {
            val BTIntent = Intent(this, BTDeviceList::class.java)
            startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT)
        } else {

            var opstream: OutputStream? = null
            try {
                opstream = btsocket!!.getOutputStream()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            btoutputstream = opstream;
            print_bt()
        }
    }

    fun print_bt() {
        try {
            try {
                Thread.sleep(1000);
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            btoutputstream = btsocket!!.getOutputStream();

            val printformat: Array<Byte> = arrayOf(0x1B, 0x21, FONT_TYPE)
            btoutputstream!!.write(printformat.toByteArray());
//            val msg = message!!.getText().toString()
            val msg: String = "a"
            btoutputstream!!.write(msg.toByteArray())
            btoutputstream!!.write(0x0D)
            btoutputstream!!.write(0x0D)
            btoutputstream!!.write(0x0D)
            btoutputstream!!.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (btsocket != null) {
                btoutputstream!!.close()
                btsocket!!.close()
                btsocket = null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            btsocket = BTDeviceList.getSocket()
            if (btsocket != null) {
                print_bt()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
