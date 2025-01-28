package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.awt.peer.TextComponentPeer;
import java.security.Key;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    public static int HEIGHT = 800;
    public static int WIDTH = 500;
    float velocidadPajaro;
    float posicionPajaro = HEIGHT/2;

    private Texture pardal, fondo;

    @Override
    public void create() {

        batch = new SpriteBatch();
        pardal = new Texture("pardal.png");
        fondo = new Texture("fondo.png");
    }

    @Override
    public void render() {

        update();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(fondo, 0, 0, WIDTH, HEIGHT);
        batch.draw(pardal, WIDTH/5, posicionPajaro, 80, 60);

        batch.end();
    }

    void update() {

        // Agregar Velocidad
        velocidadPajaro += -0.18;

        // Dar Impulso
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {

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
}
