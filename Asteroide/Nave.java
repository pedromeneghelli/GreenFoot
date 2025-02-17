import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Nave here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Nave extends Actor
{
    public static long COOLDOWN_TIRO = 500; // Cooldown de 1 tiro a cada 0,5 segundos.
    private static final GifImage image = new GifImage("./nave.gif");
    private long ultimoTiro = 0; // O tempo em que a ultima bala foi atirada, para calcular o cooldown.
    private int vidas = 3; // Adicionando múltiplas vidas para a nave
    
    /**
     * Act - do whatever the Nave wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        
        setImage(image.getCurrentImage());
        
        if (MyWorld.PAUSE) return; // Não fazer nada caso esteja pausado.
        
        // Lógica de virar.
        if (Greenfoot.isKeyDown("right")) {
            turn(3);
        }
        if (Greenfoot.isKeyDown("left")) {
            turn(-3);
        }
        
        Projetil projetil = null;
        
        // Lógica de atirar
        if (Greenfoot.isKeyDown("space")) {
            long tempoDeAgora = System.currentTimeMillis();
            
            if (tempoDeAgora - ultimoTiro >= COOLDOWN_TIRO) { // Somente atirar quando estiver fora do cooldown.
                ultimoTiro = tempoDeAgora;
                projetil = new Projetil();
                getWorld().addObject(projetil, getX(), getY());  
                projetil.setRotation(getRotation());
            }
        }
        
        // Lógica de mover.
        if (Greenfoot.isKeyDown("w")) {
            turnTowards(getX(), getY() - 1);
            move(3);
        }
        if (Greenfoot.isKeyDown("a")) {
            turnTowards(getX() - 1, getY());
            move(3);
        }
        if (Greenfoot.isKeyDown("s")) {
            turnTowards(getX(), getY() + 1);
            move(3);
        }
        if (Greenfoot.isKeyDown("d")) {
            turnTowards(getX() + 1, getY());
            move(3);
        }
        
        // Lógica de colisão com asteroides.
        Actor asteroide = getOneIntersectingObject(Asteroide.class);
        if (asteroide != null) {
            vidas--; // Decrementa uma vida
            getWorld().removeObject(asteroide); // Remove o asteroide
            if (vidas <= 0) {
                getWorld().addObject(new GameOver(), MyWorld.WIDTH / 2, MyWorld.HEIGHT / 2);
                Greenfoot.stop(); // Termina o jogo se as vidas acabarem
            }
            ((MyWorld) getWorld()).atualizarVidas(vidas); // Atualiza as imagens das vidas
        }
    }
}
