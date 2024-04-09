package com.intelliflow.apps.view.login


import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
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
import com.intelliflow.apps.view.dashboard.Dashboard
import com.intelliflow.apps.view.password.ForgotPassword
import com.intelliflow.apps.viewmodel.LoginActivityViewModel
import java.util.*
import java.util.concurrent.Executor
import java.util.regex.Pattern
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class LoginActivity1 : AppCompatActivity() {

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

    var localeUpdatedContext: ContextWrapper? = null

    var mContext:Context? = null
    lateinit var locale: Locale
    private var currentLanguage = "ar"
    private var currentLang: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity_main1)

        forgetPassword = findViewById<TextView>(R.id.fgt_pswd)
        var loginBtn = findViewById<Button>(R.id.buttonLogin)
        spinner = findViewById(R.id.language1)
        cBox = findViewById(R.id.check_box)

        forgetPassword.setOnClickListener {
            Intent(this, ForgotPassword::class.java).also {
                startActivity(it)
                finish()
            }
        }

        val adapter = ArrayAdapter(
            this@LoginActivity1, R.layout.spinner_list1, R.id.tv_english1, resources.getStringArray(R.array.languages)
        )
        spinner?.dropDownWidth = 300
        spinner?.adapter = adapter
        spinner?.setSelection(2)
        btnAuth = findViewById(R.id.btnAuth)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this@LoginActivity1,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@LoginActivity1, "Error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    Toast.makeText(
                        this@LoginActivity1,
                        "Authentication Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                    Toast.makeText(this@LoginActivity1, "Authentication Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()
        btnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

            //selectedlan





        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                Log.d(Tag,"position1 is $position")

//                if(position==1){
//
////                    var preferences =getSharedPreferences("language",Context.MODE_PRIVATE)
////                    var selectedLanguage =  preferences.getString("selectedlan","")
//
//                    val sharedPreferences: SharedPreferences = PreferenceManager
//                        .getDefaultSharedPreferences(this@LoginActivity1)
//                    var editor=sharedPreferences.edit()
//                    editor.putString("selectedlan","ar")
//                    editor.commit()
//                  //  recreate()
//                       setLocale("ar")
//                //   attachBaseContext(localeUpdatedContext)
//                }

                if(position==1){

//                    var preferences =getSharedPreferences("language",Context.MODE_PRIVATE)
//                    var selectedLanguage =  preferences.getString("selectedlan","")

                    val sharedPreferences: SharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(this@LoginActivity1)
                    var editor=sharedPreferences.edit()
                    editor.putString("selectedlan","en")
                    editor.commit()
                    //  recreate()
                  //  setLocale("en")
                    val refresh = Intent(  mContext, LoginActivity::class.java)
                    // refresh.putExtra(currentLang, localeName)
                    startActivity(refresh)
                }
//                Toast.makeText(
//                    this@LoginActivity1,
//                    "Langauge selected: ${adapterView?.getItemAtPosition(position).toString()}",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        //    val editTextPassword = findViewById<EditText>(R.id.etPassword)
        //  val password = editTextPassword.text.toString().trim()
        val textInputPassword: TextInputLayout = findViewById(R.id.textPasswordLayout)

        sharedPreference = getSharedPreferences("emailSave", Context.MODE_PRIVATE)
        var savedEmail = sharedPreference?.getString("email", "").toString()
        Log.d(Tag, "savedEmail  before if is $savedEmail")
        //  String data1 = sharedPreference.getString("maxDead", "").t
        if (savedEmail != null && savedEmail.isNotEmpty() && savedEmail != "null") {

           // Log.d(Tag, "savedEmail after if  is $savedEmail")
//            var decryptedVal = AESEncyption.decrypt(savedEmail!!)
//            Log.d(Tag, "savedEmail decryption $decryptedVal")
            editTextEmail.setText(savedEmail)

        }

        loginBtn.setOnClickListener {

            email = editTextEmail.text.toString().trim()
            // get data from textInputPassword
            password = textInputPassword.editText!!.text.toString()
            if (email!!.isEmpty()) {
                editTextEmail.error = "Email Required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if (email!!.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.error = "Enter Valid EmailID"
                editTextEmail.requestFocus()
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
                    signIn(email!!, it)

                }
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = getColor(R.color.colorPrimaryDark)
        }


        cBox.setOnClickListener(View.OnClickListener {

            if (cBox.isChecked) {
                email = editTextEmail.text.toString().trim()
                if (email != null) {
                   // var encryptedEmail = AESEncyption.encrypt(email!!)
                    val editor = sharedPreference?.edit()
                    editor?.putString("email", email)
                    editor?.commit()
                }
            } else {

                val editor = sharedPreference?.edit()
                editor?.putString("email", " ")
                editor?.commit()

            }


        })


    }

    override fun attachBaseContext(newBase: Context?) {
        newBase ?: return
        val sharedPreferences: SharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(newBase)
    //    currentLanguage= sharedPreferences.getString("selectedlan", "en").toString()
        currentLanguage="ar"
        val localeToSwitchTo = Locale(currentLanguage)
        val localeUpdatedContext: ContextWrapper? =
            newBase?.let { ContextUtils.updateLocale(it, localeToSwitchTo) }
        mContext = newBase
       // if(localeUpdatedContext!=null)
        super.attachBaseContext(localeUpdatedContext)
    }

    private fun setLocale(localeName: String) {

        if (localeName != currentLanguage) {

            val locale = Locale(localeName)
            Locale.setDefault(locale)
            val resources: Resources = this.resources
            val config: Configuration = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
          //  recreate()
            val refresh = Intent(  this, LoginActivity1::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)

        }

        else {
            Toast.makeText(
                this@LoginActivity1, "Language, , already, , selected)!", Toast.LENGTH_SHORT).show();
        }

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

            if(accessinfo!=null) {


                val msg = accessinfo.accessInfo
                var userInfo = accessinfo.UserInfo
                var access = userInfo?.get(0)?.access
                var email = userInfo?.get(0)?.email
                var firstName = userInfo?.get(0)?.firstName
                var lastName = userInfo?.get(0)?.lastName

                Log.d(Tag, "userInfo is $userInfo")

                Log.d(Tag, "access is $access")

                if (msg != null) {
                    var saveFirstName=firstName?.toString()
                    var saveLastName = lastName?.toString()
                    var saveEmail = email?.toString()
                    val editor = sharedPreference?.edit()
                    editor?.putBoolean("isLoggedin", true)
                    editor?.putString("firstName", saveFirstName)
                    editor?.putString("lastName", saveLastName)
                    editor?.putString("email", saveEmail)
                    editor?.commit()
                    var intent = Intent(this@LoginActivity1, Dashboard::class.java)
//                    intent.putExtra("firstName", firstName?.toString())
//                    intent.putExtra("lastName", lastName?.toString())
//                    intent.putExtra("email", email?.toString())


                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Please Enter Existed Login Details ", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else{
                Toast.makeText(this, "Unable to Login at this moment ", Toast.LENGTH_LONG)
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

}
