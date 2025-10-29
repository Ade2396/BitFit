package com.example.bitfit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bitfit.data.AppDatabase
import com.example.bitfit.data.Entry
import com.example.bitfit.databinding.ActivityCreateEntryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text?.toString()?.trim().orEmpty()
            val valueText = binding.etValue.text?.toString()?.trim().orEmpty()
            val notes = binding.etNotes.text?.toString()?.trim()

            if (title.isEmpty() || valueText.isEmpty()) {
                Toast.makeText(this, "Title and value required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val value = valueText.toDoubleOrNull()
            if (value == null) {
                Toast.makeText(this, "Value must be a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val entry = Entry(
                title = title,
                value = value,
                notes = notes,
                timestamp = System.currentTimeMillis()
            )

            lifecycleScope.launch(Dispatchers.IO) {
                AppDatabase.get(this@CreateEntryActivity).entryDao().insert(entry)
                launch(Dispatchers.Main) {
                    Toast.makeText(this@CreateEntryActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
