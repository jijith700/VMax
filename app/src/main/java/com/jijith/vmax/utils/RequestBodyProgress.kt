package com.jijith.vmax.utils

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by jijith on 1/13/18.
 */
class RequestBodyProgress : RequestBody {

    internal var mFile: File? = null
    internal val mPath: String? = null
    internal var mListener: UploadCallbacks? = null

    private val DEFAULT_BUFFER_SIZE = 2048

    interface UploadCallbacks {
        fun onProgressUpdate(percentage: Int)

        fun onError()

        fun onFinish()
    }

    constructor(file: File, listener: UploadCallbacks) {
        mFile = file
        mListener = listener
    }

    override fun contentType(): MediaType? {
        // i want to upload only images
        return MediaType.parse("image/*")
        //        return MediaType.parse("multipart/form-data");
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile!!.length()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = mFile!!.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val inputStream = FileInputStream(mFile)
        var uploaded: Long = 0

        try {
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            read = inputStream.read(buffer);
            while (read != -1) {

                // update progress on UI thread
                handler.post(ProgressUpdater(uploaded, fileLength))

                uploaded += read.toLong()
                sink.write(buffer, 0, read)

                read = inputStream.read(buffer);
            }
        } finally {
            inputStream.close()
        }
    }

    private inner class ProgressUpdater(private val mUploaded: Long, private val mTotal: Long) : Runnable {

        override fun run() {
            mListener?.onProgressUpdate((100 * mUploaded / mTotal).toInt())
        }
    }
}