package mx.itesm.ato;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.itesm.ato.PantallaMenu.Estado;

public class Personaje {
    private int temp;
    public Sprite sprite;
    private int velocidad = 30;
    private int aceleracion = 2;

    public Personaje(Texture textura){
        temp = 0;
        sprite = new Sprite(textura,12,10,60,70);
        sprite.setBounds(60,20, 100, 100);

    }

    public Estado mover(Estado estado, int tempEstado){
        temp = tempEstado/3;

        switch (estado){
            case CORRIENDO_ABAJO:
                sprite.setRegion(12,10,60,70);
                sprite.setY(0);
                return Estado.CORRIENDO_ABAJO;
            case SALTANDO:
                sprite.setRegion(16,170,60,70);
                sprite.setY(velocidad*temp-(aceleracion*(float)(Math.pow(temp,2)))/2);
                if(velocidad*temp<(aceleracion*(float)(Math.pow(temp,2))))
                    sprite.setRegion(94,170,60,70);

                if (sprite.getY()<0) {
                    PantallaMenu.resetTempEstado();
                    return Estado.CORRIENDO_ABAJO;
                }
                return Estado.SALTANDO;
            case BAJANDO:
                sprite.setRegion(890,727,65,70);
                sprite.setY(sprite.getY()-(aceleracion*(float)(Math.pow(temp,2)))/20);

                if(sprite.getY()<=0) {
                    PantallaMenu.resetTempEstado();
                    return Estado.CORRIENDO_ABAJO;
                }
                return Estado.BAJANDO;
            case GANCHO_ABAJO:
                sprite.setRegion(410,170,60,70);
                sprite.setY(sprite.getY()-velocidad*temp/3);
                if (sprite.getY()<=0) {
                    PantallaMenu.resetTempEstado();
                    return Estado.CORRIENDO_ABAJO;
                }
                return Estado.GANCHO_ABAJO;
            case GANCHO_ARRIBA:
                sprite.setRegion(175,647,60,70);
                sprite.setY(sprite.getY()+velocidad*temp/3);
                if (sprite.getY()+sprite.getHeight()>Principal.ALTO){
                    PantallaMenu.resetTempEstado();
                    return Estado.BAJANDO;
                }
                return Estado.GANCHO_ARRIBA;
            case CORRIENDO_ARRIBA:
                sprite.setY(Principal.ALTO-sprite.getHeight());
                return Estado.CORRIENDO_ARRIBA;
            default:
                System.out.println("We have a problem");
                sprite.setY(0);
                return Estado.CORRIENDO_ABAJO;

        }
    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

}
