package aplicacao.presenters

import android.content.Context
import android.net.Uri
import android.widget.Toast
import aplicacao.contracts.LivroContract
import aplicacao.models.ModelLivro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class LivroPresenter : LivroContract.Presenter{

    private lateinit var mViewMain:LivroContract.ViewMain
    private lateinit var mViewCadastro:LivroContract.ViewCadastro
    private lateinit var mViewEditar: LivroContract.ViewEditar
    private lateinit var mViewExcluir: LivroContract.ViewExcluir

    constructor(mViewMain: LivroContract.ViewMain){
        this.mViewMain = mViewMain
    }

    constructor(mViewCadastro: LivroContract.ViewCadastro){
        this.mViewCadastro = mViewCadastro
    }

    constructor(mViewEditar: LivroContract.ViewEditar){
        this.mViewEditar = mViewEditar
    }

    constructor(mViewExcluir: LivroContract.ViewExcluir){
        this.mViewExcluir = mViewExcluir
    }

    override fun excluirLivro(androidUid: String) {

        if (!androidUid.isEmpty()) {
            mViewExcluir.excluirSucesso()
            //excluir("",androidUid)
        }


    }
    override fun excluir(An: String, AndroidUid: String) {
        val dbRef = FirebaseDatabase.getInstance().reference
                .child("livros")


            if (validateExcluir(An))
                dbRef.child(AndroidUid).removeValue().addOnCompleteListener {
                    if (it.isSuccessful) {
                        mViewExcluir.finishActivity()
                    }
                }

    }
    override fun editNotes(androidUid: String, androidName: String, androidVersion: String, androidRelease: String) {

        val dbRef = FirebaseDatabase.getInstance().reference
                .child("livros")

        val androidn = ModelLivro(androidUid, androidName, androidVersion, androidRelease,"","")

        if (validateInput(androidn)){
            mViewEditar.mostrarCarregamento()
            val Uid = dbRef.child(androidn.livroUid.toString())
            if(Uid != null) {

                        Uid.child("livroNome").setValue(androidn.livroNome)
                        Uid.child("livroPreco").setValue(androidn.livroPreco)
                        Uid.child("livroDescricao").setValue(androidn.livroDescricao)
                        Uid.child("livroUid").setValue(Uid.key)
                        mViewEditar.editarSucesso()
                        mViewEditar.esconderCarregamento()
                        mViewEditar.finishActivity()



            }else{
                mViewEditar.editarFalhou()
                mViewEditar.esconderCarregamento()
            }
        }

    }

    override fun saveNotes(androidUid: String, androidName: String, androidVersion: String, androidRelease: String, androidImagem: Uri) {
        val auth = FirebaseAuth.getInstance()

        val dbRef = FirebaseDatabase.getInstance().reference
                .child("livros")

        val androidn = ModelLivro("", androidName, androidVersion, androidRelease,androidImagem.toString(),"")

        if (validateInput(androidn)){
            mViewCadastro.mostrarCarregamento()
              val Uid = dbRef.push()
            if(Uid != null) {

                if (androidImagem == null) return
                val filename = UUID.randomUUID().toString()
                val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
                   ref.putFile(androidImagem!!).addOnSuccessListener {

                    ref.downloadUrl.addOnSuccessListener {
                        it.toString()
                        val uidUser = auth.currentUser!!.uid

                        Uid.child("livroNome").setValue(androidn.livroNome)
                        Uid.child("livroPreco").setValue(androidn.livroPreco)
                        Uid.child("livroDescricao").setValue(androidn.livroDescricao)
                        Uid.child("livroUid").setValue(Uid.key)
                        Uid.child("imagemLivro").setValue(it.toString())
                        Uid.child("idUsuario").setValue(uidUser)
                        mViewCadastro.cadastroSucesso()
                        mViewCadastro.esconderCarregamento()
                        mViewCadastro.finishActivity()
                    }

                }


            }else{
                mViewCadastro.cadastroFalhou()
                mViewCadastro.esconderCarregamento()
            }
        }


    }

    override fun validateInput(androidN: ModelLivro): Boolean {

        var valid = false

        if (!androidN.livroNome.equals("")){
            valid = true

            if (!androidN.livroPreco.equals("")) {
                valid = true

                if (!androidN.livroDescricao.equals("")) {
                    valid = true
                } else {
                    valid = false
                    mViewCadastro.invalidoDescricao()
                }
            }else{
                valid = false
                mViewCadastro.invalidoPreco()
            }
        }else{
            valid = false
            mViewCadastro.invalidoNome()
        }
        return valid
    }

    override fun validateExcluir(androidN: String): Boolean {
        var valid = false

        if (!androidN.isEmpty()){
            valid = true

        }else{
            valid = false
            mViewExcluir.excluirFalhou()
        }
        return valid
    }
    private val mAndroidNote = mutableListOf<ModelLivro>()
    override fun getListaLivros() {
        val dbRef = FirebaseDatabase.getInstance().reference
                .child("livros")

        mViewMain.mostrarCarregamento()
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {

                mAndroidNote.clear()
                for (children in p0.children){
                    mAndroidNote.add(
                            ModelLivro(
                                    children.child("livroUid").getValue(String::class.java),
                                    children.child("livroNome").getValue(String::class.java),
                                    children.child("livroPreco").getValue(String::class.java),
                                    children.child("livroDescricao").getValue(String::class.java),
                                    children.child("imagemLivro").getValue(String::class.java),
                                    children.child("idUsuario").getValue(String::class.java)


                            )
                    )
                    mViewMain.listaLivros(mAndroidNote)

                }
                mViewMain.esconderCarregamento()
            }

        })
    }

    override fun verificarUser() {

        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser
        if (uid == null){
            mViewMain.usuarioNaoLogado()
        }else{
            mViewMain.usuarioLogado()
        }
    }
    override fun verificarAnuncio(idUsuario: String) {
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser!!.uid
        if (idUsuario.equals(uid)){
            mViewExcluir.mostrarCarregamento()

        }else{
            mViewExcluir.esconderCarregamento()
        }
    }
    override fun sair() {
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser
        if (uid != null) {
            auth.signOut()
        }
    }

}