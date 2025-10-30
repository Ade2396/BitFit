package com.example.bitfit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bitfit.data.AppDatabase
import com.example.bitfit.databinding.FragmentDashboardBinding
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.get(requireContext()).entryDao()
        viewLifecycleOwner.lifecycleScope.launch {
            dao.getAll().collect { list ->
                val count = list.size
                val total = list.sumOf { it.value }
                val avg = if (count > 0) total / count else 0.0

                binding.tvCount.text = "Entries: $count"
                binding.tvTotal.text = "Total: ${pretty(total)}"
                binding.tvAverage.text = "Average: ${pretty(avg)}"
            }
        }
    }

    private fun pretty(v: Double): String {
        return if (v % 1.0 == 0.0) v.roundToInt().toString() else String.format("%.2f", v)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
