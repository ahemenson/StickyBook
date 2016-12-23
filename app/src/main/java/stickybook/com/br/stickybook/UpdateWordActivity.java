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

public class UpdateWordActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private RepositorioPalavra repositorioPalavra;
    private EditText palavraInlges;
    private EditText palavraEmPortugues;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        palavraInlges = (EditText) findViewById(R.id.editTextTraducao);
        String palavra = getIntent().getStringExtra("Palavra");
        palavraInlges.setText(palavra);

        palavraEmPortugues = (EditText) findViewById(R.id.editTextPalavra);
        String traducao = getIntent().getStringExtra("Traducao");
        palavraEmPortugues.setText(traducao);

        id = getIntent().getIntExtra("_id",0);

        try{

            dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            repositorioPalavra = new RepositorioPalavra(sqLiteDatabase);
            Log.d("ConexoaBancoTry", "banco conectado");

        }catch (SQLException exception){
            Log.d("ConexaoBancoCatch", "Erro ao conectar com o banco");
        }
    }

    public void atualizaPalavra(View view){

        if(palavraInlges.getText().length()<=0 || palavraEmPortugues.getText().length()<=0 ){
            AlertDialog.Builder dig = new AlertDialog.Builder(this);
            dig.setMessage("Não foi possível atualizar: PREENCHA TODOS OS CAMPOS");
            dig.setNeutralButton("OK", null);
            dig.show();
        }

        else {

            try {
                Palavra p = new Palavra();
                p.setId(id);
                p.setPalavrasIngles(palavraInlges.getText().toString());
                p.setPalavrasPortugues(palavraEmPortugues.getText().toString());
                repositorioPalavra.atualizaPalavrasNoDB(p);

                Toast toast = Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_LONG);
                toast.show();

                palavraEmPortugues.setText("");
                palavraInlges.setText("");
                voltar();

            }
            catch (Exception ex){
                Toast toast = Toast.makeText(this, "Erro ao inserir os dados: " + ex.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }

    public void voltar(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }



}
