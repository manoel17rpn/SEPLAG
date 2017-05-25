package br.com.seplag.view;

import android.content.Intent;
import android.support.annotation.FloatRange;
import android.os.Bundle;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import br.com.seplag.R;
import br.com.seplag.helper.IntroHelper;

public class IntroScreen extends MaterialIntroActivity {
    private IntroHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        helper = new IntroHelper(this);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.accent)
                .title("A Caruaru que precisamos")
                .description("Seja bem vindo!" +
                        "\n Contribua para fazer uma Caruaru melhor, seja um cidadão ativo")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.accent)
                .title("Cargos")
                .description("Contribua e ganhe cargos e prêmios.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.accent)
                .title("Indique a um amigo")
                .description("Indique a um amigo e ganhe pontos com o seu indicado!")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.accent)
                .title("Pontuação")
                .description("Participe para ganhar pontos, além de ganhar pontos \n" +
                        "das ações dos seus indicados!")
                .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary)
                        .buttonsColor(R.color.accent)
                        .title("Vamos começar?")
                        .build());

    }

    @Override
    public void onFinish() {
        super.onFinish();
        helper.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroScreen.this, LoginActivity.class));
    }
}
