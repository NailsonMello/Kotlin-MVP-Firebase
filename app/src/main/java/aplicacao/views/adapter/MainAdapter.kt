package aplicacao.views.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aplicacao.models.ModelLivro
import aplicacao.views.main.TelaLivroActivity
import barbearia.com.kotlinapplicationtest.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_item_livro.view.*

class MainAdapter(private val mContext: Context,
                  private val mListNote: List<ModelLivro>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder =
            ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_item_livro, parent, false))


    override fun getItemCount(): Int =
            mListNote.size

    override fun onBindViewHolder(p0: MainAdapter.ViewHolder, p1: Int) {

        p0.bindAndroidNotes(mListNote[p1])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindAndroidNotes(modelLivro: ModelLivro){

            itemView.livroNomeText.text = modelLivro.livroNome
            itemView.livroPrecoText.text = modelLivro.livroPreco
            itemView.livroDescricaoText.text = modelLivro.livroDescricao
            Glide.with(mContext)
                    .load(modelLivro.imagemLivro)
                    .into(itemView.img_livro)

            itemView.setOnClickListener(View.OnClickListener {
                //Toast.makeText(mContext, androidNote.livroUid, Toast.LENGTH_LONG).show()
                val intent = Intent(mContext,TelaLivroActivity::class.java)
                intent.putExtra("UidLivro", modelLivro.livroUid)
                intent.putExtra("NomeLivro", modelLivro.livroNome)
                intent.putExtra("PrecoLivro", modelLivro.livroPreco)
                intent.putExtra("DescricaoLivro", modelLivro.livroDescricao)
                intent.putExtra("ImagemLivro", modelLivro.imagemLivro)
                intent.putExtra("idUsuario", modelLivro.idUsuario)
                mContext.startActivity(intent)
            })
        }
    }

}
