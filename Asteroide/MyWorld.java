import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    public static final int WIDTH = 800;
    public static final int HEIGHT= 600;
    public static long COOLDOWN_ASTEROIDE = 2000; // Cooldown de 2 segundos para spawnar asteroide
    public static volatile boolean PAUSE = false;
    private static GifImage background = new GifImage("./space.gif");
    private static Pausado tituloPausado = null; // O titulo que aparece quando está pausado.
    private static final GreenfootImage[] asteroides = new GreenfootImage[16];
    private GreenfootImage[] vidasImages = new GreenfootImage[3]; // Array para armazenar as imagens das vidas
    
    static {
        // Preenchendo o array com as imagens de asteroides.
        for (int i = 0; i < asteroides.length; i++) {
            asteroides[i] = new GreenfootImage("asteroide" + (i + 1) + ".png");
        }
    }
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        super(WIDTH, HEIGHT, 1);
        prepare();
    }
    
    private long ultimoP = 0;
    private long ultimoAsteroide = 0;
    
    public void act() {
        setBackground(background.getCurrentImage());
        long tempoDeAgora = System.currentTimeMillis();
        
        // Lógica botão pause.
        if (Greenfoot.isKeyDown("p")) {
            if (tempoDeAgora - ultimoP > 500) { // Cooldown de 0,5 seg para apertar o botão de pause.
                ultimoP = tempoDeAgora;
                PAUSE = !PAUSE;

                if (PAUSE) {
                    if (tituloPausado == null) {
                        tituloPausado = new Pausado();
                        addObject(tituloPausado, WIDTH / 2, HEIGHT / 2);
                    }
                } else {
                    if (tituloPausado != null) {
                        removeObject(tituloPausado);
                        tituloPausado = null;
                    }
                }
            }
        }
        
        if (PAUSE) return; // Não fazer nada caso esteja pausado.
        
        // Lógica spawnar asteroide        
        if (tempoDeAgora - ultimoAsteroide > COOLDOWN_ASTEROIDE) {
            ultimoAsteroide = tempoDeAgora;
            Asteroide asteroide = new Asteroide();
            
            // Definindo a imagem do asteroide para uma aleatória.
            asteroide.setImage(asteroides[Greenfoot.getRandomNumber(15)]);
            // Definindo aleatoriamente se o asteroide vai aparecer na horizontal ou vertical.
            boolean horizontal = Greenfoot.getRandomNumber(2) == 1;
            // Definindo aleatoriamente se o asteroide vai aparecer na parte de baixo ou de cima.
            boolean baixo = Greenfoot.getRandomNumber(2) == 1;
                
            if (horizontal) {
                if (baixo) {
                    addObject(asteroide, 0, Greenfoot.getRandomNumber(HEIGHT));
                } else {
                    addObject(asteroide, WIDTH, Greenfoot.getRandomNumber(HEIGHT));
                }
            } else {
                if (baixo) {
                    addObject(asteroide, Greenfoot.getRandomNumber(WIDTH), HEIGHT);
                } else {
                    addObject(asteroide, Greenfoot.getRandomNumber(WIDTH), 0);
                }
            }
        }
    }
    
    public void started() {
        PAUSE = false;
    }
    
    public void stopped() {
        PAUSE = true;
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Nave nave = new Nave();        
        addObject(nave, WIDTH / 2, HEIGHT / 2); // Colocando nave no meio do mapa
        nave.turnTowards(WIDTH / 2, 0); // Deixando a nave virada pra cima inicialmente
        
        // Inicializa as imagens das vidas
        for (int i = 0; i < vidasImages.length; i++) {
            vidasImages[i] = nave.getImage(); // Usa a imagem da nave para as vidas
        }
        
        atualizarVidas(3); // Atualiza as imagens das vidas no início do jogo
    }
    
    /**
     * Atualiza as imagens das vidas exibidas na tela.
     * @param vidas o número de vidas restantes
     */
    public void atualizarVidas(int vidas) {
        // Remove todas as imagens atuais das vidas
        removeObjects(getObjects(ImagemVida.class));
        
        // Adiciona as novas imagens das vidas
        int y = 30; // Posição Y inicial
        int distanciaVertical = 50; // Distância vertical entre as imagens das vidas
        for (int i = 0; i < 3; i++) {
            GreenfootImage imagemVida;
            if (i < vidas) {
                imagemVida = vidasImages[i];
            } else {
                imagemVida = new GreenfootImage(50, 50); // Imagem em branco para vidas extras
            }
            ImagemVida vida = new ImagemVida(imagemVida);
            addObject(vida, WIDTH - 40, y); // Adiciona as imagens das vidas na posição desejada
            y += distanciaVertical; // Ajusta a posição Y para a próxima imagem da vida
        }
    }
}
