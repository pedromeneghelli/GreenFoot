import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Asteroid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Asteroide extends Actor
{
    int vida = 1;
    
    /**
     * Act - do whatever the Asteroid wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (MyWorld.PAUSE) return; // Não fazer nada caso esteja pausado.
        
        // Caso esteja encostando em uma bala, remover 1 de vida, e remover a bala.
        if (isTouching(Projetil.class)) {
            vida--;
            removeTouching(Projetil.class);
        }
        // Caso a vida seja 0, remover asteroide.
        if (vida <= 0) {
            getWorld().removeObject(this);
            return;
        }
        // Caso o asteroide esteja encostando na borda do mapa, virar ele para o centro.
        if (isAtEdge()) turnTowards(MyWorld.WIDTH / 2, MyWorld.HEIGHT / 2);

        move(1);
    }
}
