package stickybook.com.br.stickybook;

import java.io.Serializable;

/**
 * Created by lleo on 27/09/2016.
 */
public class Palavra implements Serializable{

    public static String ID = "_id";
    public static String PALAVRA = "PALAVRA";
    public static String TRADUCAO = "TRADUCAO";

    private String palavrasIngles;
    private String palavrasPortugues;
    private Integer id;

    public Palavra(){

    }

    public Palavra(Integer id, String palavrasIngles, String palavrasPortugues ) {
        this.id = id;
        this.palavrasIngles = palavrasIngles;
        this.palavrasPortugues = palavrasPortugues;
    }

    public void setPalavrasIngles(String palavrasIngles){
        this.palavrasIngles = palavrasIngles;
    }
    public String getPalavrasIngles(){
        return palavrasIngles;
    }

    public void setPalavrasPortugues(String palavrasPortugues){
        this.palavrasPortugues = palavrasPortugues;
    }
    public String getPalavrasPortugues(){
        return palavrasPortugues;
    }

    @Override
    public String toString() {
        return getPalavrasPortugues()  + " : " + getPalavrasIngles();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
