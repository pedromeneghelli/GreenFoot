import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Projetil here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projetil extends Actor
{
    /**
     * Act - do whatever the Projetil wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (MyWorld.PAUSE) return; // Não fazer nada caso esteja pausado.
        // Removendo caso encoste na borda do mundo.
        if (isAtEdge()) getWorld().removeObject(this);
        move(9);
    }
}
