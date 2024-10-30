import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.criticalnewstest.R
import com.example.criticalnewstest.databinding.ItemNewsBinding
import com.example.criticalnewstest.models.Article
import com.example.criticalnewstest.views.NewsDetailActivity

class NewsAdapter(private val newsList: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    class NewsViewHolder(val binding  : ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        currentItem?.let { article ->
            holder.binding.newsTitle.text = currentItem.title

            Glide.with(holder.itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.binding.newsImage)

            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, NewsDetailActivity::class.java)
                intent.putExtra("article", article)
                Log.d("NewsAdapter", "Sent $article")
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}