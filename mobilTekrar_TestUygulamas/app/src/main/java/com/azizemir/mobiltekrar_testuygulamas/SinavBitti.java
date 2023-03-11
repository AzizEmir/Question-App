package com.azizemir.mobiltekrar_testuygulamas;

import static com.azizemir.mobiltekrar_testuygulamas.SorularVeCevaplar.sorular;

import android.widget.TextView;
import android.widget.Toast;

public class SinavBitti {
    public void sonuclar(int SayacYanlis, TextView Dogru_Yanlis, MainActivity mainActivity) {
        int sonucDogru = sorular.length - SayacYanlis;
        Dogru_Yanlis.setText("Verdiğiniz doğru cevaplar : " + sonucDogru + "\n" +
                             "Verdiğiniz yanlış cevaplar : " + SayacYanlis);
        Toast.makeText(mainActivity, "SINAV SONA ERMİŞTİR!", Toast.LENGTH_SHORT).show();
    }
}
