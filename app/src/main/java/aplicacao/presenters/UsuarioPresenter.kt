package aplicacao.presenters

import android.util.Patterns
import aplicacao.contracts.UsuarioContract
import aplicacao.models.ModelLivro
import aplicacao.models.ModelUsuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class UsuarioPresenter : UsuarioContract.Presenter{


    private lateinit var mViewCadastro: UsuarioContract.ViewCadastro
    private lateinit var mViewLogin: UsuarioContract.ViewLogin

    constructor(mViewCadastro: UsuarioContract.ViewCadastro){
        this.mViewCadastro = mViewCadastro
    }

    constructor(mViewLogin: UsuarioContract.ViewLogin){
        this.mViewLogin = mViewLogin
    }


    override fun loginUsuario(email: String, senha: String) {

        val auth = FirebaseAuth.getInstance()

        val modelUser = ModelUsuario("", email, senha)
        mViewLogin.mostrarCarregamento()
        if (valideInputLogin(modelUser)){
            auth.signInWithEmailAndPassword(modelUser.email.toString(), modelUser.senha.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){


                            mViewLogin.loginSucesso()
                            mViewLogin.esconderCarregamento()
                            mViewLogin.finishActivity()
                        }else{
                            mViewLogin.esconderCarregamento()
                            mViewLogin.loginFalhou()
                        }
                    }
        }
    }

    override fun valideInputLogin(mod: ModelUsuario): Boolean {

        var valid = false

        if (Patterns.EMAIL_ADDRESS.matcher(mod.email).matches()){
            valid = true

            if (!mod.senha!!.isEmpty()){
                valid = true
            }else{
                valid = false
                mViewLogin.invalidoSenha()
            }
        }else{
            valid = false
            mViewLogin.invalidoEmail()
        }

        return valid
    }


    override fun criarConta(nome: String, email: String, senha: String) {

        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().reference.child("Usuarios")

        val modelusuario = ModelUsuario(nome, email, senha)

        if (valideInputCadastro(modelusuario)){

            mViewCadastro.mostrarCarregamento()

            auth.createUserWithEmailAndPassword(modelusuario.email.toString(), modelusuario.senha.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){

                            val uid = auth.currentUser!!.uid
                            val UidDb = database.child(uid)

                            UidDb.child("nome").setValue(modelusuario.nome)
                            UidDb.child("email").setValue(modelusuario.email)
                            mViewCadastro.cadastroSucesso()
                            mViewCadastro.esconderCarregamento()
                            mViewCadastro.finishActivity()

                        }else{
                            mViewCadastro.cadastroFalhou()
                            mViewCadastro.esconderCarregamento()

                        }
                    }
        }

    }

    override fun valideInputCadastro(mod: ModelUsuario): Boolean {
        var valid = false

        if (!mod.nome.equals("")){
            valid = true

            if (Patterns.EMAIL_ADDRESS.matcher(mod.email).matches()) {
                valid = true

                if (!mod.senha.equals("") && mod.senha!!.length >= 6) {
                    valid = true
                } else {
                    valid = false
                    mViewCadastro.invalidoSenha()
                }
            }else{
                valid = false
                mViewCadastro.invalidoEmail()
            }
        }else{
            valid = false
            mViewCadastro.invalidoNome()
        }

        return valid
    }


}