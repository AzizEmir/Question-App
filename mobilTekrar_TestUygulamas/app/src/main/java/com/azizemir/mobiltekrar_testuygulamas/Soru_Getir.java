package com.azizemir.mobiltekrar_testuygulamas;

import static com.azizemir.mobiltekrar_testuygulamas.SorularVeCevaplar.secenekler;
import static com.azizemir.mobiltekrar_testuygulamas.SorularVeCevaplar.sorular;

import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Soru_Getir {
    public static void soru_Getir(TextView soru_Txt,RadioButton secilen, RadioButton secenek_A_RB, RadioButton secenek_B_RB, RadioButton secenek_C_RB, RadioButton secenek_D_RB, ArrayList indisler, int SAYAC) {
        secilen = null; //sonraki soruya geçildiğinde önceki sorunun seçeneği kalıyor

        soru_Txt.setText(sorular[(int) indisler.get(SAYAC)]);
        secenek_A_RB.setText(secenekler[(int) indisler.get(SAYAC)][0]);
        secenek_B_RB.setText(secenekler[(int) indisler.get(SAYAC)][1]);
        secenek_C_RB.setText(secenekler[(int) indisler.get(SAYAC)][2]);
        secenek_D_RB.setText(secenekler[(int) indisler.get(SAYAC)][3]);

    }
}
