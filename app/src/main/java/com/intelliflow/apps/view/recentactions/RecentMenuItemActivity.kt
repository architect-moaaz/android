package com.intelliflow.apps.view.recentactions


import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Html
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.intelliflow.apps.R
import com.intelliflow.apps.adapter.BottomListAdapter
import com.intelliflow.apps.model.recentactions.menuitem.Choices
import com.intelliflow.apps.model.recentactions.menuitem.RecentMenuItemModel
import com.intelliflow.apps.utils.ContextUtils
import com.intelliflow.apps.viewmodel.RecentActionsMenuItemViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class RecentMenuItemActivity : AppCompatActivity() {

    private lateinit var recentActionsMenuItemViewModel: RecentActionsMenuItemViewModel
    private var Tag: String = "RecentMenuItemActivity"
    private var menuListLayout: LinearLayout? = null
    private var btn_cancel: Button? = null
    private var btn_save: Button? = null
    private var REQUEST_IMAGE_CAPTURE = 2
    private var recentList: List<RecentMenuItemModel>? = null
    private var toolbar: Toolbar? = null
    private var btn_get_sign: Button? = null
    private var mClear: Button? = null
    private var mGetSign: Button? = null
    private var mCancel: Button? = null
    private var file: File? = null
    private var dialog: Dialog? = null
    private var imageDialog: Dialog? = null
    private var fileDialog: Dialog? = null
    private var mContent: LinearLayout? = null
    private var view: View? = null
    private var mSignature: signature? = null
    private var bitmap: Bitmap? = null
    private val requestCodeCameraPermission = 1001
    private val imageIdMap: MutableMap<String, ImageView> = mutableMapOf()
    private val fileIdMap: MutableMap<String, File> = mutableMapOf()
    private val bitMapsImageMap: MutableMap<String, Bitmap> = mutableMapOf()

    private val capturedStateImageMaps: MutableMap<String, Boolean> = mutableMapOf()
    var progressBar: ProgressBar? = null

    @SuppressLint("ResourceType", "ClickableViewAccessibility")

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_menu_item)
        progressBar = findViewById(R.id.dynamicFormProgress)
        var menuUrl = intent.extras?.get("menuUrl")
        var title = intent.extras?.get("title")
        menuUrl = menuUrl.toString().replace("http", "https")
        menuUrl = "$menuUrl/service/form/content/$title/"
        Log.d(Tag, "menuUrl $menuUrl")
        menuListLayout = findViewById(R.id.menuListLayout)

        recentActionsMenuItemViewModel =
            ViewModelProvider(this)[RecentActionsMenuItemViewModel::class.java]

        //  btn_cancel= findViewById(R.id.btn_cancel)
        btn_save = findViewById(R.id.btn_save)

        btn_save?.setOnClickListener {

            finish()
        }

        btn_cancel?.setOnClickListener {

            finish()

        }

        if (ContextUtils.isOnline(this)) {
            progressBar?.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            if (Build.VERSION.SDK_INT >= 30) {
                if (!Environment.isExternalStorageManager()) {
                    val getpermission = Intent()
                    getpermission.action =
                        Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    startActivity(getpermission)
                }
            }

            recentActionsMenuItemViewModel.getRecentMenuItem1(menuUrl)!!
                .observe(this, Observer { menuItemList ->
                    Log.d(Tag, "msg is $menuItemList")
                    progressBar?.visibility = View.INVISIBLE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    menuListLayout?.removeAllViews()
                    if (menuItemList != null) {
                        var numberOfLines = 1

                        var sortedMenuItemList = menuItemList.sortedByDescending { it.id }
                        //(compareBy { it.id })
                        Log.d(Tag, "sortedMenuItemList is $sortedMenuItemList")

                        for (menu in sortedMenuItemList) {
                            var elementType = menu.elementType
                            var fieldTYpe = menu.fieldType
                            var required = menu.required
                            var edit = menu.edit
                            var multiSelect = menu.multiSelect
                            var fieldName = menu.fieldName
                            var choices: ArrayList<Choices> = menu.choices
                            var menuId = menu.id
                            var id = 1
                            var edit_id = 1
                            val layout2 = LinearLayout(this)
                            // layout2.removeAllViews()
                            layout2.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            layout2.weightSum = 2F
                            layout2.orientation = LinearLayout.HORIZONTAL
                            layout2.background = getDrawable(R.drawable.text_bg)
                            if (elementType.equals("dropdown") || elementType.equals("number")
                                || elementType.equals("text")

                            ) {
                                id++
                                menuListLayout?.addView(layout2)
                                val lparams = LinearLayout.LayoutParams(
                                    0, 80, 1.0f
                                )
                                //  val lp = lparams as LinearLayout.LayoutParams
                                lparams.setMargins(15, 0, 5, 0)
                                val tv = TextView(this)
                                tv.layoutParams = lparams

                                if (menuId != null) {
                                    tv.id = menuId
                                }
                                tv.setPadding(15, 0, 5, 0)
                                tv.setTextColor(resources.getColor(R.color.black, null))


                                if (fieldName != null) {
                                    showHtmlTv(fieldName, tv)
                                }

                                //   tv.background =getDrawable(R.drawable.text_bg)
                                layout2?.addView(tv)

                                edit_id++
                                val tv2 = EditText(this)
                                val lparams2 = LinearLayout.LayoutParams(0, 100, 1.0f)
                                lparams2.setMargins(15, 20, 5, 10)
                                tv2.layoutParams = lparams2
                                tv2.setPadding(15, 10, 5, 5)
                                tv2.id = edit_id
                                tv2.isClickable = true
                                tv2.isEnabled = true
                                // tv2.setTextSize()
                                if (elementType.equals("number")) {
                                    tv2.inputType = InputType.TYPE_CLASS_NUMBER
                                }

                                tv2.setTextSize(0, 40.0f);
                                //  tv2.text = fieldName
                                tv2.background = getDrawable(R.drawable.text_bg)
                                if (elementType.equals("dropdown")) {
                                    tv2.setTextIsSelectable(true)
                                    tv2.showSoftInputOnFocus = false
                                    tv2.setCompoundDrawablesWithIntrinsicBounds(
                                        null,
                                        null,
                                        ContextCompat.getDrawable(this, R.drawable.ic_dropdown),
                                        null
                                    )
                                }
                                //  tv2.fo
                                layout2?.addView(tv2)

//                            tv.setOnClickListener {
//                                Log.d(Tag, "tv type is $elementType")
//                                if (elementType.equals("dropdown")) {
//
//                                    showBottomSheetDialog(choices, tv2)
//
//                                }
//
//
//                            }
                                if (elementType.equals("dropdown")) {
                                    //  tv2.isEnabled = false
                                }
                                tv2.setOnClickListener {
                                    Log.d(Tag, "tv type is $elementType")
                                    if (elementType.equals("dropdown")) {

                                        showBottomSheetDialog(choices, tv2)

                                    }

                                }

                            }

                            if (elementType.equals("file")) {

                                id++
                                menuListLayout?.addView(layout2)

                                val lparams = LinearLayout.LayoutParams(
                                    0, 100, 1.0F
                                )

                                lparams.setMargins(15, 20, 5, 10)
                                val tv = TextView(this)
                                tv.layoutParams = lparams
                                tv.id = id
                                tv.setPadding(15, 10, 5, 5)

                                tv.setTextColor(resources.getColor(R.color.black, null))

                                if (fieldName != null) {
                                    showHtmlTv(fieldName, tv)
                                }

                                layout2?.addView(tv)

                                val imageView = ImageView(this)

                                // setting height and width of imageview

                                val lparams2 = LinearLayout.LayoutParams(0, 100, 1.0f)
                                lparams2.setMargins(15, 20, 5, 10)
                                imageView.layoutParams = lparams2
                                imageView.setImageResource(R.drawable.ic_menu_gallery)
                                imageView.id = 2000
                                layout2?.addView(imageView)

//                            var preferences = getSharedPreferences("file", Context.MODE_PRIVATE)
//                            var path = preferences.getString("filepath", "")
//                            Log.d(Tag, "path is $path")
                                imageView.setOnClickListener {

                                if (capturedStateImageMaps["FILE"] == true) {
                                        // Dialog Function
                                        fileDialog = Dialog(this)
                                        // Removing the features of Normal Dialogs
                                        fileDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                        fileDialog!!.setContentView(R.layout.pdf_dialog)
                                        fileDialog!!.setCancelable(true)
                                        showFileDialog()
                                    } else {
                                    selectFile(imageView)
                                    }

                                    //   }


                                }

                            }

                            if (elementType.equals("image")) {

                                id++
                                menuListLayout?.addView(layout2)

                                val lparams = LinearLayout.LayoutParams(
                                    0, 100, 1.0F
                                )

                                lparams.setMargins(15, 20, 5, 10)
                                val tv = TextView(this)
                                tv.layoutParams = lparams
                                tv.id = id
                                tv.setPadding(15, 10, 5, 5)

                                tv.setTextColor(resources.getColor(R.color.black, null))

                                if (fieldName != null) {
                                    showHtmlTv(fieldName, tv)
                                }

                                layout2?.addView(tv)

                                val imageView = ImageView(this)

                                // setting height and width of imageview

                                val lparams2 = LinearLayout.LayoutParams(0, 100, 1.0f)
                                lparams2.setMargins(15, 20, 5, 10)
                                imageView.layoutParams = lparams2
                                imageView.setImageResource(R.drawable.ic_menu_gallery)
                                layout2?.addView(imageView)


                                imageView.setOnClickListener {

                                    if (capturedStateImageMaps["CAMERA"] == true) {
                                        // Dialog Function
                                        imageDialog = Dialog(this)
                                        // Removing the features of Normal Dialogs
                                        imageDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                        imageDialog!!.setContentView(R.layout.dialogue_image)
                                        imageDialog!!.setCancelable(true)
                                        showImageDialog()
                                    } else {
                                        selectImage(imageView)
                                    }


                                }

                            }

                            if (elementType.equals("date")) {


                                id++
                                menuListLayout?.addView(layout2)

                                val lparams = LinearLayout.LayoutParams(
                                    0, 100, 1.0f

                                )

                                //  val lp = lparams as LinearLayout.LayoutParams
                                lparams.setMargins(15, 20, 5, 10)
                                val tv = TextView(this)
                                tv.layoutParams = lparams
                                tv.id = id
                                tv.setPadding(15, 10, 5, 5)

                                tv.setTextColor(resources.getColor(R.color.black, null))
//tv.textColors = getColor(R.color.black)
                                if (fieldName != null) {
                                    showHtmlTv(fieldName, tv)
                                }
                                //   tv.background =getDrawable(R.drawable.text_bg)
                                layout2?.addView(tv)


                                val datePicker = DatePicker(this)

                                //   layout2?.addView(datePicker)


                                val tv2 = EditText(this)
                                tv2.layoutParams = lparams
                                tv2.setPadding(15, 10, 5, 5)

                                // tv2.isEnabled = false
                                tv2.id = edit_id
                                // tv2.setTextSize()
                                if (elementType.equals("number")) {
                                    tv2.inputType = InputType.TYPE_CLASS_NUMBER
                                }

                                tv2.showSoftInputOnFocus = false

                                tv2.setTextSize(0, 40.0f);
                                //  tv2.text = fieldName
                                tv2.background = getDrawable(R.drawable.text_bg)

                                tv2.setCompoundDrawablesWithIntrinsicBounds(
                                    ContextCompat.getDrawable(
                                        this,
                                        android.R.drawable.ic_menu_my_calendar
                                    ), null, null, null
                                )
                                tv2.setTextIsSelectable(true)
                                layout2?.addView(tv2)

                                val today = Calendar.getInstance()
                                datePicker.init(
                                    today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                                    today.get(Calendar.DAY_OF_MONTH)

                                ) { view, year, month, day ->
                                    val month = month + 1
                                    val msg = "You Selected: $day/${month + 1}/$year"
                                    val selectedDate: String = "$day/$month/$year"
                                    tv2.setText(selectedDate)
                                }


//                            tv2.setOnTouchListener { v, event ->
//
//                                v.onTouchEvent(event)
//                                val imm =
//                                    v.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                                imm?.hideSoftInputFromWindow(v.windowToken, 0)
//
//
//                                // when (event?.action) {
////                                    MotionEvent.ACTION_DOWN -> {
//
////                                        v.onTouchEvent(event)
////                                        val imm: InputMethodManager =
////                                            v.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
////                                        if (imm != null) {
////                                            imm.hideSoftInputFromWindow(v.windowToken, 0)
////                                        }
//                                     //   return true
//
//                                  //  }
//                             //   }
//
//                                v?.onTouchEvent(event) ?: true
//                            }

                                tv2.setOnClickListener {

                                    Log.d(Tag, "tv type is $elementType")
                                    if (elementType.equals("date")) {

                                        //  showBottomSheetDateDialog(tv2)

                                        showDatePicker(tv2)


                                    }

                                }


                            }

                            if (elementType.equals("label")) {
                                var id = 2002
                                // menuListLayout?.addView(layout2)

                                val lparams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, 100, 2.0f

                                )
                                val tv = TextView(this)
                                tv.layoutParams = lparams
                                tv.id = id
                                tv.setPadding(15, 10, 5, 5)
                                tv.gravity = Gravity.CENTER
                                tv.setTextColor(resources.getColor(R.color.black, null))
                                if (fieldName != null) {
                                    showHtmlTv(fieldName, tv)
                                }

                                menuListLayout?.addView(tv)
                            }

                            if (elementType.equals("checkbox"))
                                fieldName?.let {
                                    showDynamicCheckbox(
                                        it,
                                        choices,
                                        parentLayout = menuListLayout,
                                        layout2
                                    )
                                }

                            if (elementType.equals("radio"))
                                fieldName?.let {
                                    showDynamicRadioGroup(
                                        it,
                                        choices,
                                        parentLayout = menuListLayout,
                                        layout2
                                    )
                                }

                            if (elementType.equals("esign"))
                                fieldName?.let {
                                    showDynamicSignaturePad(
                                        it,
                                        choices,
                                        parentLayout = menuListLayout,
                                        layout2
                                    )
                                }

                            if (elementType.equals("link"))
                                fieldName?.let {
                                    showLink(
                                        it,
                                        choices,
                                        parentLayout = menuListLayout,
                                        layout2
                                    )
                                }

                            if (elementType.equals("rating"))
                                fieldName?.let {
                                    showDynamicRatingBar(
                                        it,
                                        parentLayout = menuListLayout,
                                        layout2
                                    )
                                }

                            if (elementType.equals("qrcode"))
                                fieldName?.let {
                                    showDynamicQrcode(
                                        it,
                                        parentLayout = menuListLayout,
                                        layout2
                                    )
                                }

                        }

                    } else {

                        menuListLayout?.removeAllViews()
                        var id = 1001
                        // menuListLayout?.addView(layout2)

                        val lparams = LinearLayout.LayoutParams(
                            500, 100, 2.0f
                        )
                        val tv = TextView(this)
                        tv.layoutParams = lparams
                        tv.id = id
                        tv.setPadding(15, 10, 5, 5)
                        tv.gravity = Gravity.CENTER
                        tv.setTextColor(resources.getColor(R.color.black, null))
                        tv.text = "Unable To Get the Proper Data"
                        menuListLayout?.addView(tv)
//                Toast.makeText(this, "Unable to get the proper data  ", Toast.LENGTH_LONG)
//                    .show()


                    }

                }
                )
        } else {

            progressBar?.visibility = View.INVISIBLE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            Toast.makeText(
                this@RecentMenuItemActivity,
                " You don't have Network Connection! ",
                Toast.LENGTH_SHORT
            ).show();

        }

    }

    private fun selectImage(imageView: ImageView) {
        val choice = arrayOf<CharSequence>(
            "Capture from Camera",
            "Choose from Gallery",
            "Cancel"
        )
        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        myAlertDialog.setTitle("Select Image")
        myAlertDialog.setItems(choice, DialogInterface.OnClickListener { dialog, item ->
            when {
                // Select "Choose from Gallery" to pick image from gallery
                choice[item] == "Choose from Gallery" -> {
                    imageIdMap["GALLERY"] = imageView
                    val pickFromGallery = Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    pickFromGallery.type = "image/*"
                    startActivityForResult(pickFromGallery, 1)
                }

                choice[item] == "Capture from Camera" -> {
                    captureImage(imageView)
                }

                choice[item] == "Cancel" -> {
                    myAlertDialog.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                }
            }
        })
        myAlertDialog.show()
    }

    private fun selectFile(imageView: ImageView) {
        val choice = arrayOf<CharSequence>(
            "Choose PDF File",
            "Cancel"
        )
        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        myAlertDialog.setTitle("Select File")
        myAlertDialog.setItems(choice, DialogInterface.OnClickListener { dialog, item ->
            when {

                // Select "Take Photo" to take a photo
                choice[item] == "Choose PDF File" -> {
                    val pdfType = Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    pdfType.type = "application/pdf"
                    startActivityForResult(pdfType, 0)
                }
                // Select "Cancel" to cancel the task

                choice[item] == "Cancel" -> {
                    myAlertDialog.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                }
            }
        })
        myAlertDialog.show()
    }

    private fun captureImage(imageView: ImageView) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            imageIdMap["CAMERA"] = imageView
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            //  resultLauncher.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    var someActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                //  doSomeOperations()
            }
        })

    fun openSomeActivityForResult() {
//        val intent = Intent(this, SomeActivity::class.java)
//        resultLauncher.launch(intent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                // doSomeOperations()
            }
        }


    fun showHtmlTv(fieldName: String, tv: TextView) {
        if (fieldName != null) {
            if (fieldName.contains("div")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv.text =
                        Html.fromHtml(fieldName, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    tv.text = Html.fromHtml(fieldName)
                }
            } else {
                tv.text = fieldName
            }

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        menuListLayout?.removeAllViews()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDynamicRadioGroup(
        fieldName: String,
        choices: ArrayList<Choices>,
        parentLayout: LinearLayout?,
        subLayout: LinearLayout
    ) {

        parentLayout?.addView(subLayout)
        val lparams = LinearLayout.LayoutParams(
            0, 100, 1.0f
        )
        lparams.setMargins(15, 20, 5, 10)
        val tv = TextView(this)
        tv.layoutParams = lparams

        tv.setPadding(15, 10, 5, 5)

        tv.setTextColor(resources.getColor(R.color.black, null))
        //tv.textColors = getColor(R.color.black)
//        if (fieldName != null) {
//            if (fieldName.contains("div")) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    tv.text =
//                        Html.fromHtml(fieldName, Html.FROM_HTML_MODE_COMPACT);
//                } else {
//                    tv.text = Html.fromHtml(fieldName);
//                }
//            } else {
//                tv.text = fieldName
//            }
//
//        }

        showHtmlTv(fieldName, tv)

        // tv2.background = getDrawable(R.drawable.text_bg)
        subLayout?.addView(tv)

        val radioGroup = RadioGroup(this)
        val params = LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.MATCH_PARENT, 1.0f
        )
        params.setMargins(5, 0, 0, 0)
        radioGroup.layoutParams = params
        var i = 2
        for (choice in choices) {

            val radioButton1 = RadioButton(this)
//            radioButton1.layoutParams = LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
            radioButton1.text = choice.choice
            radioGroup.addView(radioButton1)
        }

        subLayout.addView(radioGroup)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDynamicCheckbox(
        fieldName: String,
        choices: ArrayList<Choices>,
        parentLayout: LinearLayout?,
        subLayout: LinearLayout
    ) {

        parentLayout?.addView(subLayout)
        val lparams = LinearLayout.LayoutParams(
            0, 100, 1.0f
        )
        lparams.setMargins(15, 20, 0, 10)
        val tv = TextView(this)
        tv.layoutParams = lparams
//        if (menuId != null) {
//            tv.id = menuId
//        }
        tv.setPadding(15, 10, 0, 5)


        tv.setTextColor(resources.getColor(R.color.black, null))
        //tv.textColors = getColor(R.color.black)
        if (fieldName != null) {
            showHtmlTv(fieldName, tv)
        }
        // tv2.background = getDrawable(R.drawable.text_bg)
        subLayout?.addView(tv)


        var cSize = choices.size

        var i = 2
        var d = 0

        var cbLayout = LinearLayout(this)

        cbLayout.layoutParams = lparams

        for (choice in choices) {


            // if (d == 0){

            val cb = CheckBox(this)
            Log.d(Tag, "d is $d")
//                lparams.setMargins(5, 0, 0, 0)
//                cb.layoutParams = lparams
            cb.text = choice.choice
            cb.id = i + 6
            cbLayout.addView(cb)
            //}
//            else{
//                val cb = CheckBox(this)
//                Log.d(Tag,"d is $d")
//                lparams.setMargins(0, 0, 0, 0)
//                cb.layoutParams = lparams
//                cb.text = choice.choice
//                cb.id = i + 6
//                subLayout.addView(cb)
//            }


            d += 1
        }

        subLayout?.addView(cbLayout)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDynamicRatingBar(
        fieldName: String,
        parentLayout: LinearLayout?,
        subLayout: LinearLayout
    ) {
        parentLayout?.addView(subLayout)
        val lparams = LinearLayout.LayoutParams(
            0, 100, 1.0f
        )
        lparams.setMargins(15, 10, 5, 0)
        val tv = TextView(this)
        tv.layoutParams = lparams
//        if (menuId != null) {
//            tv.id = menuId
//        }
        tv.setPadding(20, 20, 5, 0)
        tv.setTextColor(resources.getColor(R.color.black, null))
        //tv.textColors = getColor(R.color.black)
        if (fieldName != null) {
            showHtmlTv(fieldName, tv)
        }
        // tv2.background = getDrawable(R.drawable.text_bg)
        subLayout?.addView(tv)
        val rBar =
            layoutInflater.inflate(R.layout.rating_bar, subLayout, false) as AppCompatRatingBar
        lparams.setMargins(5, 20, 0, 0)
        rBar.layoutParams = lparams
        subLayout.addView(rBar)


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDynamicQrcode(
        fieldName: String,
        parentLayout: LinearLayout?,
        subLayout: LinearLayout
    ) {
        parentLayout?.addView(subLayout)
        val lparams = LinearLayout.LayoutParams(
            0, 100, 1.0f
        )
        lparams.setMargins(15, 20, 5, 10)
        val tv = TextView(this)
        tv.layoutParams = lparams
//        if (menuId != null) {
//            tv.id = menuId
//        }
        /*tv.gravity=Gravity.LEFT*/
        tv.setPadding(15, 10, 5, 5)
        tv.setTextColor(resources.getColor(R.color.black, null))
        //tv.textColors = getColor(R.color.black)
        // if (fieldName != null) {
        showHtmlTv(fieldName, tv)
        // }
        // tv2.background = getDrawable(R.drawable.text_bg)
        subLayout?.addView(tv)
        val qrImage = ImageView(this)
        val layoutParams = LinearLayout.LayoutParams(
            0,
            100, 1.0f
        )
        layoutParams.setMargins(0, 0, 5, 0)
        layoutParams.gravity = Gravity.LEFT
        qrImage.layoutParams = layoutParams

        qrImage.setImageResource(R.drawable.ic_menu_gallery)

        // qrImage.gravity=Gravity.LEFT
        subLayout.addView(qrImage)

        qrImage.setOnClickListener {
            //  startActivity(Intent(this@RecentMenuItemActivity, QrCodeActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDatePicker(etDate: EditText) {

        val newCalendar = Calendar.getInstance()

        val startDate = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                etDate.setTextColor(resources.getColor(R.color.black, null))
                etDate.setText(selectedDate)
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )

        startDate.show()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDynamicSignaturePad(
        fieldName: String,
        choices: ArrayList<Choices>,
        parentLayout: LinearLayout?,
        subLayout: LinearLayout
    ) {

        parentLayout?.addView(subLayout)
        val lparams = LinearLayout.LayoutParams(
            0, 100, 1.0f
        )
        lparams.setMargins(15, 20, 5, 10)
        val tv = TextView(this)
        tv.layoutParams = lparams
        lparams.setMargins(15, 20, 5, 10)
        tv.setPadding(15, 10, 5, 5)

        tv.setTextColor(resources.getColor(R.color.black, null))

        if (fieldName != null) {
            showHtmlTv(fieldName, tv)
        }

        subLayout?.addView(tv)

        val iv = ImageView(this)
        iv.layoutParams = lparams
        iv.setPadding(15, 10, 5, 5)
        //  et.setTextColor(resources.getColor(R.color.black, null))
        iv.background = getDrawable(R.drawable.text_bg)
        // iv.isEnabled = false

        subLayout?.addView(iv)

        // Creating Separate Directory for saving Generated Images
        var DIRECTORY = Environment.getExternalStorageDirectory().path + "/DigitSign/"
        var pic_name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        var StoredPath = "$DIRECTORY$pic_name.png"


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
        iv!!.setOnClickListener { // Function call for Digital Signature
            dialog_action(StoredPath, iv)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLink(
        fieldName: String,
        choices: ArrayList<Choices>,
        parentLayout: LinearLayout?,
        subLayout: LinearLayout
    ) {

        parentLayout?.addView(subLayout)
        val lparams = LinearLayout.LayoutParams(
            0, 100, 1.0f
        )
        lparams.setMargins(15, 20, 5, 10)
        val tv = TextView(this)
        tv.layoutParams = lparams
        lparams.setMargins(15, 20, 5, 10)
        tv.setPadding(15, 10, 5, 5)

        tv.setTextColor(resources.getColor(R.color.black, null))

        if (fieldName != null) {
            showHtmlTv(fieldName, tv)
        }

        subLayout?.addView(tv)

        var cSize = choices.size

        var i = 2
        val et = EditText(this)
        et.layoutParams = lparams
        et.setPadding(15, 10, 5, 5)
        et.setTextColor(resources.getColor(R.color.black, null))
        et.background = getDrawable(R.drawable.text_bg)
        et.isEnabled = false
        subLayout?.addView(et)


    }

    // Intent for navigating to the files
    private fun selectPdf(type: String) {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        if (type == "image") {
            pdfIntent.type = "/image"
        } else if (type == "pdf") {
            pdfIntent.type = "application/pdf"
        }
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    private fun showBottomSheetDialog(choice: ArrayList<Choices>, et: EditText) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottomsheet_actions)

        val recyclerView =
            bottomSheetDialog.findViewById<RecyclerView>(R.id.notification_recycler_view1)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        val bottomListAdapter = BottomListAdapter()

        if (recyclerView != null) {
            recyclerView.adapter = bottomListAdapter
        }

        bottomListAdapter.setDataList(choice)

        recyclerView?.addOnItemTouchListener(
            RecyclerItemClickListenr(
                this,
                recyclerView,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun onItemClick(view: View, position: Int) {

                        // getColor(R.color.black)

                        et.setTextColor(resources.getColor(R.color.black, null))
                        et.setText(choice[position].label.toString())
                        // et.text=choice.get(position).toString()
                        bottomSheetDialog.dismiss()
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        TODO("do nothing")
                    }
                })
        )

        bottomSheetDialog.show()
    }

    private fun showBottomSheetDateDialog(et: EditText) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.show_date)

        val datePicker = DatePicker(this)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        datePicker.layoutParams = layoutParams

        val today = Calendar.getInstance()
        datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "$day/$month/$year"

            et.setText(msg)

            //     Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()

        }

        val dateLayout = findViewById<LinearLayout>(R.id.dateLayout)
        // add datePicker in LinearLayout
        dateLayout?.addView(datePicker)

        bottomSheetDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(
            Tag,
            " requestCode $requestCode, REQUEST_IMAGE_CAPTURE $REQUEST_IMAGE_CAPTURE , resultCode $resultCode , RESULT_OK  $RESULT_OK   "
        )

        // For loading Image
        if (resultCode != RESULT_CANCELED) {

            when (requestCode) {

                0 -> if (resultCode == RESULT_OK && data != null) {
                    val fileSelected = data?.data.toString()
                    //as File?
                    Log.d(Tag, " fileSelected $fileSelected")
                    var preferences = getSharedPreferences("file", Context.MODE_PRIVATE)
                    var editor = preferences.edit()
                    editor.putString("filepath", fileSelected)
                    editor.commit()

                    imageIdMap["FILE"]?.setBackgroundColor(
                        resources.getColor(
                            R.color.black,
                            null
                        )
                    )
                    imageIdMap["FILE"]?.setImageResource(com.google.android.material.R.drawable.design_password_eye)
                  //  imageIdMap["FILE"]?.setImageBitmap(imageBitmap)

                    capturedStateImageMaps["FILE"] = true

                  var  file = File(fileSelected)
                    file?.let {



                        fileIdMap["FILE"] = it

                        saveFile(it) }
                }

                1 -> if (resultCode == RESULT_OK && data != null) {
                    val imageUri: Uri? =  data.data
                    Log.d(Tag, "imageBitmap $imageUri")
                    imageIdMap["GALLERY"]?.setBackgroundColor(
                        resources.getColor(
                            R.color.black,
                            null
                        )
                    )
                    imageIdMap["GALLERY"]?.setImageResource(com.google.android.material.R.drawable.design_password_eye)
                    imageIdMap["GALLERY"]?.setImageURI(imageUri)
                 //   bitMapsImageMap["GALLERY"] = imageBitmap
                    capturedStateImageMaps["GALLERY"] = true

                  if(imageUri!=null) {
                      file = File(imageUri.path)
                      file?.let { saveFile(it) }
                  }
                }

                2 -> if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    Log.d(Tag, "imageBitmap $imageBitmap")
                    imageIdMap["CAMERA"]?.setBackgroundColor(
                        resources.getColor(
                            R.color.black,
                            null
                        )
                    )
                    imageIdMap["CAMERA"]?.setImageResource(com.google.android.material.R.drawable.design_password_eye)
                    //imageIdMap["CAMERA"]?.setImageBitmap(imageBitmap)
                    bitMapsImageMap["CAMERA"] = imageBitmap
                    capturedStateImageMaps["CAMERA"] = true

                    var file = bitmapToFile(imageBitmap, "selectedFile")
                    if (file != null) {
                        saveFile(file)
                    }
                }
            }
        }

        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {

            }
        }
    }

    fun bitmapToFile(bitmap: Bitmap, fileName: String): File? {

        val file_path = Environment.getExternalStorageDirectory().absolutePath
        val dir = File(file_path)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir,"map.jpeg")
        file.createNewFile()
        val fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
        fOut.flush()
        fOut.close()
         return file

    }

    private fun saveFile(file: File) {
        Log.d(Tag,"file is $file")
        if (file != null) {
            recentActionsMenuItemViewModel.sendFileData(
                "https://cds.intelliflow.in/testapp/upload",
                file
            )!!
                .observe(this, Observer { fileUploadResponse ->
                    if (fileUploadResponse != null) {

                        var fileName = fileUploadResponse?.file?.filename

                        Log.d(Tag, "fileName $fileName")

                    }
                    else{
                        Log.d(Tag, "fileName error  $fileUploadResponse")
                    }

                })
        }


    }

    // Function for Digital Signature
    private fun dialog_action(StoredPath: String, iv: ImageView) {
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
            val imgFile = File(StoredPath)

            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                // val myImage = findViewById<View>(R.id.imageviewTest) as ImageView
                iv.setImageBitmap(myBitmap)
            }
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

    // Function for Digital Signature
    private fun showImageDialog() {
        //    mContent = imageDialog!!.findViewById<View>(R.id.linearLayout) as LinearLayout

        var imageView = imageDialog!!.findViewById<View>(R.id.dialogue_iv) as ImageView

        imageView.setImageBitmap(bitMapsImageMap["CAMERA"])

        imageDialog!!.show()
    }

    private fun showFileDialog() {
        //    mContent = imageDialog!!.findViewById<View>(R.id.linearLayout) as LinearLayout

        var pdfView = fileDialog!!.findViewById<View>(R.id.pdfDialogView) as PDFView

        var pdfPath = fileIdMap["FILE"]
        Log.d(Tag,"pdfPath $pdfPath")
        val uriPath = Uri.fromFile(pdfPath)
        pdfView?.fromUri(uriPath)
            ?.password(null)
            ?.defaultPage(0)
            ?.enableSwipe(true)
            ?.swipeHorizontal(false)
            ?.enableDoubletap(true)
            ?.onPageError { page, _ ->

                Log.d(Tag," error is $page")

//                Toast.makeText(
//                    this@PdfViewActivity,
//                    "Error at page: $page", Toast.LENGTH_LONG
//                ).show()

            }
            ?.load()



        fileDialog!!.show()
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@RecentMenuItemActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }


    override fun onDestroy() {
        super.onDestroy()
     //   menuListLayout?.removeAllViews()
        // cameraSource.stop()
    }

}

