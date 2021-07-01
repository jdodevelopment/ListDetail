package ar.com.jdodevelopment.listdetail.ui.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.jdodevelopment.listdetail.data.model.Product
import ar.com.jdodevelopment.listdetail.data.network.Resource
import ar.com.jdodevelopment.listdetail.databinding.ProductsListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private val viewModel: ProductsListViewModel by viewModels()
    private lateinit var binding: ProductsListFragmentBinding
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductsListFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        initListeners()
        initObservers()
    }

    private fun initAdapter() {
        productsAdapter = ProductsAdapter { _, item -> openDetail(item) }
    }

    private fun openDetail(product: Product) {
        val navDirections = ProductsListFragmentDirections.actionOpenDetail(product)
        findNavController().navigate(navDirections)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
    }

    private fun initListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.loadData() }
        binding.buttonRetry.setOnClickListener { viewModel.loadData() }
    }

    private fun initObservers() {
        viewModel.listResource.observe(viewLifecycleOwner, { handleListResource(it) })
    }

    private fun handleListResource(resource: Resource<List<Product>>) {
        when (resource) {
            is Resource.Success -> {
                productsAdapter.submitList(resource.data)
                finishLoading()
            }
            is Resource.Error -> {
                finishLoading()
                binding.errorLayout.isVisible = true
                binding.errorMessage.text = resource.message.toString()
            }
            is Resource.Loading -> startLoading()
        }
    }

    private fun startLoading() {
        binding.errorLayout.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.loadingLayout.isVisible = true
    }

    private fun finishLoading() {
        binding.loadingLayout.isVisible = false
    }

}