package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    Random random = new Random();

    private SpriteBatch batch;

    public static int HEIGHT = 800;
    public static int WIDTH = 500;
    int alturaTuberia = 654;
    int anchuraTuberia = 96;

    float velocidadPajaro;
    float posicionPajaro = HEIGHT/2;

    int tuberia1x = random.nextInt(550,600);
    int tuberia1y = random.nextInt(200, 700);
    int tuberia1abajo = random.nextInt(125, 170);
    int tuberia2x = random.nextInt(tuberia1x + 300,tuberia1x +400);
    int tuberia2y = random.nextInt(200, 700);
    int tuberia2abajo = random.nextInt(130, 170);

    boolean jugando = false;

    private Texture pardal, fondo, tuberiaArriba, tuberiaAbajo, titulo, tap, gameOver;

    @Override
    public void create() {

        batch = new SpriteBatch();
        pardal = new Texture("pardal.png");
        fondo = new Texture("fondo.png");
        tuberiaArriba = new Texture("tuberiaArriba.png");
        tuberiaAbajo = new Texture("tuberiaAbajo.png");
        titulo = new Texture("titulo.png");
        tap = new Texture("tap.png");
        gameOver = new Texture("gameOver.png");

    }

    @Override
    public void render() {

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        // Sin empezar
        if (!jugando) {

            empezarJuego();
            batch.draw(fondo, 0, 0, WIDTH, HEIGHT);
            batch.draw(pardal, WIDTH/5, posicionPajaro, 60, 45);
            batch.draw(titulo, WIDTH/2-175, HEIGHT/6*4, 350, 80);
            batch.draw(tap, WIDTH/2-20, HEIGHT/6*3, 100, 50);

        }
        else if (jugando) {

            update();
            batch.draw(fondo, 0, 0, WIDTH, HEIGHT);

            batch.draw(tuberiaArriba, tuberia1x, tuberia1y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia1x, tuberia1y - tuberia1abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(tuberiaArriba, tuberia2x, tuberia2y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia2x, tuberia2y - tuberia2abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(pardal, WIDTH/5, posicionPajaro, 60, 45);
        }

        batch.end();
    }

    void update() {

        updatePajaro();
        updateTuberia();
    }

    void updatePajaro() {

        // Agregar Velocidad
        velocidadPajaro += -0.18;

        // Dar Impulso
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            velocidadPajaro = 6;
        }
        // Posicion Final
        posicionPajaro += velocidadPajaro;

        // Comprobar Posiciones Maximas
        // Suelo
        if (posicionPajaro < 0) {

            posicionPajaro = 0;
        }
        // Maximo
        if (posicionPajaro >= HEIGHT) {

            posicionPajaro = HEIGHT;
        }
    }

    void updateTuberia() {

        // Mover Tuberias
        tuberia1x += -4;
        tuberia2x += -4;

        // Reasignar Tuberias
        if (tuberia1x <= -anchuraTuberia) {

            tuberia1x = random.nextInt(tuberia2x + 300,tuberia2x +400);
            tuberia1y = random.nextInt(200, 700);
            tuberia1abajo = random.nextInt(130, 170);
        }
        else if (tuberia2x <= -anchuraTuberia) {

            tuberia2x = random.nextInt(tuberia1x + 300,tuberia1x +400);
            tuberia2y = random.nextInt(200, 700);
            tuberia2abajo = random.nextInt(130, 170);
        }
    }

    void empezarJuego() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            jugando = true;
            velocidadPajaro = 6;
        }
    }
}
