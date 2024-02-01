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
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.FragmentRegistrationBinding

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

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

    companion object {
        val fullText = "Нажимая кнопку \"Войти\", вы принимаете условия программы лояльности"
        fun newInstance(): AuthorizationFragment = AuthorizationFragment()
    }
}
