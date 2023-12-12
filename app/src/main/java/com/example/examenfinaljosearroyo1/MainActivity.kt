package com.example.examenfinaljosearroyo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val txtCorreo = findViewById<EditText>(R.id.txtEmail)
        val txtClave = findViewById<EditText>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val correo = txtCorreo.text.toString().trim()
            val contraseña = txtClave.text.toString().trim()

            if (correo.isNotEmpty() && contraseña.isNotEmpty()) {
                mAuth.signInWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val intent = Intent(this@MainActivity, ProductsActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {

                            Toast.makeText(this@MainActivity, "Credenciales inválidas - error en la autenticación: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {

                Toast.makeText(this@MainActivity, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }







    }
}