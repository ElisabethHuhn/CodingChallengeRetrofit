package com.huhn.codingchallengeretrofit.ui

import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.huhn.codingchallengeretrofit.R
import com.huhn.codingchallengeretrofit.databinding.FragmentSecondBinding
import com.huhn.codingchallengeretrofit.model.Data
import com.huhn.codingchallengeretrofit.viewmodel.GPatternViewModelImpl

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private val fragmentViewModel : GPatternViewModelImpl by activityViewModels()

//    private var toolbarLayout: CollapsingToolbarLayout? = null

    private lateinit var patternNameTextView: TextView
    private lateinit var patternStartDateTextView: TextView
    private lateinit var patternEndDateTextView: TextView
    private lateinit var patternUrlTextView: TextView
    private var position: Int = 0
    private var selectedPattern: Data? = null


    private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

     private fun updateContent() {
//        toolbarLayout?.title = selectedPattern?.name

         val pName = "Name: ${selectedPattern?.name ?: "No Name"}"
         val pStart = "Start Date: ${selectedPattern?.startDate ?: ""}"
         val pEnd = "End Date: ${selectedPattern?.endDate ?: ""}"
         val pUrl = "URL: ${selectedPattern?.url ?: ""}"
         patternNameTextView.text      = pName
         patternStartDateTextView.text = pStart
         patternEndDateTextView.text   = pEnd
         patternUrlTextView.text       = pUrl
    }

    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
          Toast.makeText(v.context, "Drag Event detected", Toast.LENGTH_SHORT).show()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                val itemName = it.getString(ARG_ITEM_ID)
                if (itemName != null) {
                    position = fragmentViewModel.findPosition(itemName)
                    if (position >= 0) {
                        selectedPattern = fragmentViewModel.findGPattern(position)!!
                    } else {
                        selectedPattern = null
                    }
                } else {
                    position = -1
                    selectedPattern = null
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
//        toolbarLayout = binding.toolbarLayout
        patternNameTextView = binding.dataName
        patternStartDateTextView = binding.dataStartdate
        patternEndDateTextView = binding.dataEnddate
        patternUrlTextView = binding.dataUrl

        selectedPattern = fragmentViewModel.findGPattern(position)!!

        updateContent()
        binding.root.setOnDragListener(dragListener)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}