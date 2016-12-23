package stickybook.com.br.stickybook;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertWordActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private RepositorioPalavra repositorioPalavra;
    private EditText palavraInlges;
    private EditText palavraEmPortugues;
    private Palavra p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        palavraInlges = (EditText) findViewById(R.id.editTextTraducao);
        palavraEmPortugues = (EditText) findViewById(R.id.editTextPalavra);

        try{

            dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            repositorioPalavra = new RepositorioPalavra(sqLiteDatabase);
            Log.d("ConexoaBancoTry", "banco conectado");

        }catch (SQLException exception){
            Log.d("ConexaoBancoCatch", "Erro ao conectar com o banco");
        }
    }

    public void gravarPalavra(View view){

        p = new Palavra();

        if(palavraInlges.getText().length()<=0 || palavraEmPortugues.getText().length()<=0 ){
            AlertDialog.Builder dig = new AlertDialog.Builder(this);
            dig.setMessage("Não foi possível gravar: PREENCHA TODOS OS CAMPOS");
            dig.setNeutralButton("OK", null);
            dig.show();
        }
        else {

            try {
                p.setPalavrasIngles(palavraInlges.getText().toString());
                p.setPalavrasPortugues(palavraEmPortugues.getText().toString());
                repositorioPalavra.inserirPalavrasNoDB(p);

                Toast toast = Toast.makeText(this, "Gravado com sucesso!", Toast.LENGTH_LONG);
                toast.show();

                palavraEmPortugues.setText("");
                palavraInlges.setText("");

            }
            catch (Exception ex){
                Toast toast = Toast.makeText(this, "Erro ao inserir os dados: " + ex.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }

    public void voltar(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}


