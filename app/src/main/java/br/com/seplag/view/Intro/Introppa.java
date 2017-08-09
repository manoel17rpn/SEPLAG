package br.com.seplag.view.Intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import br.com.seplag.R;
import br.com.seplag.fragments.EixosFragment;

public class Introppa extends MaterialIntroActivity {
    private static final String PREF_NAME = "intro_ppa";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        pref = this.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        if (!isFirstTimeLaunch()) {
            Intent intent = new Intent(Introppa.this, EixosFragment.class);
            startActivity(intent);
        } else {
            addSlide(new SlideFragmentBuilder()
                    .backgroundColor(R.color.colorPrimary)
                    .buttonsColor(R.color.accent)
                    .image(R.drawable.bg_mobiliza_intro)
                    .title("A Caruaru que Precisamos")
                    .description("Nós queremos lhe ouvir. Aqui, você pode dar sugestões, para que, juntos, " +
                            "possamos construir uma cidade com mais qualidade de vida para todos.")
                    .build());

            addSlide(new SlideFragmentBuilder()
                    .backgroundColor(R.color.colorPrimary)
                    .buttonsColor(R.color.accent)
                    .image(R.drawable.bg_mobiliza_intro)
                    .title("Áreas")
                    .description("Faça sugestões em diversas áreas, " +
                            "como saúde, educação, sustentabilidade, mobilidade e outras")
                    .build());
        }
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        setFirstTimeLaunch(false);
        startActivity(new Intent(Introppa.this, EixosFragment.class));
        finish();
    }
}
