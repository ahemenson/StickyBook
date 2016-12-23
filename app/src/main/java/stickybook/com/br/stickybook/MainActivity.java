package stickybook.com.br.stickybook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayAdapter<Palavra> addPalavras;
    private RepositorioPalavra repositorioPalavra;
    private ListView lisPalavras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);

        lisPalavras  = (ListView) findViewById(R.id.listView);

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        repositorioPalavra = new RepositorioPalavra(sqLiteDatabase);

        atualizarLista();


    }

    public void adicionar(View view){
//        Palavra p = new Palavra();
//        p.setPalavrasIngles("test");
//        p.setPalavrasPortugues("testo");
//        repositorioPalavra.inserirPalavrasNoDB(p);

        //  Toast toast = Toast.makeText(this, "Tocou!", Toast.LENGTH_LONG);
        //  toast.show();

        startActivity(new Intent(this, InsertWordActivity.class));

    }

    public void editar(Palavra palavra){
        Intent intent = new Intent(this, UpdateWordActivity.class);
        intent.putExtra("_id", palavra.getId());
        intent.putExtra("Palavra", palavra.getPalavrasIngles());
        intent.putExtra("Traducao", palavra.getPalavrasPortugues());
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Palavra palavra = addPalavras.getItem(position);

        exibeDialog(palavra);
    }

    public void apagaPalavra(Palavra palavra){

        repositorioPalavra.apagarDB(palavra);
        atualizarLista();

    }

    public void atualizarLista(){

        addPalavras = repositorioPalavra.buscaPalavras(this);
        lisPalavras.setAdapter(addPalavras);
        lisPalavras.setOnItemClickListener(this);
    }



    public void exibeDialog(final Palavra palavra) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.dialogTitulo));
        builder.setMessage(getString(R.string.dialogMensagem));

        String editText = getString(R.string.editar);
        builder.setPositiveButton(editText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        editar(palavra);
                    }
                });

        String excludeText = getString(R.string.excluir);
        builder.setNegativeButton(excludeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        apagaPalavra(palavra);
                    }
                });

        String cancelText = getString(R.string.cancelar);
        builder.setNeutralButton(cancelText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                addPalavras.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}