package com.azizemir.mobiltekrar_testuygulamas;

import static com.azizemir.mobiltekrar_testuygulamas.SorularVeCevaplar.dogruCevaplar;
import static com.azizemir.mobiltekrar_testuygulamas.SorularVeCevaplar.sorular;
import static com.azizemir.mobiltekrar_testuygulamas.TemizleArkaPlan.temizleArkaPlan;
import static com.azizemir.mobiltekrar_testuygulamas.TemizleRadiobutton.temizleRadioButton;
import static com.azizemir.mobiltekrar_testuygulamas.Soru_Getir.soru_Getir;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    TextView soru_Txt, Dogru_Yanlis;
    RadioButton secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB, secilen;
    Button cevapla_BTN, Onceki, Sonraki;
    String[] cevaplanmisSorular = new String[sorular.length];
    ArrayList<Integer> indisler = new ArrayList<>();
    ArrayList<String> verilenCevaplar = new ArrayList<>();
    SinavBitti sinavBitti = new SinavBitti();
    boolean durum = true;
    int SayacYanlis = 0;
    int SAYAC = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soru_Txt = findViewById(R.id.soruTxt);
        Dogru_Yanlis = findViewById(R.id.Dogru_Yanlis);
        secenek_A_RB = findViewById(R.id.secenek_a_RB);
        secenek_B_RB = findViewById(R.id.secenek_b_RB);
        secenek_C_RB = findViewById(R.id.secenek_c_RB);
        secenek_D_RB = findViewById(R.id.secenek_d_RB);
        cevapla_BTN = findViewById(R.id.cevapla_BTN);
        Onceki = findViewById(R.id.GERI);
        Sonraki = findViewById(R.id.ILERI);


        for (int i = 0; i < sorular.length; i++) {
            indisler.add(i);
        }

        Collections.shuffle(indisler);

        soru_Getir(soru_Txt, secilen, secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB, indisler, SAYAC);

        cevapla_BTN.setOnClickListener(view -> {
            if (secilen != null) {
                if (secilen.getTag().toString().equals(String.valueOf(dogruCevaplar[indisler.get(SAYAC)]))) {
                    secilen.setBackgroundColor(Color.GREEN);
                    durum = true;
                } else {
                    secilen.setBackgroundColor(Color.RED);
                    durum = false;
                    SayacYanlis++;
                }

                verilenCevaplar.add(secilen.getTag().toString());
                cevaplanmisSorular[SAYAC] = secilen.getTag().toString();

                new CountDownTimer(3000, 100) {
                    @Override
                    public void onTick(long l) {
                        cevapla_BTN.setClickable(false);
                        Onceki.setClickable(false);
                        Sonraki.setClickable(false);
                        if (l < 2000) {
                            if (!durum) {
                                switch (dogruCevaplar[indisler.get(SAYAC)]) {
                                    case "A":
                                        secenek_A_RB.setBackgroundColor(Color.YELLOW);
                                        break;
                                    case "B":
                                        secenek_B_RB.setBackgroundColor(Color.YELLOW);
                                        break;
                                    case "C":
                                        secenek_C_RB.setBackgroundColor(Color.YELLOW);
                                        break;
                                    case "D":
                                        secenek_D_RB.setBackgroundColor(Color.YELLOW);
                                        break;
                                }
                            }
                        }

                        if (l < 100) {
                            temizleRadioButton(secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB);
                        }

                    }

                    @Override
                    public void onFinish() {
                        boolean bittiMi = true;
                        int bitti = 0;

                        for (String cevap : cevaplanmisSorular) {
                            if (cevap == null || cevap.isEmpty()) {
                                bittiMi = false;
                                break;
                            }
                        }

                        if (bittiMi) {
                            sinavBitti.sonuclar(SayacYanlis, Dogru_Yanlis, MainActivity.this);
                            cevapla_BTN.setClickable(false);
                            SAYAC--;
                            bitti++;
                        }

                        if (bitti == 0)
                            cevapla_BTN.setClickable(true);

                        Onceki.setClickable(true);
                        Sonraki.setClickable(true);
                    }
                }.start();
            } else {
                Toast.makeText(MainActivity.this, "HENÜZ SEÇİM YAPMADINIZ!", Toast.LENGTH_SHORT).show();
            }
        });
        Onceki.setOnClickListener(view -> {
            if (SAYAC >= 0) {
                if (SAYAC != 0) {
                    SAYAC--;
                }

                temizleArkaPlan(secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB);
                temizleRadioButton(secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB);
                soru_Getir(soru_Txt, secilen, secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB, indisler, SAYAC);

                //Cevaplanmamış bir soruya dönülür ise hata vermemesi için eklenmiştir
                try {
                    oncekiSoruKarsilastir();
                } catch (Exception ignored) {

                }

            }
            if (SAYAC == 0) {
                Toast.makeText(MainActivity.this, "İLK SORUDASINIZ", Toast.LENGTH_SHORT).show();
            }
        });
        Sonraki.setOnClickListener(view -> {
            if (SAYAC < sorular.length) {
                if (SAYAC != sorular.length - 1) SAYAC++;
                temizleArkaPlan(secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB);
                temizleRadioButton(secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB);
                soru_Getir(soru_Txt, secilen, secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB, indisler, SAYAC);
                if (!sorular[sorular.length - 1].equals(soru_Txt.getText().toString())) {
                    temizleArkaPlan(secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB);
                }
                try {
                    if (!Objects.equals(cevaplanmisSorular[SAYAC], ""))
                        oncekiSoruKarsilastir();
                } catch (Exception ignored) {

                }
            }
            if (SAYAC == sorular.length - 1) {
                Toast.makeText(MainActivity.this, "SON SORUDASINIZ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String karsilastir;

    public void oncekiSoruKarsilastir() {
        if (cevaplanmisSorular[SAYAC] != "null") {
            karsilastir = cevaplanmisSorular[SAYAC];
        }

        //Soru cevaplanmis ise
        if (cevaplanmisSorular[SAYAC] != null) {
            if (!Objects.equals(karsilastir, dogruCevaplar[(indisler.get(SAYAC))])) {
                switch (dogruCevaplar[(indisler.get(SAYAC))]) {
                    case "A":
                        secenek_A_RB.setBackgroundColor(Color.YELLOW);
                        break;
                    case "B":
                        secenek_B_RB.setBackgroundColor(Color.YELLOW);
                        break;
                    case "C":
                        secenek_C_RB.setBackgroundColor(Color.YELLOW);
                        break;
                    case "D":
                        secenek_D_RB.setBackgroundColor(Color.YELLOW);
                        break;
                    default:
                        break;
                }
            }
            //Bir soru 2 defa cevaplanmamalıdır
            secenek_A_RB.setClickable(false);
            secenek_B_RB.setClickable(false);
            secenek_C_RB.setClickable(false);
            secenek_D_RB.setClickable(false);
            cevapla_BTN.setClickable(false);
        }
        //Cevaplanmamis ise secenekler isaretlenebilir olacaktır
        if (cevaplanmisSorular[SAYAC] == null) {
            secenek_A_RB.setClickable(true);
            secenek_B_RB.setClickable(true);
            secenek_C_RB.setClickable(true);
            secenek_D_RB.setClickable(true);
            cevapla_BTN.setClickable(true);
            Onceki.setClickable(true);
            Sonraki.setClickable(true);
        }
        if (Objects.equals(karsilastir, dogruCevaplar[(indisler.get(SAYAC))])) {
            switch (karsilastir) {
                case "A":
                    secenek_A_RB.setBackgroundColor(Color.GREEN);
                    break;
                case "B":
                    secenek_B_RB.setBackgroundColor(Color.GREEN);
                    break;
                case "C":
                    secenek_C_RB.setBackgroundColor(Color.GREEN);
                    break;
                case "D":
                    secenek_D_RB.setBackgroundColor(Color.GREEN);
                    break;
            }
        } else {
            switch (karsilastir) {
                case "A":
                    secenek_A_RB.setBackgroundColor(Color.RED);
                    break;
                case "B":
                    secenek_B_RB.setBackgroundColor(Color.RED);
                    break;
                case "C":
                    secenek_C_RB.setBackgroundColor(Color.RED);
                    break;
                case "D":
                    secenek_D_RB.setBackgroundColor(Color.RED);
                    break;
            }
        }

    }

    public void secilen_Sık(View view) {
        temizleArkaPlan(secenek_A_RB, secenek_B_RB, secenek_C_RB, secenek_D_RB);
        view.setBackgroundColor(Color.GRAY);
        secilen = (RadioButton) view;
    }


}