package mx.itesm.ato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaMenu implements Screen {
    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    private Texture texturaPersonaje;
    private SpriteBatch batch;
    public Estado estado;
    protected static int tempEstado;
    private Personaje personaje;

    public PantallaMenu(Principal principal) {
        this.principal = principal;
    }

    public static void resetTempEstado() {
        tempEstado = 0;
    }

    @Override
    public void show() {
        //Iniciar personaje
        texturaPersonaje = new Texture("ninja.png");
        personaje = new Personaje(texturaPersonaje);
        resetTempEstado();
        estado = Estado.CORRIENDO_ABAJO;

        //Ajustes cámara
        camara = new OrthographicCamera(Principal.ANCHO, Principal.ALTO);
        vista = new FitViewport(Principal.ANCHO,Principal.ALTO,camara);
        camara.position.set(Principal.ANCHO/2,Principal.ALTO/2,0);
        camara.update();

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    @Override
    public void render(float delta) {
        //Ajustes fondo
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Mover personaje
        tempEstado +=1;
        estado = personaje.mover(estado, tempEstado);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        personaje.render(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private class ProcesadorEntrada implements InputProcessor {
        //Si el personaje esta corriendo entonces salta y prepara el gancho
        //Si el personaje esta en el techo, se deja caer y prepara el gancho
        @Override
        public boolean keyDown(int keycode) {
            if(estado==Estado.CORRIENDO_ABAJO){
                estado=Estado.SALTANDO;
                resetTempEstado();
                return true;
            }
            if (estado==Estado.CORRIENDO_ARRIBA) {
                estado = Estado.BAJANDO;
                resetTempEstado();
                return true;
            }else{
                return false;
            }
        }


        //El personaje lanza el gancho ya sea ariba o abajo dependiendo de las condiciones
        @Override
        public boolean keyUp(int keycode) {

            if (estado==Estado.CORRIENDO_ABAJO || estado==Estado.GANCHO_ABAJO || estado==Estado.SALTANDO || estado==Estado.BAJANDO) {
                estado = Estado.GANCHO_ARRIBA;
                resetTempEstado();
                return true;
            }
            if (estado==Estado.CORRIENDO_ARRIBA || estado==Estado.GANCHO_ARRIBA) {
                estado = Estado.GANCHO_ABAJO;
                resetTempEstado();
                return true;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
    public enum Estado{
        CORRIENDO_ABAJO,
        CORRIENDO_ARRIBA,
        SALTANDO,
        BAJANDO,
        GANCHO_ARRIBA,
        GANCHO_ABAJO
    }
}
