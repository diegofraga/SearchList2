package com.example.searchlist2;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.service.autofill.CharSequenceTransformation;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {


    private ListView list;
    private EditText text;
    private ArrayList<String> wordsfindedArray = new ArrayList<String>();

    private String[] words = {"you", "probably", "despite", "moon", "misspellings", "pale",
            "house", "teacher", "smart", "city", "batman", "god", "test", "county", "Brazil", "covid"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.EditText_id);
        list = findViewById(R.id.list_id);






        ArrayAdapter<String> adapter_list;

        adapter_list = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                words
        );

        list.setAdapter(adapter_list);
        WordsFinded();

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.setVisibility(View.VISIBLE);
                //adapter_list.getFilter().filter(s);
                WordsFinded();
                list.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, wordsfindedArray));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void WordsFinded() {
        int textlength = text.getText().length();
        String Textword = String.valueOf(text.getText());
        wordsfindedArray.clear();

        for (int i = 0; i < words.length; i++) {


                if (jumbled(words[i], Textword) || isTypo(words[i], Textword))
                    wordsfindedArray.add(words[i]);
            
            
        }
    



         /*

        for (int i = 0; i < words.length; i++) {
            if (textlength <= words[i].length()) {
                if (text.getText().toString().equalsIgnoreCase((String) words[i].subSequence(0, textlength))) {
                    wordsfindedArray.add(words[i]);
                }
            }
        }


        */


    }


    static Boolean jumbled(String word1, String word2) {


        Boolean permutation;

        if(word1.length() == word2.length()) {

            if (word1.codePointAt(0) == word2.codePointAt(0)) {

                if (word1.length() > 3) {

                    float cont = 0;
                    float proportion = (float) 0.6667;
                    for (int i = 0; i < word1.length(); i++) {
                        if (word1.codePointAt(i) != word2.codePointAt(i))
                            cont++;

                    }
                    cont = cont / word1.length();
                    if (cont < proportion)
                        permutation = true;
                    else
                        permutation = false;
                } else {
                    permutation = true;
                }
            } else {
                permutation = false;
            }
        }else{
            permutation = false;
        }
        return permutation;
    }

    static Boolean isTypo(String word1, String word2) {


        Boolean typo = null;
        //check equal length word typo
        if (word1.length() == word2.length()) {
            int cont = 0;
            for (int i = 0; i < word1.length(); i++) {
                if (word1.codePointAt(i) != word2.codePointAt(i))
                    cont++;
                if (cont >= 2) {
                    typo = false;
                    break;
                }
            }
            if (cont == 1)
                typo = true;

            //check word2 adding 1 Character
        } else if ((word1.length() + 1) == word2.length()) {
            String temp;
            StringBuilder temp1 = new StringBuilder();
            StringBuilder temp2 = new StringBuilder();
            temp1.append(word1);
            temp2.append(word2);

            //Check all possibilities "inside" the word
            for (int i = 0; i < word1.length(); i++) {
                if (word1.codePointAt(i) != word2.codePointAt(i)) {
                    char tempString = word2.charAt(i);
                    temp1.insert(i, tempString);
                    temp = temp1.toString();
                    if (temp.compareTo(word2) == 0) {
                        typo = true;
                        break;
                    } else {
                        typo = false;
                        break;
                    }
                }
            }
            //check the first and last character was inserted
            if ((word2.substring(1).compareTo(word1) == 0) || (word2.substring(0, word2.length() - 1).compareTo(word1) == 0))
                typo = true;
            //check word2 removing 1 Character
        } else if ((word1.length() - 1) == word2.length()) {
            String temp;
            StringBuilder temp1 = new StringBuilder();
            StringBuilder temp2 = new StringBuilder();
            temp1.append(word1);
            temp2.append(word2);
            //Check all possibilities "inside" the word
            for (int i = 0; i < word2.length(); i++) {
                if (word1.codePointAt(i) != word2.codePointAt(i)) {
                    temp1.delete(i, i + 1);
                    temp = temp1.toString();
                    if (temp.compareTo(word2) == 0) {
                        typo = true;
                        break;
                    } else {
                        typo = false;
                        break;
                    }
                }
            }
            //check the first and last character was removed
            if ((word1.substring(1).compareTo(word2) == 0) || (word1.substring(0, word1.length() - 1).compareTo(word2) == 0))
                typo = true;

        } else
            typo = false;




        return typo;


    }

}