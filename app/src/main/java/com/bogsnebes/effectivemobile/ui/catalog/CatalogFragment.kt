package com.bogsnebes.effectivemobile.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.bogsnebes.effectivemobile.databinding.FragmentCatalogBinding
import com.bogsnebes.effectivemobile.ui.catalog.custom_view.CustomSpinnerAdapter

class CatalogFragment : Fragment() {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayOf("По популярности", "По уменьшению цены", "По возрастанию цены")
        val adapter =
            CustomSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Здесь реализуйте логику при выборе элемента
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ничего не выбрано
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CatalogFragment = CatalogFragment()
    }
}
