package aplicacao.views.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import aplicacao.contracts.LivroContract
import aplicacao.extensions.gone
import aplicacao.extensions.visible
import aplicacao.models.ModelLivro
import aplicacao.presenters.LivroPresenter
import aplicacao.views.adapter.MainAdapter
import barbearia.com.kotlinapplicationtest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LivroContract.ViewMain{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = LivroPresenter(this@MainActivity)
        presenter.getListaLivros()

        refreshMain.setOnRefreshListener {
            presenter.getListaLivros()
        }

        btn_tela_cadastro.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, CadastroActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
        val presenter = LivroPresenter(this@MainActivity)
        presenter.verificarUser()

    }
    override fun mostrarCarregamento() {
        progressView.visible()
        listlivros.gone()
    }

    override fun listaLivros(listlivro: List<ModelLivro>) {

        listlivros.layoutManager = LinearLayoutManager(this@MainActivity)
        listlivros.adapter = MainAdapter(this@MainActivity, listlivro)
        (listlivros.adapter as MainAdapter).notifyDataSetChanged()
    }

    override fun esconderCarregamento() {
        progressView.gone()
        listlivros.visible()

        refreshMain.isRefreshing = false
    }
    override fun usuarioLogado() {

        //Toast.makeText(this@MainActivity, "Bem vindo de volta", Toast.LENGTH_SHORT).show()
    }

    override fun usuarioNaoLogado() {
        Toast.makeText(this@MainActivity, "Usuario não logado!!!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.id_sair ->{
                val presenter = LivroPresenter(this@MainActivity)
                presenter.sair()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)

            }
        }

        return super.onOptionsItemSelected(item)
    }

}