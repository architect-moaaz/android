package com.intelliflow.apps.view.recentactions


import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.intelliflow.apps.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class SignatureActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    var btn_get_sign: Button? = null
    var mClear: Button? = null
    var mGetSign: Button? = null
    var mCancel: Button? = null
    var file: File? = null
    var dialog: Dialog? = null
    var mContent: LinearLayout? = null
    var view: View? = null
    var mSignature: signature? = null
    var bitmap: Bitmap? = null

    // Creating Separate Directory for saving Generated Images
    var DIRECTORY = Environment.getExternalStorageDirectory().path + "/DigitSign/"
    var pic_name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    var StoredPath = "$DIRECTORY$pic_name.png"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signature_activity)

        // Setting ToolBar as ActionBar
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Button to open signature panel
        btn_get_sign = findViewById<View>(R.id.signature) as Button

        // Method to create Directory, if the Directory doesn't exists
        file = File(DIRECTORY)
        if (!file!!.exists()) {
            file!!.mkdir()
        }

        // Dialog Function
        dialog = Dialog(this)
        // Removing the features of Normal Dialogs
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_signature)
        dialog!!.setCancelable(true)
        btn_get_sign!!.setOnClickListener { // Function call for Digital Signature
            dialog_action()
        }
    }

    // Function for Digital Signature
    fun dialog_action() {
        mContent = dialog!!.findViewById<View>(R.id.linearLayout) as LinearLayout
        mSignature = signature(applicationContext, null)
        mSignature!!.setBackgroundColor(Color.WHITE)
        // Dynamically generating Layout through java code
        mContent!!.addView(
            mSignature,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mClear = dialog!!.findViewById<View>(R.id.clear) as Button
        mGetSign = dialog!!.findViewById<View>(R.id.getsign) as Button
        mGetSign!!.isEnabled = false
        mCancel = dialog!!.findViewById<View>(R.id.cancel) as Button
        view = mContent
        mClear!!.setOnClickListener {
            Log.v("tag", "Panel Cleared")
            mSignature!!.clear()
            mGetSign!!.isEnabled = false
        }
        mGetSign!!.setOnClickListener {
            Log.v("tag", "Panel Saved")
            view!!.isDrawingCacheEnabled = true
            mSignature!!.save(view, StoredPath)
            dialog!!.dismiss()
            Toast.makeText(applicationContext, "Successfully Saved", Toast.LENGTH_SHORT).show()
            // Calling the same class
            recreate()
        }
        mCancel!!.setOnClickListener {
            Log.v("tag", "Panel Cancelled")
            dialog!!.dismiss()
            // Calling the same class
            recreate()
        }
        dialog!!.show()
    }

    inner class signature(context: Context?, attrs: AttributeSet?) :
        View(context, attrs) {
        private val paint = Paint()
        private val path = Path()
        private var lastTouchX = 0f
        private var lastTouchY = 0f
        private val dirtyRect = RectF()
        fun save(v: View?, StoredPath: String?) {
            Log.v("tag", "Width: " + v!!.width)
            Log.v("tag", "Height: " + v.height)
            if (bitmap == null) {
                bitmap =
                    Bitmap.createBitmap(mContent!!.width, mContent!!.height, Bitmap.Config.RGB_565)
            }
            val canvas = Canvas(bitmap!!)
            try {
                // Output the file
                val mFileOutStream = FileOutputStream(StoredPath)
                v.draw(canvas)
                // Convert the output file to Image such as .png
                bitmap!!.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream)
                mFileOutStream.flush()
                mFileOutStream.close()
            } catch (e: Exception) {
                Log.v("log_tag", e.toString())
            }
        }

        fun clear() {
            path.reset()
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawPath(path, paint)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val eventX = event.x
            val eventY = event.y
            mGetSign!!.isEnabled = true
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(eventX, eventY)
                    lastTouchX = eventX
                    lastTouchY = eventY
                    return true
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                    resetDirtyRect(eventX, eventY)
                    val historySize = event.historySize
                    var i = 0
                    while (i < historySize) {
                        val historicalX = event.getHistoricalX(i)
                        val historicalY = event.getHistoricalY(i)
                        expandDirtyRect(historicalX, historicalY)
                        path.lineTo(historicalX, historicalY)
                        i++
                    }
                    path.lineTo(eventX, eventY)
                }
                else -> {
                    debug("Ignored touch event: $event")
                    return false
                }
            }
            invalidate(

                (dirtyRect.left - 2.5f).toInt(),
                (dirtyRect.top - 2.5f).toInt(),
                (dirtyRect.right + 2.5f).toInt(),
                (dirtyRect.bottom + 2.5f).toInt()

//                (dirtyRect.left - Companion.HALF_STROKE_WIDTH).toInt(),
//                (dirtyRect.top - Companion.HALF_STROKE_WIDTH).toInt(),
//                (dirtyRect.right + Companion.HALF_STROKE_WIDTH).toInt(),
//                (dirtyRect.bottom + Companion.HALF_STROKE_WIDTH).toInt()
            )
            lastTouchX = eventX
            lastTouchY = eventY
            return true
        }

        private fun debug(string: String) {
            Log.v("log_tag", string)
        }

        private fun expandDirtyRect(historicalX: Float, historicalY: Float) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX
            }
            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY
            }
        }

        private fun resetDirtyRect(eventX: Float, eventY: Float) {
            dirtyRect.left = Math.min(lastTouchX, eventX)
            dirtyRect.right = Math.max(lastTouchX, eventX)
            dirtyRect.top = Math.min(lastTouchY, eventY)
            dirtyRect.bottom = Math.max(lastTouchY, eventY)
        }

//        companion object {
//            private const val STROKE_WIDTH = 5f
//            private const val HALF_STROKE_WIDTH = STROKE_WIDTH / 2
//        }

        init {
            paint.isAntiAlias = true
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = Paint.Join.ROUND
          //  paint.strokeWidth = Companion.STROKE_WIDTH
            paint.strokeWidth = 5f
        }
    }
}