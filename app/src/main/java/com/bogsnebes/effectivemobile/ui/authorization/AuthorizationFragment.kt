package com.bogsnebes.effectivemobile.ui.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.FragmentRegistrationBinding
import com.bogsnebes.effectivemobile.ui.MainActivity
import com.bogsnebes.effectivemobile.ui.catalog.CatalogFragment

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickableSpan()
        setupTextWatchers()
        setupSavePreferences()
    }

    private fun setupClickableSpan() {
        val spannableString = SpannableString(resources.getString(R.string.terms_of_use)).apply {
            val clickableSpan = createClickableSpan { view ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"))
                view.context.startActivity(intent)
            }
            val start = indexOf("условия программы лояльности")
            val end = start + "условия программы лояльности".length
            setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.textView2.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            setLinkTextColor(resources.getColor(R.color.terms_of_use))
        }
    }

    private fun createClickableSpan(action: (View) -> Unit): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(view: View) = action(view)
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = checkFieldsForEmptyValues()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        listOf(
            binding.clearableEditText2,
            binding.clearableEditText3,
            binding.maskedEditText
        ).forEach { editText ->
            editText.addTextChangedListener(textWatcher)
        }
    }

    private fun checkFieldsForEmptyValues() {
        binding.activeButton.isActive = listOf(
            binding.clearableEditText2,
            binding.clearableEditText3,
            binding.maskedEditText
        ).none { it.text.isNullOrEmpty() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSavePreferences() {
        binding.activeButton.setOnClickListener {
            val editText2Text = binding.clearableEditText2.text.toString()
            val editText3Text = binding.clearableEditText3.text.toString()
            val maskedEditTextText = binding.maskedEditText.text.toString()

            viewModel.saveUserData(editText2Text, editText3Text, maskedEditTextText)
            openNextFragment()
            (activity as? MainActivity)?.showBottomNav(true)
        }
    }

    private fun openNextFragment() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container_view_tag,
                CatalogFragment.newInstance()
            )
            commit()
        }
    }

    companion object {
        fun newInstance(): AuthorizationFragment = AuthorizationFragment()
    }
}
