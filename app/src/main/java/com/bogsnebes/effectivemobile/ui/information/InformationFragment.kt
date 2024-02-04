package com.bogsnebes.effectivemobile.ui.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.effectivemobile.databinding.FragmentInformationAboutItemBinding
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.recycler.HorizontalImagesAdapter

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationAboutItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentInformationAboutItemBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ratingView.estimation = 4.3f
        val imagesAdapter = HorizontalImagesAdapter(
            listOf(
                "https://i.pinimg.com/736x/83/11/0c/83110c6604f80210bfce7171ca7b2024.jpg",
                "https://sun9-79.userapi.com/c237131/u34601202/d58/-3/o_5d7bb888c0.jpg",
                "https://lh3.googleusercontent.com/proxy/QecnzfvHUlWK7bCofjGAcGK8lM1U56EBtFiJS2fTs3rDk5j4TeFMVwN8RluPZ3JEpQEBsQ9eenZqFtoiUn25Gt6dTSqysnIYlqs"
            ),
            binding.indicatorsLayout
        )
        binding.recyclerView3.apply {
            PagerSnapHelper().attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = imagesAdapter
        }
        binding.recyclerView3.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // Используйте SnapHelper для определения текущего видимого элемента
                val snapHelper = PagerSnapHelper()
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val snapView = snapHelper.findSnapView(layoutManager) ?: return
                val newPosition = layoutManager.getPosition(snapView)
                if (newPosition != RecyclerView.NO_POSITION) {
                    imagesAdapter.selectDot(newPosition)
                }
            }
        })
        binding.textView14.setText("Пенка для лица Глубокое очищение содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контролирует работу сальных желез, сужает поры. Обладает мягким антисептическим действием, не пересушивая кожу. Минеральная вода тонизирует и смягчает кожу.")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): InformationFragment = InformationFragment()
    }
}