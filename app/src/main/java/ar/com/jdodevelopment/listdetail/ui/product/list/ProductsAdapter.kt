package ar.com.jdodevelopment.listdetail.ui.product.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.jdodevelopment.listdetail.data.model.Product
import ar.com.jdodevelopment.listdetail.databinding.ProductItemBinding


class ProductsAdapter(
    private val onItemClickListener: (view: View, item: Product) -> Unit
) : ListAdapter<Product, ProductsAdapter.ProductsItemViewHolder>(
    ProductsItemDiffCallback()
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ProductsItemViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ProductItemBinding.inflate(layoutInflater, viewGroup, false)
        return ProductsItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ProductsItemViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bindTo(item, onItemClickListener)
    }

    /**
     * ViewHolder
     */
    class ProductsItemViewHolder(
        private val binding: ProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: Product, onItemClickListener: (view: View, item: Product) -> Unit) {
            binding.setObject(item)
            binding.root.setOnClickListener { view: View ->
                onItemClickListener(view, item)
            }
        }

    }

    /**
     * Diff Callback
     */
    class ProductsItemDiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.title == newItem.title
        }

    }
}
