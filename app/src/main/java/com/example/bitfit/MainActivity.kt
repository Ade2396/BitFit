package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfit.data.AppDatabase
import com.example.bitfit.databinding.ActivityMainBinding
import com.example.bitfit.ui.EntryAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = EntryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, CreateEntryActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val dao = AppDatabase.get(this).entryDao()
        lifecycleScope.launch {
            dao.getAll().collect { list ->
                adapter.submit(list)
            }
        }
    }
}
