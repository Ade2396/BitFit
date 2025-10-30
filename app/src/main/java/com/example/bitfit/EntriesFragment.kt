package com.example.bitfit.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfit.CreateEntryActivity
import com.example.bitfit.data.AppDatabase
import com.example.bitfit.databinding.FragmentEntriesBinding
import kotlinx.coroutines.launch

class EntriesFragment : Fragment() {

    private var _binding: FragmentEntriesBinding? = null
    private val binding get() = _binding!!
    private val adapter = EntryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEntries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEntries.adapter = adapter

        val dao = AppDatabase.get(requireContext()).entryDao()
        viewLifecycleOwner.lifecycleScope.launch {
            dao.getAll().collect { list -> adapter.submit(list) }
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), CreateEntryActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
