package com.edival.recioxpenses.ui.views.today

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.edival.recioxpenses.BR
import com.edival.recioxpenses.R
import com.edival.recioxpenses.databinding.FragmentTodayBinding
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.ui.utils.UtilityFunctions
import com.edival.recioxpenses.ui.utils.showSnackBar
import com.edival.recioxpenses.ui.utils.showToast
import com.edival.recioxpenses.ui.viewModel.TodayViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodayFragment : Fragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var utils: UtilityFunctions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers(view, view.context)
        setupButtons()
    }

    private fun setupViewModel() {
        val vm: TodayViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers(view: View, ctx: Context) {
        binding.viewModel?.let { vm ->
            vm.getWorkDayByDayRes.observe(viewLifecycleOwner) { resource ->
                Log.i("getWorkDayByDayRes", "$resource")
                when (resource) {
                    is Resource.Success -> vm.currentWorkDay.value = resource.data
                    is Resource.Error -> view.showSnackBar(
                        resource.message
                            ?: ctx.getString(R.string.record_query_error)
                    )
                }
            }
            vm.saveWorkDayRes.observe(viewLifecycleOwner) { resource ->
                Log.i("saveWorkDayRes", "$resource")
                when (resource) {
                    is Resource.Success -> ctx.showToast(R.string.today_save_success)
                    is Resource.Error -> view.showSnackBar(
                        resource.message
                            ?: ctx.getString(R.string.unknown_error)
                    )
                }
            }
            vm.isHideKeyboard.observe(viewLifecycleOwner) { isHide ->
                Log.i("isHideKeyboard", "$isHide")
                if (isHide) utils.hideKeyboard(view)
            }
        }
    }

    private fun setupButtons() {
        binding.btnSave.setOnClickListener {
            binding.viewModel?.saveWorkDay(
                startCapital = binding.etStartCapital.text.toString().toDoubleOrNull(),
                finalCapital = binding.etFinalCapital.text.toString().toDoubleOrNull(),
                expenses = binding.etExpenses.text.toString().toDoubleOrNull()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}