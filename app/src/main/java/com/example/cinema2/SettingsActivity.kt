package com.example.cinema2
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val prefs = getSharedPreferences(
            "cinema_prefs",
            MODE_PRIVATE
        )
        val etPrenom = findViewById<EditText>(R.id.et_prenom)
        val etThema = findViewById<EditText>(R.id.et_thematique)
        etPrenom.setText(prefs.getString("prenom", ""))
        etThema.setText(prefs.getString("thematique", "Romance"))
        findViewById<android.widget.Button>(
            R.id.btn_sauvegarder
        ).setOnClickListener {
            prefs.edit()
                .putString("prenom", etPrenom.text.toString())
                .putString("thematique", etThema.text.toString())
                .apply()

                Toast.makeText(this, "Sauvegarde reussie !", Toast.LENGTH_SHORT).show()
            finish()

        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}