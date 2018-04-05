package com.jijith.vmax.modules.add_product

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import com.jijith.vmax.R
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.Stock
import com.jijith.vmax.utils.*
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddProductActivity : BaseActivity(), AddProductView, RequestBodyProgress.UploadCallbacks {

    companion object {
        val TAG = AddProductActivity::class.java.simpleName
    }

    internal val REQUEST_PICK_IMAGE = 83
    internal val REQUEST_IMAGE_CAPTURE = 84
    internal val REQUEST_PERMISSION = 100

    @BindView(R.id.layout_product_image)
    lateinit var layoutProductImage: RelativeLayout
    @BindView(R.id.iv_product)
    lateinit var productImage: ImageView
    @BindView(R.id.til_product_name)
    lateinit var layoutStockName: TextInputLayout
    @BindView(R.id.til_quantity)
    lateinit var layoutQuantity: TextInputLayout
    @BindView(R.id.til_stock_price)
    lateinit var layoutStockPrice: TextInputLayout
    @BindView(R.id.til_sale_price)
    lateinit var layoutSalePrice: TextInputLayout

    @BindView(R.id.tiet_product_name)
    lateinit var productName: TextInputEditText
    @BindView(R.id.tiet_quantity)
    lateinit var quantity: TextInputEditText
    @BindView(R.id.tiet_stock_price)
    lateinit var stockPrice: TextInputEditText
    @BindView(R.id.tiet_sale_price)
    lateinit var salePrice: TextInputEditText

    @BindView(R.id.tv_date)
    lateinit var purchaseDate: TextView
    @BindView(R.id.btn_add)
    lateinit var btnAdd: Button

    internal var bitmap: Bitmap? = null

    internal var fromDatePicker: DatePickerDialog? = null
    internal var fromDate: Long? = null

    @Inject
    lateinit var addProductPresenter: AddProductPresenter

//    private var realm: Realm by Delegates.notNull()

    var fileName: String = ""


    override fun getLayout(): Int {
        return R.layout.activity_add_product
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_stock)
        AndroidInjection.inject(this);

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Open the realm for the UI thread.
//        realm = Realm.getDefaultInstance()

        val calendar = Calendar.getInstance()
        fromDatePicker = DatePickerDialog(this, fromDateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        var millis = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .parse("2018/01/01 00:00:00").time;

        try {
            fromDatePicker?.getDatePicker()?.setMaxDate(Calendar.getInstance().timeInMillis)
            fromDatePicker?.getDatePicker()?.setMinDate(millis)
        } catch (e: ParseException) {
            Log.e(TAG, e.toString())
        }

        registerForContextMenu(layoutProductImage)

        Picasso.with(this)
                .load(Uri.parse(fileName))
                .placeholder(R.drawable.ic_ph_product)
                .into(productImage)

        addProductPresenter.getId()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data?.extras
            try {
                val bitmap = extras!!.get("data") as Bitmap

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                val imageUri = getImageUri(applicationContext, bitmap!!)

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                //                File finalFile = new File(getRealPathFromURI(imageUri));

                Log.e("FILE PATH", imageUri.getPath())
                //                Log.e("FILE NAME", finalFile.toString());

                val filePath = FileUtils.getFile(applicationContext, imageUri)
                Log.e("NEW PATH", filePath.toString())

                fileName = filePath.toString()
                Log.e(TAG, fileName.substring(fileName.lastIndexOf(".") + 1))

                if (!fileName.substring(fileName.lastIndexOf(".") + 1).equals("PNG", ignoreCase = true) &&
                        !fileName.substring(fileName.lastIndexOf(".") + 1).equals("JPEG", ignoreCase = true) &&
                        !fileName.substring(fileName.lastIndexOf(".") + 1).equals("JPG", ignoreCase = true)) {
                    Toast.makeText(this, getString(R.string.error_invalid_file), Toast.LENGTH_SHORT).show()
                    return
                }

                val fileBody = RequestBodyProgress(filePath!!, this)

                //                RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, token);
                //                RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), new String(token));
                //                MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", new File(imageUri.getPath()).getName(), RequestBody.create(MediaType.parse("image/*"), fileBody));
                val body = MultipartBody.Part.createFormData("file", filePath.getName(), fileBody)

//                productImage.setImageBitmap(bitmap)

                Picasso
                        .with(this)
                        .load(File(fileName))
                        .placeholder(R.drawable.ic_ph_product)
                        .into(productImage)

            } catch (e: NullPointerException) {
                //                if (mProgressDialog.isShowing())
                //                    mProgressDialog.dismiss();
                //                fileName = "";
                Toast.makeText(this, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Log.e(TAG, e.toString())
            }

        }

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) { //Image Upload

            val imageUri = data?.data
            val imageStream: InputStream
            try {
                imageStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(imageStream)

                Log.e("FILE PATH", imageUri!!.path)
                Log.e("FILE NAME", File(imageUri.path).name)

                //                String token = Utils.LoadPreferences(getApplicationContext(), Constants.TOKEN);
                //                Log.e("TOKEN", token);

                //                MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), fileBody);

                val filePath = FileUtils.getFile(applicationContext, imageUri)
                Log.e("NEW PATH", filePath.toString())

                fileName = filePath.toString()
                Log.e(TAG, fileName.substring(fileName.lastIndexOf(".") + 1))

                if (!fileName.substring(fileName.lastIndexOf(".") + 1).equals("PNG", ignoreCase = true) &&
                        !fileName.substring(fileName.lastIndexOf(".") + 1).equals("JPEG", ignoreCase = true) &&
                        !fileName.substring(fileName.lastIndexOf(".") + 1).equals("JPG", ignoreCase = true)) {
                    Toast.makeText(this, getString(R.string.error_invalid_file), Toast.LENGTH_SHORT).show()
                    return
                }

                val fileBody = RequestBodyProgress(filePath!!, this)

                //                RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, token);

                //                RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), new String(token));
                //                MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", new File(imageUri.getPath()).getName(), RequestBody.create(MediaType.parse("image/*"), fileBody));
                val body = MultipartBody.Part.createFormData("file", filePath.getName(), fileBody)

//                productImage.setImageBitmap(bitmap)

                Picasso
                        .with(this)
                        .load(File(fileName))
                        .placeholder(R.drawable.ic_ph_product)
                        .into(productImage)

            } catch (e: FileNotFoundException) {
                //                if (mProgressDialog.isShowing())
                //                    mProgressDialog.dismiss();
                //                fileName = "";
                Toast.makeText(this, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Log.e(TAG, e.toString())
            } catch (e: NullPointerException) {
                Toast.makeText(this, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Log.e(TAG, e.toString())
            }

        }
    }

    private fun onSelectGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Storage permission has not been granted.
                requestPermission(REQUEST_PERMISSION)
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        } else {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_PICK_IMAGE)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu!!.add(0, v!!.id, 0, getString(R.string.cmenu_camera))//groupId, itemId, order, title
        menu!!.add(0, v!!.id, 0, getString(R.string.cmenu_gallery))

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.title === getString(R.string.cmenu_camera)) {
            dispatchTakePictureIntent()
            //            Toast.makeText(getApplicationContext(), "Camera code", Toast.LENGTH_LONG).show();
        } else if (item.title === getString(R.string.cmenu_gallery)) {
            onSelectGallery()
            //            Toast.makeText(getApplicationContext(), "Gallery code", Toast.LENGTH_LONG).show();
        } else {
            return false
        }
        return true
    }

    private fun requestPermission(id: Int) {
        AppLog.d(TAG, "Storage permission has NOT been granted. Requesting permission.")

        // Storage permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), id)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == REQUEST_PERMISSION) {

            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onSelectGallery()
            } else {
                Toast.makeText(this, "Please allow permission to continue.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    override fun onStockAdded(msg: String) {
        Utils.showSnackBar(productImage, msg)

        productImage.setImageDrawable(null)
        productName.setText("")
        purchaseDate.text = ""
        quantity.setText("")
        stockPrice.setText("")
        salePrice.setText("")

        productName.requestFocus()

        Utils.SavePreferences(this, Constants.PRODUCT_CHANGED, true)

    }

    override fun onError(msg: String) {
        Utils.Toast(this, msg)
    }

    override fun onErrorProductImage(msg: String) {
        Utils.Toast(this, msg)
    }

    override fun onErrorStockName(msg: String) {
        productName.error = msg
    }

    override fun onErrorPurchaseDate(msg: String) {
        Utils.Toast(this, msg)
    }

    override fun onErrorQuantity(msg: String) {
        quantity.error = msg
    }

    override fun onErrorStockPrice(msg: String) {
        stockPrice.error = msg
    }

    override fun onErrorSalePrice(msg: String) {
        salePrice.error = msg
    }

    override fun onProgressUpdate(percentage: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFinish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @OnClick(R.id.layout_product_image)
    fun onClickProductImage() {
        openContextMenu(layoutProductImage)
//        Utils.Toast(this, "clicked")
    }

    @OnClick(R.id.tv_date)
    fun onClickDate() {
        fromDatePicker?.show()
    }

//    @OnClick(R.id.btn_show)
//    fun onClickShow() {
//        // Find the first person (no query conditions) and read a field
//        val product = realm.where(Product::class.java).findAll()
////        showStatus(person.name + ": " + person.age)
//        for(pro in product) {
//            AppLog.e(TAG, pro.productName + "img" + pro.imagePath)
//        }
//    }

    @OnClick(R.id.btn_add)
    fun onClickAddProduct() {

        var product = Product()
        var stock = Stock()
        product.productName = productName.text.toString()
        stock.purchaseDate = purchaseDate.text.toString()
        product.quantity = Integer.parseInt(quantity.text.toString())
        stock.stockPrice = Integer.parseInt(stockPrice.text.toString())
        stock.salePrice = Integer.parseInt(salePrice.text.toString())
        stock.balanceStock = Integer.parseInt(quantity.text.toString())
        product.imagePath = fileName

        addProductPresenter.addStock(product, stock)
    }

    private val fromDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        var arg2 = arg2
        // arg1 = year
        // arg2 = month
        // arg3 = day
        Log.e("fromDate", arg1.toString() + " " + arg2 + " " + arg3)

        arg2 += 1
        val date = arg1.toString() + "-" + arg2 + "-" + arg3

        try {
            fromDate = SimpleDateFormat("yyyy-MM-dd").parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        try {
            fromDatePicker?.getDatePicker()?.setMaxDate(Calendar.getInstance().timeInMillis)
//                fromDatePicker?.getDatePicker()?.setMinDate(Calendar.getInstance().timeInMillis)
        } catch (e: ParseException) {
            Log.e(TAG, e.toString())
        }

        purchaseDate.setText(date)
    }

}
