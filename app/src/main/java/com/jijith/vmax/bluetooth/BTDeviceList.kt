package com.jijith.vmax.bluetooth

import android.app.ListActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.io.IOException
import java.util.*

class BTDeviceList : ListActivity() {

    companion object {

    val REQUEST_CONNECT_BT = 0x2300

    val REQUEST_ENABLE_BT = 0x1000

    var mBluetoothAdapter: BluetoothAdapter? = null

    var mArrayAdapter: ArrayAdapter<String>? = null

    var btDevices: ArrayAdapter<BluetoothDevice>? = null

    var SPP_UUID = UUID
            .fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
// UUID.fromString("00001101-0000-1000-8000-00805F9B34FB”)

    var mbtSocket: BluetoothSocket? = null


        fun getSocket(): BluetoothSocket {
            return mbtSocket!!
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("Bluetooth Devices")

        val btIntentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(mBTReceiver, btIntentFilter)

        try {
            if (initDevicesList() != 0) {
                finish()
                return
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            finish()
            return
        }


    }

    private fun flushData() {
        try {
            if (mbtSocket != null) {
                mbtSocket!!.close()
                mbtSocket = null
            }

            if (mBluetoothAdapter != null) {
                mBluetoothAdapter!!.cancelDiscovery()
            }

            if (btDevices != null) {
                btDevices!!.clear()
                btDevices = null
            }

            if (mArrayAdapter != null) {
                mArrayAdapter!!.clear()
                mArrayAdapter!!.notifyDataSetChanged()
                mArrayAdapter!!.notifyDataSetInvalidated()
                mArrayAdapter = null
            }

//            finalize()

        } catch (ex: Exception) {
        } catch (e: Throwable) {
        }

    }

    private fun initDevicesList(): Int {

        flushData()
        if (btDevices == null) {
            btDevices = ArrayAdapter<BluetoothDevice>(applicationContext, android.R.layout.simple_list_item_1)
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth not supported!!", Toast.LENGTH_LONG).show()
            return -1
        }

//        if (mBluetoothAdapter!!.isDiscovering()) {
//            mBluetoothAdapter!!.cancelDiscovery()
//        }

        mArrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1)

        setListAdapter(mArrayAdapter)

        val enableBtIntent = Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE)
        try {
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return -2
        }

        Toast.makeText(getApplicationContext(),
                "Getting all available Bluetooth Devices", Toast.LENGTH_SHORT).show()

        return 0

    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(reqCode, resultCode, intent)

        when (reqCode) {
            REQUEST_ENABLE_BT -> {

                if (resultCode == RESULT_OK) {
                    val btDeviceList = mBluetoothAdapter!!.getBondedDevices()
                    try {
                        if (btDeviceList.size > 0) {
                            for (device in btDeviceList) {
//                                if (btDeviceList.contains(device) == false) {

                                    btDevices!!.add(device)

                                    mArrayAdapter!!.add(device.getName() + "\n" + device.getAddress())
                                    mArrayAdapter!!.notifyDataSetInvalidated()
//                                }
                            }
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }

        mBluetoothAdapter!!.startDiscovery()
    }

    val mBTReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            val action = intent!!.action
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

                try {
                    if (btDevices == null) {
                        btDevices = ArrayAdapter<BluetoothDevice>(applicationContext, android.R.layout.simple_list_item_1)
                    }

                    if (btDevices!!.getPosition(device) < 0) {
                        btDevices!!.add(device)
                        mArrayAdapter!!.add(device.getName() + "\n"
                                + device.getAddress() + "\n")
                        mArrayAdapter!!.notifyDataSetInvalidated()
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    override fun onListItemClick(l: ListView, v: View, position: Int,
                                 id: Long) {
        super.onListItemClick(l, v, position, id)

        if (mBluetoothAdapter == null) {
            return
        }

        if (mBluetoothAdapter!!.isDiscovering()) {
            mBluetoothAdapter!!.cancelDiscovery()
        }

        Toast.makeText(
                getApplicationContext(),
                "Connecting to " + btDevices!!.getItem(position).getName() + ","
                        + btDevices!!.getItem(position).getAddress(),
                Toast.LENGTH_SHORT).show()

        var connectThread = Thread(Runnable() {

            fun run() {
                try {
                    val gotuuid = btDevices!!.getItem(position)
                            .fetchUuidsWithSdp()
                    val uuid = btDevices!!.getItem(position).getUuids()[0]
                            .getUuid()
                    mbtSocket = btDevices!!.getItem(position)
                            .createRfcommSocketToServiceRecord(uuid)

                    mbtSocket!!.connect()
                } catch (ex: IOException) {
                    runOnUiThread(socketErrorRunnable)
                    try {
                        mbtSocket!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    mbtSocket = null
                    return
                } finally {
                    runOnUiThread(Runnable() {

                        fun run() {
                            finish()

                        }
                    })
                }
            }
        })

        connectThread.start()
    }

    var socketErrorRunnable = Runnable() {

        fun run() {
            Toast.makeText(getApplicationContext(),
                    "Cannot establish connection", Toast.LENGTH_SHORT).show()
            mBluetoothAdapter!!.startDiscovery()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.add(0, Menu.FIRST, Menu.NONE, "Refresh Scanning")

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.getItemId()) {
            Menu.FIRST -> {
                initDevicesList()
                return true
            }
        }

        return true
    }

}