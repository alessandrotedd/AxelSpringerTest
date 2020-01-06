package app.alessandrotedesco.axelspringertest.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import app.alessandrotedesco.axelspringertest.R
import app.alessandrotedesco.axelspringertest.models.Teaser
import kotlinx.android.synthetic.main.teaser_item.view.*

class RecyclerAdapter(private val list: ArrayList<RecyclerItem>) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val TYPE_TEASER = 0
        const val TYPE_BUY_SUBSCRIPTION = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is Teaser)
            TYPE_TEASER
        else
            TYPE_BUY_SUBSCRIPTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            // inflate teaser card
            TYPE_TEASER -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.teaser_item, parent, false)
                TeaserViewHolder(v)
            }
            // inflate buy subscription
            TYPE_BUY_SUBSCRIPTION -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.buy_subscription_item, parent, false)
                BuySubscriptionViewHolder(v)
            }
            // error: invalid viewType
            else -> {
                Log.e("NewsAdapter", "Invalid viewType: $viewType")
                val v =
                    LayoutInflater.from(parent.context).inflate(R.layout.teaser_item, parent, false)
                TeaserViewHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_TEASER) {
            val teaser = list[position]
            teaser as Teaser
            holder as TeaserViewHolder
            // set onClickListener
            holder.bind(teaser)

            // set teaser title
            holder.teaserTitle.text = teaser.title
            // set teaser text content
            holder.teaserText.text = teaser.text
            // set teaser content price text
            @Suppress("ConstantConditionIf") // can be true, if isPaid:true is defined in the json response
            holder.teaserPriceTextView.text = if (teaser.isPaid) "paid content" else "free content"
            // set teaser content price visibility
            if (teaser.priceIsVisible)
                holder.teaserPriceTextView.visibility = View.VISIBLE
            else
                holder.teaserPriceTextView.visibility = View.GONE
        }
        /*
        else if (getItemViewType(position) == TYPE_BUY_SUBSCRIPTION) {
            ... as BuySubscriptionItem
            holder as BuySubscriptionViewHolder
        }
        */
    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal inner class TeaserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teaserTitle: TextView = itemView.newsTitleTextView
        val teaserText: TextView = itemView.newsTextTextView
        val teaserPriceTextView: TextView = itemView.isPaidTextView

        // handle teaser click listener
        fun bind(teaser: Teaser) {
            itemView.setOnClickListener {
                teaser.priceIsVisible = true
                // add buy subscription item if needed
                if (teaser.isPaid && (adapterPosition + 1 == itemCount || list[adapterPosition + 1] is Teaser)) {
                    list.add(adapterPosition + 1, BuySubscription())
                    notifyItemInserted(adapterPosition + 1)
                }
                // show paid/free content textView
                itemView.isPaidTextView.visibility = View.VISIBLE
                notifyItemChanged(adapterPosition)
            }
        }
    }

    internal inner class BuySubscriptionViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

}