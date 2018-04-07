package com.jijith.vmax.utils

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.os.Environment
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jijith.vmax.R
import java.io.File
import java.io.IOException

/**
 * Created by jijith on 12/26/17.
 */
class Utils {

    companion object {

        val TAG = Utils::class.java.simpleName

        fun savePreferences(context: Context, key: String, value: String) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sp.edit()
            editor.putString(key, value)
            editor.apply()
            //Log.w("Preference saved  .", value);

        }

        fun savePreferences(context: Context, key: String, value: Boolean) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sp.edit()
            editor.putBoolean(key, value)
            editor.apply()
            //Log.w("Preference saved  .", value);

        }

        fun loadPreferences(context: Context, key: String): String {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = ""
            value = sp.getString(key, "")
            return value
        }

        fun loadPreferencesBoolean(context: Context, key: String): Boolean {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = false
            value = sp.getBoolean(key, false)
            return value
        }

        fun clearPreference(context: Context, key: String) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.remove(key).apply()
        }

        fun clearAllPreference(context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.clear()
            editor.apply()
        }

        fun logout(context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
//            editor.remove(Constants.TOKEN)
//            editor.remove(Constants.USERID)
//            editor.remove(Constants.EMAIL)
//            editor.remove(Constants.PRO_PIC)
//            editor.remove(Constants.USER_NAME)
            //        editor.clear();
            editor.apply()
        }

        fun isNetworkConnected(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (manager.activeNetworkInfo != null) {
                if (manager.activeNetworkInfo.isAvailable && manager.activeNetworkInfo.isConnected)
                    true
                else
                    false
            } else {
                false
            }
        }

        fun alert(context: Context, msg: String) {
            val dialog = AlertDialog.Builder(context,
                    R.style.Theme_App_Light_Dialog_NoActionBar)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(context.getString(R.string.btn_ok)) { dialogInterface, i -> dialogInterface.dismiss() }
                    .create()

            dialog.show()
        }

        fun toast(context: Context, msg: String) {
            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
        }

        fun showSnackBar(view: View, msg: String) {
            Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null)
                    .setDuration(6000)
                    .show()
        }

        fun hideKeyboard(context: Context, view: View) {
            val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * Method to create App folder
         */
        fun hasAppFolder(context: Context): Boolean {
            try {
                val filePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + context.getString(R.string.app_name)

                if (!File(filePath).exists()) {
                    if (File(filePath).mkdir()) {
                        AppLog.d(TAG, "Directory created")

                        File(filePath + File.separator +
                                context.getString(R.string.dir_product)).mkdir()
                        AppLog.d(TAG, "Product Directory created")

                        File(filePath +
                                File.separator + context.getString(R.string.dir_product) +
                                File.separator + context.getString(R.string.file_no_media)).createNewFile()
                        AppLog.d(TAG, "No Media created")

                        return true
                    } else {
                        AppLog.d(TAG, "Directory is not created")
                        return false
                    }
                } else
                    return true
            } catch (e: IOException) {
                AppLog.e(TAG, e.toString())
                return false
            }
        }

        fun getFolderPath(context: Context, dir : String): File {
            var file : File? = null ;

            try {
                val filePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + context.getString(R.string.app_name)

                if (!File(filePath).exists()) {
                    if (File(filePath).mkdir()) {
                        AppLog.d(TAG, "Directory created")

                        file = File(filePath + File.separator + dir)
                        file.mkdir()
                        AppLog.d(TAG, "Product Directory created")

                        File(filePath +
                                File.separator + context.getString(R.string.dir_product) +
                                File.separator + context.getString(R.string.file_no_media)).createNewFile()
                        AppLog.d(TAG, "No Media created")

                        return file
                    } else {
                        AppLog.d(TAG, "Directory is not created")
                        file = File(filePath + File.separator + dir)
                        return file
                    }
                } else
                    file = File(filePath + File.separator + dir)
                    return file
            } catch (e: IOException) {
                AppLog.e(TAG, e.toString())
                return file!!
            }
        }

        fun setBadgeCount(context : Context, icon: LayerDrawable, count: String ) {

            val badge : BadgeDrawable

            // Reuse drawable if possible
            val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
            if (reuse != null && reuse is BadgeDrawable) {
                badge = reuse
            } else {
                badge = BadgeDrawable(context)
                icon.mutate();
                icon.setDrawableByLayerId(R.id.ic_badge, badge);
            }

            badge.setCount(count);
//            icon.mutate();
//            icon.setDrawableByLayerId(R.id.ic_badge, badge);
        }
    }
}