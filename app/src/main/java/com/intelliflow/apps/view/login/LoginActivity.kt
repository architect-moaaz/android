package com.intelliflow.apps.view.login


import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.android.material.textfield.TextInputLayout
import com.intelliflow.apps.R
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.utils.ContextUtils
import com.intelliflow.apps.utils.LocaleHelper
import com.intelliflow.apps.view.dashboard.Dashboard
import com.intelliflow.apps.view.password.ForgotPassword
import com.intelliflow.apps.viewmodel.LoginActivityViewModel
import java.util.*
import java.util.concurrent.Executor
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class LoginActivity : AppCompatActivity() {

    private val Tag = "LoginActivity"
    private lateinit var btnAuth: Button  //fgt_pswd
    private lateinit var loginBtn: Button
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    lateinit var loginActivityViewModel: LoginActivityViewModel
    private lateinit var forgetPassword: TextView  //fgt_pswd
    var spinner: Spinner? = null

    private lateinit var cBox: CheckBox

    private var sharedPreference: SharedPreferences? = null

    private var editTextEmail: EditText? = null

    private var loginTitle: TextView? = null


    // defining our own password pattern
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"
    )
    var email: String? = null
    var password: String? = null
    private var currentLanguage = "en"
    private var currentLang: String? = null
    private lateinit  var textInputPassword: TextInputLayout
   // var currentLanguage = "en", var currentLang:kotlin.String? = null
    var progressBar :ProgressBar ? =null

    override fun attachBaseContext(newBase: Context?) {
        newBase ?: kotlin.run {
            super.attachBaseContext(newBase)
            return
        }
        super.attachBaseContext(updateLanguage(newBase, getSelectedLanguage(newBase)))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity_main)
       // mContext=this
        progressBar = findViewById(R.id.loginProgress)
        Log.d(Tag, " internet result ${ContextUtils.isOnline(this)} ")
        forgetPassword = findViewById<TextView>(R.id.fgt_pswd)
        loginBtn = findViewById<Button>(R.id.buttonLogin)
        spinner = findViewById(R.id.language)
        cBox = findViewById(R.id.check_box)
        editTextEmail = findViewById<EditText>(R.id.editTextEmail)
         textInputPassword = findViewById(R.id.textPasswordLayout)
        currentLanguage = intent.getStringExtra(currentLang).toString()
        var login="Login"
        forgetPassword.setOnClickListener {
            Intent(this, ForgotPassword::class.java).also {
                startActivity(it)
            }
        }

        textInputPassword?.editText?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                textInputPassword?.hint = ""
            } else {
                textInputPassword?.editText?.hint = "Password"
            }
        }


        val adapter = ArrayAdapter(
            this@LoginActivity,
            R.layout.spinner_list,
            R.id.tv_english,
            resources.getStringArray(R.array.languages)
        )

//        val adapter1 = ArrayAdapter(
//            this@LoginActivity,
//            R.layout.spinner_list,
//            R.id.tv_english,
//            resources.getStringArray(R.array.languages1)
//        )
        spinner?.dropDownWidth = 300
        spinner?.adapter = adapter
        spinner?.setSelection(0, false)
//        btnAuth = findViewById(R.id.btnAuth)
        loginTitle = findViewById(R.id.logintitle)



//     var title = login.substring(0,1).uppercase()
//     title += header.getCategoryTitle().toSubString(1).toLowerCase);

      //  headerButton.setText(title);

        loginBtn.text = "Login"
        
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this@LoginActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@LoginActivity, "Error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                    Toast.makeText(this@LoginActivity, "Authentication Failed", Toast.LENGTH_SHORT)
                        .show()
                }

            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()

//        btnAuth.setOnClickListener {
//            biometricPrompt.authenticate(promptInfo)
//        }
        //   val language = findViewById<Spinner>(R.id.language)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                //  spinner?.setSelection(1)
                Log.d(Tag, "position is $position")
                when(position) {
                    1 -> {
                        saveSelectedLanguage(this@LoginActivity, "en")
                        spinner?.onItemSelectedListener = null
                        spinner?.setSelection(0, false)
                        recreate()
                    }
                    2 -> {
                        saveSelectedLanguage(this@LoginActivity, "ar")
                        spinner?.onItemSelectedListener = null
                        spinner?.setSelection(0, false)
                        recreate()
                    }
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        sharedPreference = getSharedPreferences("emailSave", Context.MODE_PRIVATE)
      var isSelected=  sharedPreference?.getBoolean("isSave",false)
        if(isSelected == true) {
            var savedEmail = sharedPreference?.getString("email", "").toString()
            Log.d(Tag, "savedEmail  before if is $savedEmail")
            //  String data1 = sharedPreference.getString("maxDead", "").t
            if (savedEmail != null && savedEmail.isNotEmpty() && savedEmail != "null") {

                // Log.d(Tag, "savedEmail after if  is $savedEmail")
//            var decryptedVal = AESEncyption.decrypt(savedEmail!!)
//            Log.d(Tag, "savedEmail decryption $decryptedVal")
                editTextEmail?.setText(savedEmail)

            }
        }
        loginBtn.setOnClickListener {
            if (cBox.isChecked) {
                email = editTextEmail?.text.toString().trim()
                if (email != null) {
                    // var encryptedEmail = AESEncyption.encrypt(email!!)
                    val editor = sharedPreference?.edit()
                    editor?.putBoolean("isSave",true)
                    editor?.putString("email", email)
                    editor?.commit()
                }
            } else {

                val editor = sharedPreference?.edit()
                editor?.putBoolean("isSave",false)
                editor?.putString("email", " ")
                editor?.commit()

            }
            email = editTextEmail?.text.toString().trim()
            // get data from textInputPassword
            password = textInputPassword.editText!!.text.toString()
            if (email!!.isEmpty()) {
                editTextEmail?.error = "Email Required"
                editTextEmail?.requestFocus()
                return@setOnClickListener
            }

            if (email!!.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail?.error = "Enter Valid EmailID"
                editTextEmail?.requestFocus()
                return@setOnClickListener
            }

            if (password!!.isEmpty()) {
                textInputPassword.editText!!.error = "Password Required"
                textInputPassword.editText!!.requestFocus()
                return@setOnClickListener
            }


            if (validatePassword(textInputPassword.editText!!, password!!)) {

                var encryptedPassword = encryptionVal(password!!, "encrypt")

                Log.d(Tag, "encrypt password " + encryptedPassword.toString())

                encryptedPassword?.let {
                    if(ContextUtils.isOnline(this)) {
                        progressBar?.visibility = View.VISIBLE
                        window.setFlags(
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        )
                        signIn(email!!, it)
                    }
                    else{

                        progressBar?.visibility=View.INVISIBLE
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                        Toast.makeText(
                            this@LoginActivity, " You don't have Network Connection! ", Toast.LENGTH_SHORT
                        ).show();

                    }
                }
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = getColor(R.color.colorPrimaryDark)
        }

        cBox.setOnClickListener(View.OnClickListener {

            if (cBox.isChecked) {
                email = editTextEmail?.text.toString().trim()
                if (email != null) {
                    // var encryptedEmail = AESEncyption.encrypt(email!!)
                    val editor = sharedPreference?.edit()
                    editor?.putBoolean("isSave",true)
                    editor?.putString("email", email)
                    editor?.commit()
                }
            } else {

                val editor = sharedPreference?.edit()
                editor?.putBoolean("isSave",false)
                editor?.putString("email", " ")
                editor?.commit()

            }

        })

        progressBar?.isIndeterminate = true

        progressBar?.isClickable=false

      //  textInputPassword.setColorFilter(resources.getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN)




    }

    private fun updateLanguage(mContext: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(mContext.resources.configuration)
        config.setLocale(locale)
        mContext.resources.updateConfiguration(config,
            mContext.resources.displayMetrics
        )
        return mContext.createConfigurationContext(config)
    }

    private fun saveSelectedLanguage(context: Context, language: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("selected_language", language).apply()
    }

    private fun getSelectedLanguage(context: Context):String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("selected_language", "en") ?: "en"
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun validatePassword(password: EditText, passwordInput: String): Boolean {

        if (passwordInput.isEmpty()) {
            password.error = "Field can not be empty"
            return false
        } else
            if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
                password.error = "Password is too weak"
                return false
            } else {
                password.error = null
                return true
            }
    }


    private fun signIn(username: String, password: String) {

        Log.d(Tag, "username is $username password is $password")

        loginActivityViewModel = ViewModelProvider(this)[LoginActivityViewModel::class.java]

        val signInInfo = SignInBody(password, username)

        loginActivityViewModel.getUser(signInInfo)!!.observe(this, Observer { accessinfo ->

            progressBar?.visibility=View.INVISIBLE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            val msg = accessinfo.accessInfo
            if (msg != null) {
                var userInfo = accessinfo.UserInfo
                var access = userInfo?.get(0)?.access
                var email = userInfo?.get(0)?.email
                var firstName = userInfo?.get(0)?.firstName
                var lastName = userInfo?.get(0)?.lastName
                Log.d(Tag, "userInfo is $userInfo")
                Log.d(Tag, "access is $access")

                if (userInfo != null) {
                    var saveFirstName = firstName?.toString()
                    var saveLastName = lastName?.toString()
                    var saveEmail = email?.toString()
                    val editor = sharedPreference?.edit()
                    editor?.putBoolean("isLoggedin", true)
                    editor?.putString("firstName", saveFirstName)
                    editor?.putString("lastName", saveLastName)
                    editor?.putString("email", saveEmail)
                    editor?.commit()
                    var intent = Intent(this@LoginActivity, Dashboard::class.java)
                    startActivity(intent)
                    finish()
                }

                else {

                    Toast.makeText(this, "Entered Email-id or Password is wrong ", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(this, "Entered Email-id or Password is wrong ", Toast.LENGTH_LONG)
                    .show()
            }
        }

        )

    }

    private fun encrypt(
        key: String,
        initVector: String,
        value: String,
        encryptType: String
    ): String? {
        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
            if (encryptType == "encrypt") {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
                val encrypted = cipher.doFinal(value.toByteArray())
                return String(Base64.encode(encrypted, 0))
            } else if (encryptType == "decrypt") {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
                val encrypted = cipher.doFinal(value.toByteArray())
                return String(Base64.decode(encrypted, 0))
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun encryptionVal(password: String, type: String): String? {
        val key = "6fa979f20126cb08aa645a8f495f6d85" // 128 bit key
        val initVector = "I8zyA4lVhMCaJ5Kg" // 16 bytes IV
        var encryptedVal = encrypt(key, initVector, password, type)
        return encryptedVal
        Log.d(Tag, "encryptedkey $encryptedVal")
    }

    object AESEncyption {

        const val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
        const val salt = "QWlGNHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
        const val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX

        fun encrypt(strToEncrypt: String): String? {
            try {
                val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec = PBEKeySpec(
                    secretKey.toCharArray(),
                    Base64.decode(salt, Base64.DEFAULT),
                    10000,
                    256
                )
                val tmp = factory.generateSecret(spec)
                val secretKey = SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
                return Base64.encodeToString(
                    cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)),
                    Base64.DEFAULT
                )
            } catch (e: Exception) {
                println("Error while encrypting: $e")
            }
            return null
        }

        fun decrypt(strToDecrypt: String): String? {
            try {

                val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.URL_SAFE))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec = PBEKeySpec(
                    secretKey.toCharArray(),
                    Base64.decode(salt, Base64.URL_SAFE),
                    10000,
                    256
                )
                val tmp = factory.generateSecret(spec);
                val secretKey = SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
                return String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.URL_SAFE)))
            } catch (e: Exception) {
                println("Error while decrypting: $e");
            }
            return null
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }



}
