package stickybook.com.br.stickybook;
/**
 * Created by lleo on 20/09/2016.
 */
public class ScriptSQL {
    public static String getCreatePalavras(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS PALAVRAS (");
        stringBuilder.append("_id integer primary key autoincrement,");
        stringBuilder.append("PALAVRA  VACHAR(20),");
        stringBuilder.append("TRADUCAO  VACHAR(20)");
        stringBuilder.append(");");

        return stringBuilder.toString();

    }
}
