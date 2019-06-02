package aplicacao.views.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import aplicacao.contracts.LivroContract
import aplicacao.extensions.gone
import aplicacao.extensions.visible
import aplicacao.models.ModelLivro
import aplicacao.presenters.LivroPresenter
import barbearia.com.kotlinapplicationtest.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_tela_livro.*

class TelaLivroActivity : AppCompatActivity(), LivroContract.ViewExcluir {
    var strUid: String = null.toString()
    var strIdUsuario: String = null.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_livro)

        strUid = intent.getStringExtra("UidLivro")
        strIdUsuario = intent.getStringExtra("idUsuario")
        var strNome: String = intent.getStringExtra("NomeLivro")
        var strPreco: String = intent.getStringExtra("PrecoLivro")
        var strDescricao: String = intent.getStringExtra("DescricaoLivro")
        var strImagem: String = intent.getStringExtra("ImagemLivro")
        //Toast.makeText(this@TelaLivroActivity, strIdUsuario, Toast.LENGTH_LONG).show()

        textView_nome_livro.text = strNome
        textView_preco_livro.text = strPreco
        textView_descricao_livro.text = strDescricao
        Glide.with(this)
                .load(strImagem)
                .into(imageView_livro)

        imageView_editar.setOnClickListener {
            val intent = Intent(this@TelaLivroActivity,EditarLivroActivity::class.java)
            intent.putExtra("UidLivro", strUid)
            intent.putExtra("NomeLivro", strNome)
            intent.putExtra("PrecoLivro", strPreco)
            intent.putExtra("DescricaoLivro", strDescricao)
            startActivity(intent)
            finish()
        }

        val presenter = LivroPresenter(this@TelaLivroActivity)
        imageView_excluir.setOnClickListener {

            presenter.excluirLivro(strUid)
        }

    }

    override fun onStart() {
        super.onStart()
        val presenter = LivroPresenter(this@TelaLivroActivity)
        presenter.verificarAnuncio(strIdUsuario)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun mostrarCarregamento() {
       imageView_editar.visible()
       imageView_excluir.visible()
        btn_alugar_livro.gone()
    }

    override fun esconderCarregamento() {
        imageView_editar.gone()
        imageView_excluir.gone()
        btn_alugar_livro.visible()
    }

    override fun excluirSucesso() {
        AlertDialog.Builder(this@TelaLivroActivity)
                .setTitle("Excluindo anúncio...")
                .setMessage("Tem certeza que deseja excluir o anúncio?")
                .setNegativeButton("Não", {dialog, which ->

                })
                .setPositiveButton("Sim", {dialog, which ->

                    val presenter = LivroPresenter(this@TelaLivroActivity)
                    presenter.excluir("Sucesso", strUid )

                    Toast.makeText(this@TelaLivroActivity, "Anúncio excluido com sucesso...", Toast.LENGTH_SHORT).show()
                })
                .show()
                .setCanceledOnTouchOutside(false)

    }

    override fun excluirFalhou() {
        Toast.makeText(this@TelaLivroActivity, "Erro ao excluir anúncio...", Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
       finish()
    }



}