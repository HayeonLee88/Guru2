package com.android.firebaseauth.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.firebaseauth.R
import kotlinx.android.synthetic.main.activity_create_account.*
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.gson.internal.bind.TypeAdapters.URL
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.http.Field
import retrofit2.http.POST
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class CreateAccountActivity : AppCompatActivity() {

    private val IP_ADDRESS = "10.0.2.2"
    private val TAG = "phptest"

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)

        onStart()

        btnCreateAccount.setOnClickListener {
            signIn()
        }

        btnSignIn2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            Toast.makeText(this, "로그인 해주세요", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /* 로그인 한 유저가 있는지 알아보기*/

    override fun onStart() {
        super.onStart()
        var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser?
        user = auth.currentUser

        //로그인 했다면 홈 화면으로 가기
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            Toast.makeText(this, "반가워요", Toast.LENGTH_SHORT).show()
        }
    }

    /*이메일, 비밀번호, 비밀번호 확인이 NULL 값이 아님을 확인하는 메소드*/
    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty() &&
            etPassword.text.toString().trim().isNotEmpty() &&
            etConfirmPassword.text.toString().trim().isNotEmpty()

    /*서울여자대학교 이메일인지 확인*/
    private fun confirmEmail(): Boolean {
        var confirm = false
        if(etEmail.text.toString().trim().contains("@")){
            Toast.makeText(this, "서울여대 이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
        }
        else{
            confirm = true
        }
        return confirm
    }

    /*비밀번호와 비밀번호 확인 문자의 동일함을 확인하는 메소드*/
    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()
        ) {//비밀번호와 비밀번호 확인 문자가 동일한 경우
            identical = true
        } else if (!notEmpty()) { //이메일, 비밀번호, 비밀번호 확인 중 NULL이 있는 경우
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} 을(를) 입력해주세요"
                }
            }
        } else { //비밀번호와 비밀번호 확인 문자가 동일하지 않을 경우
            Toast.makeText(this, "비밀번호가 동일하지 않습니다!", Toast.LENGTH_SHORT).show()
        }
        return identical
    }

    /*계정 생성시 로그인 되는 메소드*/
    private fun signIn() {
        if (identicalPassword() && confirmEmail()) {

            // identicalPassword()는 입력된 정보가 모두 null이 아니면서 비밀번호와 비밀번호 확인이 동일할 경우에만 true 반환
            userEmail = etEmail.text.toString().trim() + "@swu.ac.kr"
            userPassword = etPassword.text.toString().trim()

            /*계정 생성*/
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "회원 가입 완료! 메일 인증을 해주세요",
                            Toast.LENGTH_SHORT
                        ).show()
                        sendEmailVerification()
                        FirebaseAuth.getInstance().signOut()

                        val task = InsertData()
                        task.execute("http://$IP_ADDRESS/SignUp.php", userEmail, userPassword)
                    }
                }
        } else {
            Toast.makeText(this, "다시 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

/* 새로 가입된 유저에게 인증 메일 보내기 firebase user가 not null일 경우에 보내짐*/

    private fun sendEmailVerification() {

        //인증 메일을 보내는 actionCodeSettings 메소드
        val actionCodeSettings = actionCodeSettings {
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            url = "https://www.example.com/finishSignUp?cartId=1234"
            // This must be true
            handleCodeInApp = true
            setIOSBundleId("com.example.ios")
            setAndroidPackageName(
                "com.example.android",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */
            )
        }
        //인증 메일이 보내졌을 시 뜨는 Toast 매세지
        FirebaseAuth.getInstance().currentUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "$userEmail 에 이메일을 보냈습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private class InsertData : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg params: String?): String {

            val serverURL: String? = params[0]
            val email: String? = params[1]
            val password: String? = params[2]

            val postParameters: String = "email=$email&password=$password"

            try {
                val url = URL(serverURL)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.connect()


                val outputStream: OutputStream = httpURLConnection.outputStream
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()

                val responseStatusCode: Int = httpURLConnection.responseCode


                val inputStream: InputStream
                inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream
                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()
                var line: String? = null

                while (bufferedReader.readLine().also({ line = it }) != null) {
                    sb.append(line)
                }

                bufferedReader.close();

                return sb.toString();

            } catch (e: Exception) {
                return "Error" + e.message
            }

        }

    }

}