package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
    boolean muerto = false;
    boolean pause = false;
    int contadorPuntos = 0;
    boolean tuberia1active = true;
    boolean tuberia2active = true;


    private Texture pardal, fondo, tuberiaArriba, tuberiaAbajo, titulo, tap, gameOver, pausa;
    BitmapFont font;

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
        pausa = new Texture("pause.png");

        font = new BitmapFont(Gdx.files.internal("fuente.fnt"));
        font.getData().setScale(3);

    }

    @Override
    public void render() {

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        if (muerto) {

            reiniciarJuego();

            batch.draw(fondo, 0, 0, WIDTH, HEIGHT);

            batch.draw(tuberiaArriba, tuberia1x, tuberia1y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia1x, tuberia1y - tuberia1abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(tuberiaArriba, tuberia2x, tuberia2y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia2x, tuberia2y - tuberia2abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(pardal, WIDTH/5, posicionPajaro, 60, 45);
//
            batch.draw(gameOver, WIDTH/2-175, HEIGHT/2, 350, 71);

            GlyphLayout puntos = new GlyphLayout();
            puntos.setText(font, "" + contadorPuntos);
            font.draw(batch, puntos, (WIDTH - puntos.width)/2, HEIGHT/5*4);
        }
        // Sin empezar
        //
        else if (!jugando) {

            empezarJuego();
            batch.draw(fondo, 0, 0, WIDTH, HEIGHT);
            batch.draw(pardal, WIDTH/5, posicionPajaro, 60, 45);
            batch.draw(titulo, WIDTH/2-175, HEIGHT/6*4, 350, 80);
            batch.draw(tap, WIDTH/2-20, HEIGHT/6*3, 100, 50);

        }
        else if (pause) {

            batch.draw(fondo, 0, 0, WIDTH, HEIGHT);

            batch.draw(tuberiaArriba, tuberia1x, tuberia1y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia1x, tuberia1y - tuberia1abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(tuberiaArriba, tuberia2x, tuberia2y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia2x, tuberia2y - tuberia2abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(pardal, WIDTH/5, posicionPajaro, 60, 45);

            GlyphLayout puntos = new GlyphLayout();
            puntos.setText(font, "" + contadorPuntos);
            font.draw(batch, puntos, (WIDTH - puntos.width)/2, HEIGHT/5*4);
            batch.draw(pausa, WIDTH/2-75, HEIGHT/2-75, 150, 160);

            pause = despausarJuego();
        }
        else if (jugando) {

            update();
            batch.draw(fondo, 0, 0, WIDTH, HEIGHT);

            batch.draw(tuberiaArriba, tuberia1x, tuberia1y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia1x, tuberia1y - tuberia1abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(tuberiaArriba, tuberia2x, tuberia2y, anchuraTuberia, alturaTuberia);
            batch.draw(tuberiaAbajo, tuberia2x, tuberia2y - tuberia2abajo - alturaTuberia, anchuraTuberia, alturaTuberia);

            batch.draw(pardal, WIDTH/5, posicionPajaro, 60, 45);

            Rectangle pajaro = new Rectangle(WIDTH/5+5, posicionPajaro+5, 50, 35);
            Rectangle t1arriba = new Rectangle(tuberia1x, tuberia1y+11, anchuraTuberia, alturaTuberia);
            Rectangle t2arriba = new Rectangle(tuberia2x, tuberia2y+11, anchuraTuberia, alturaTuberia);
            Rectangle t1abajo = new Rectangle(tuberia1x, tuberia1y - tuberia1abajo - alturaTuberia, anchuraTuberia, alturaTuberia-11);
            Rectangle t2abajo = new Rectangle(tuberia2x, tuberia2y - tuberia2abajo - alturaTuberia, anchuraTuberia, alturaTuberia-11);

            GlyphLayout puntos = new GlyphLayout();
            puntos.setText(font, "" + contadorPuntos);
            font.draw(batch, puntos, (WIDTH - puntos.width)/2, HEIGHT/5*4);

            muerto = checkColisions(pajaro, t1arriba, t1abajo, t2arriba, t2abajo);
            pause = pausarJuego();


        }

        batch.end();
    }

    void update() {

        updatePajaro();
        updateTuberia();
        updateContador();
    }

    void updatePajaro() {

        // Agregar Velocidad
        velocidadPajaro += -21.5 * Gdx.graphics.getDeltaTime();

        // Dar Impulso
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            velocidadPajaro = 440 * Gdx.graphics.getDeltaTime();
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
        tuberia1x += (int)(-330 * Gdx.graphics.getDeltaTime());
        tuberia2x += (int)(-330 * Gdx.graphics.getDeltaTime());

        // Reasignar Tuberias
        if (tuberia1x <= -anchuraTuberia) {

            tuberia1x = random.nextInt(tuberia2x + 300,tuberia2x +400);
            tuberia1y = random.nextInt(200, 700);
            tuberia1abajo = random.nextInt(130, 170);
            tuberia1active = true;
        }
        else if (tuberia2x <= -anchuraTuberia) {

            tuberia2x = random.nextInt(tuberia1x + 300,tuberia1x +400);
            tuberia2y = random.nextInt(200, 700);
            tuberia2abajo = random.nextInt(130, 170);
            tuberia2active = true;
        }
    }

    void updateContador() {

        if (tuberia1x < WIDTH/5+5 && tuberia1active) {

            contadorPuntos++;
            tuberia1active = false;
        }
        else if (tuberia2x < WIDTH/5+5 && tuberia2active) {

            contadorPuntos++;
            tuberia2active = false;
        }
    }

    boolean checkColisions(Rectangle pajaro, Rectangle t1arriba, Rectangle t1abajo, Rectangle t2arriba, Rectangle t2abajo) {

        if (pajaro.overlaps(t1arriba) || pajaro.overlaps(t1abajo) || pajaro.overlaps(t2arriba) || pajaro.overlaps(t2abajo) || posicionPajaro == 0) {

            return true;
        }
        return false;
    }

    void empezarJuego() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            velocidadPajaro = 440 * Gdx.graphics.getDeltaTime();
            jugando = true;
        }
    }

    void reiniciarJuego() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            tuberia1x = random.nextInt(550,600);
            tuberia1y = random.nextInt(200, 700);
            tuberia1abajo = random.nextInt(125, 170);
            tuberia2x = random.nextInt(tuberia1x + 300,tuberia1x +400);
            tuberia2y = random.nextInt(200, 700);
            tuberia2abajo = random.nextInt(130, 170);

            muerto = false;
            posicionPajaro = HEIGHT/2;
            velocidadPajaro = 440 * Gdx.graphics.getDeltaTime();
            contadorPuntos = 0;
            tuberia2active = true;
            tuberia1active = true;
        }


    }

    boolean pausarJuego() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            return true;
        }
        return false;
    }

    boolean despausarJuego() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))) {

            return false;
        }
        return true;
    }
}
