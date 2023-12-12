package com.example.examenfinaljosearroyo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenfinaljosearroyo1.models.ProductsModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val txtId: EditText = findViewById(R.id.txtId)
        val txtDesc: EditText = findViewById(R.id.txtDesc)
        val txtCant: EditText = findViewById(R.id.txtCantidad)
        val txtPrecio: EditText = findViewById(R.id.txtPrecio)
        val btnRegistro: Button = findViewById(R.id.btnRegistrar)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val collectionRef = db.collection("Products")

        btnRegistro.setOnClickListener {
            val id = txtId.text.toString()
            val desc = txtDesc.text.toString()
            val cant = txtCant.text.toString()
            val precio = txtPrecio.text.toString()

            val user = auth.currentUser
            val uid = user?.uid

            val productsModel =
                ProductsModel(id = id, description = desc, quantity = cant, price = precio)

            collectionRef.document(uid ?: "").set(productsModel)
                .addOnCompleteListener {
                    // Manejar la operación completa si es necesario
                    Snackbar
                        .make(
                            findViewById(android.R.id.content),
                            "Registro exitoso del producto",
                            Snackbar.LENGTH_LONG
                        ).show()
                }.addOnFailureListener { error ->
                    // Manejo de error en caso de fallo al agregar datos a Firestore
                    Snackbar
                        .make(
                            findViewById(android.R.id.content),
                            "Error al registrar en Firestore: ${error.message}",
                            Snackbar.LENGTH_LONG
                        ).show()

                }
        }
    }
}