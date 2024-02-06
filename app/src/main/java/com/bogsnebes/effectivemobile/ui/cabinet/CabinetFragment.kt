package com.bogsnebes.effectivemobile.ui.cabinet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.FragmentCabinetBinding
import com.bogsnebes.effectivemobile.ui.MainActivity
import com.bogsnebes.effectivemobile.ui.favourites.FavouritesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CabinetFragment : Fragment() {
    private var _binding: FragmentCabinetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CabinetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentCabinetBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.showProgressBar(false)
        viewModel.loadFavoritesCount()
        setupUserData()
        setupButtonClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUserData() {
        with(binding) {
            name.text = "${viewModel.getName()} + ${viewModel.getSurname()}"
            phoneNumber.text = viewModel.getPhone()
            viewModel.countOfFavorites.observe(viewLifecycleOwner) { count ->
                countOfItems.text = resources.getQuantityString(R.plurals.items_count, count, count)
            }
        }
    }

    private fun setupButtonClickListener() {
        binding.button2.setOnClickListener {
            navigateToFavouritesFragment()
        }
    }

    private fun navigateToFavouritesFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, FavouritesFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance(): CabinetFragment = CabinetFragment()
    }
}
