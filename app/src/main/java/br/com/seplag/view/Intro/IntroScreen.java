package br.com.seplag.view.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import br.com.seplag.R;
import br.com.seplag.helper.IntroHelper;
import br.com.seplag.view.LoginActivity;

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
                .image(R.drawable.bg_mobiliza_intro)
                .title("Mobiliza Caruaru")
                .description("Seja bem-vindo a plataforma do Mobiliza Caruaru." +
                        "\n Esta espaço foi criado para que você, cidadão, possa nos ajudar a " +
                        "construir a cidade que queremos.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.accent)
                .image(R.drawable.cargos)
                .title("Cargos")
                .description("Contribua com nossa cidade e seja um secretário virtual. Quanto mais " +
                        "você contribui, mais promoções você recebe.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.accent)
                .title("Indique a um amigo")
                .image(R.drawable.bg_indique)
                .description(" Indicando amigos para participar do Mobiliza Caruaru, você acumula " +
                        "mais pontos e pode ganhar até prêmios. ")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.accent)
                .image(R.drawable.bg_pontos)
                .title("Pontuação")
                .description("Participe para ganhar pontos, além de ganhar pontos " +
                        "das ações dos seus indicados!")
                .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary)
                        .buttonsColor(R.color.accent)
                        .image(R.drawable.bg_iniciar)
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        helper.setFirstTimeLaunch(false);
                        startActivity(new Intent(IntroScreen.this, LoginActivity.class));
                        finish();
                    }
                }, "Começar!"));

    }

    @Override
    public void onFinish() {
        super.onFinish();
        helper.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroScreen.this, LoginActivity.class));
        finish();
    }
}
