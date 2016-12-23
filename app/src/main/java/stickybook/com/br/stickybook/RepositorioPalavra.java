package stickybook.com.br.stickybook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Created by lleo on 21/09/2016.
 */
public class RepositorioPalavra {

    private SQLiteDatabase sqLiteDatabase;
    private Palavra palavra;
    ContentValues values = new ContentValues();

    public RepositorioPalavra(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;

    }
    public void inserirPalavrasNoDB(Palavra palavra){

        values.put("PALAVRA", palavra.getPalavrasIngles());
        values.put("TRADUCAO", palavra.getPalavrasPortugues());
        sqLiteDatabase.insertOrThrow("PALAVRAS", null, values );

    }

    public ArrayAdapter<Palavra> buscaPalavras(Context context){

        ArrayAdapter<Palavra> adpPalavra = new ArrayAdapter<Palavra>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = sqLiteDatabase.query("PALAVRAS", null, null, null, null, null, " TRADUCAO ASC");
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                palavra = new Palavra();
                palavra.setId(cursor.getInt(cursor.getColumnIndex(Palavra.ID)));
                palavra.setPalavrasIngles(cursor.getString(cursor.getColumnIndex(Palavra.PALAVRA)));
                palavra.setPalavrasPortugues(cursor.getString(cursor.getColumnIndex(Palavra.TRADUCAO)));
                adpPalavra.add(palavra);

            }while(cursor.moveToNext());

        }
        return adpPalavra;
    }
    public void apagarDB(Palavra palavra){


            String where = Palavra.ID + "=" + palavra.getId();
            try{
                sqLiteDatabase.delete("PALAVRAS", where, null);
                //sqLiteDatabase.close();
                Log.d("TesteApagarBD", "apagou a palavra" + palavra.getId() + " " +palavra.getPalavrasPortugues());
            }catch (Exception e){
                Log.d("TesteApagarBD", "NÂO apagou a palavra" + palavra.getId() + " " +palavra.getPalavrasPortugues());
            }


    }


    public void atualizaPalavrasNoDB(Palavra palavra){

        values.put("PALAVRA", palavra.getPalavrasIngles());
        values.put("TRADUCAO", palavra.getPalavrasPortugues());

        String selecao = Palavra.ID + " = ?";
        try{
           sqLiteDatabase.update("PALAVRAS", values, selecao, new String[]{String.valueOf(palavra.getId())});
           //sqLiteDatabase.close();
            Log.d("TesteAtualizarBD", "atualizou a palavra" + palavra.getId() + " " +palavra.getPalavrasPortugues());
        }catch (Exception e){
            Log.d("TesteAtualizarBD", "NÂO atualizou a palavra" + palavra.getId() + " " +palavra.getPalavrasPortugues());
        }

    }

}
