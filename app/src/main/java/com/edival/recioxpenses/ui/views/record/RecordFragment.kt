package com.edival.recioxpenses.ui.views.record

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.edival.recioxpenses.BR
import com.edival.recioxpenses.R
import com.edival.recioxpenses.databinding.FragmentRecordBinding
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.ui.utils.showSnackBar
import com.edival.recioxpenses.ui.utils.showToast
import com.edival.recioxpenses.ui.viewModel.RecordViewModel
import com.edival.recioxpenses.ui.views.record.adapters.OnRecordListener
import com.edival.recioxpenses.ui.views.record.adapters.RecordAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecordFragment : Fragment(), OnRecordListener {
    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var recordAdapter: RecordAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView(view.context)
        setupObservers(view, view.context)
    }

    private fun setupViewModel() {
        val vm: RecordViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupRecyclerView(ctx: Context) {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = this@RecordFragment.recordAdapter
            layoutManager = LinearLayoutManager(ctx).also { manager ->
                addItemDecoration(DividerItemDecoration(ctx, manager.orientation))
            }
        }.also { recordAdapter.setOnClickListener(this) }
    }

    private fun setupObservers(view: View, ctx: Context) {
        binding.viewModel?.let { vm ->
            vm.getEverydayWorkRes.observe(viewLifecycleOwner) { resource ->
                Log.i("getEverydayWorkRes", "$resource")
                when (resource) {
                    is Resource.Success -> recordAdapter.submitList(resource.data)
                    is Resource.Error -> view.showSnackBar(
                        resource.message ?: ctx.getString(R.string.record_query_error)
                    )
                }
            }
            vm.deleteWorkDayRes.observe(viewLifecycleOwner) { resource ->
                Log.i("deleteWorkDayRes", "$resource")
                when (resource) {
                    is Resource.Success -> ctx.showToast(R.string.record_delete_success)
                    is Resource.Error -> view.showSnackBar(
                        resource.message ?: ctx.getString(R.string.record_query_error)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(day: String) {
        binding.root.showSnackBar(day)
    }

    override fun onDelete(day: String) {
        binding.viewModel?.deleteWorkDay(day)
    }
}