
/**
 * Beschreiben Sie hier die Klasse Titschen.
 * 
 * @author Lukas Breuer 
 * @version 18.12.2019
 * 
 * TODO:
 * -Farbanpassungen
 * -Geschwindigkeit
 * -Wurfparabel
 * -Main-Methode
 */

public class Titschen
{   
    private static int fensterHoehe = 720;
    private static int fensterBreite = 1280;
    //Variabeln für den Raum
    private int b;//Raumbreite
    private int h; //Raumhöhe
    private int wd; //Wanddicke
    private int xPosRahmen;
    private int yPosRahmen;
    private int ballGroesse;
    private int xPosBallUser;
    private int yPosBallUser;
    private int xPosBallStart;
    private int yPosBallStart;
    private int vBallY;
    private int vBallX;
    private boolean real; //Schräger wurf oder Bildschirmschoner
    
    //debug
    
    //Objekte
    Rechteck wandL;
    Rechteck wandR;
    Rechteck boden;
    Rechteck dach;
    Kreis ball;
    
    public Titschen()
    {   
        b = 1260;
        h = 720;
        wd = 10;
        xPosRahmen = 0;
        yPosRahmen = 0;
        xPosBallUser = 0;
        yPosBallUser = 0;
        ballGroesse = 60;
        vBallY = 3;
        vBallX = 3;
        real = false;
        wandL = new Rechteck();
        wandR = new Rechteck();
        boden = new Rechteck();
        dach = new Rechteck();
        ball = new Kreis();
        getUserInput();
        rahmenBauen();
        ballPositionieren();
        bewegenBildschirmschoner();
    }
    
    public void getUserInput()
    {   
        boolean standardKonfig;
        
        standardKonfig = EinfachEingabe.getBoolean("Standardkonfiguration laden, oder eigene Werte verwenden?", "Standardkonfig.", "eigene Werte");
        
        if(!standardKonfig)
        {
            b = EinfachEingabe.getInt("Breite eingeben.");
            h = EinfachEingabe.getInt("Höhe eingeben.");
            wd = EinfachEingabe.getInt("Wanddicke eingeben.");
            xPosRahmen = EinfachEingabe.getInt("X-Position des Rahmen.");
            yPosRahmen = fensterHoehe - EinfachEingabe.getInt("Y-Position des Rahmen.") - h;
            xPosBallUser = EinfachEingabe.getInt("X-Position des Balls.");
            yPosBallUser = EinfachEingabe.getInt("Y-Position des Balls.");
            vBallX = EinfachEingabe.getInt("Geschwindigkeit des Balls.");
            vBallY = vBallX;
            //xPosBall = xPosRahmen + wd + EinfachEingabe.getInt("X-Position des Balls.");                      veraltet:
            //yPosBall = yPosRahmen + h - EinfachEingabe.getInt("Y-Position des Balls.") - wd - ballGroesse;    14.12.19
        } else
        {   
            xPosBallUser = 100;
            yPosBallUser = 100;
            //xPosBall = xPosRahmen + wd; veraltet:
            //yPosBall = yPosRahmen + wd; 13.12.19
        }
        
        real = EinfachEingabe.getBoolean("Reale Parabel oder Bildschirmschoner", "Wurfparabel", "Bildschirmschoner");
    }
    
    public void rahmenBauen()
    {   
        //linke Wand
        wandL.positionAendern(xPosRahmen, yPosRahmen);
        wandL.groesseAendern(wd, h);
        wandL.farbeAendern("blau");
        wandL.sichtbarMachen();
        
        //Boden
        boden.positionAendern(xPosRahmen + wd, yPosRahmen + h - wd);
        boden.groesseAendern(b, wd);
        boden.farbeAendern("rot");
        boden.sichtbarMachen();
        
        //rechte Wand
        wandR.positionAendern(xPosRahmen + wd + b, yPosRahmen);
        wandR.groesseAendern(wd, h);
        wandR.farbeAendern("gelb");
        wandR.sichtbarMachen();
        
        //Dach
        if(!real)
        {
            dach.positionAendern(xPosRahmen + wd, yPosRahmen);
            dach.groesseAendern(b, wd);
            dach.farbeAendern("gruen");
            dach.sichtbarMachen();
        }       
    }
    
    public void ballPositionieren()
    {   
        xPosBallStart = xPosRahmen + wd + xPosBallUser;
        yPosBallStart = yPosRahmen + h - yPosBallUser - wd - ballGroesse;
        ball.positionAendern(xPosBallStart, yPosBallStart);
        ball.groesseAendern(ballGroesse);
        ball.farbeAendern("schwarz");
        ball.sichtbarMachen();
    }
    
    public void bewegenBildschirmschoner()
    {   
        int xPosBall = 0 + xPosBallUser;
        int yPosBall = 0 + yPosBallUser;
        int grenzeR = b;
        int grenzeO = h - wd - wd;
        int grenzeL = 0;
        int grenzeU = 0;
        int schrittX = 0;
        int schrittY = 0;
        
        
        while(vBallX != 0 || vBallY != 0)
        {   
            if(vBallX > 0)
            {
                if(xPosBall + vBallX + ballGroesse <= grenzeR)
                {
                    schrittX = vBallX;
                    xPosBall = xPosBall + vBallX;
                } else if(xPosBall + vBallX + ballGroesse > grenzeR)
                {
                    schrittX = grenzeR - xPosBall - ballGroesse;
                    xPosBall = grenzeR - ballGroesse;
                    vBallX = -vBallX;
                }
                //ball.langsamHorizontalBewegen(schrittX);
                ball.horizontalBewegen(schrittX);
            } else if(vBallX < 0)
            {
                if(xPosBall + vBallX >= grenzeL)
                {
                    schrittX = vBallX;
                    xPosBall = xPosBall + vBallX;
                } else if(xPosBall + vBallX < grenzeL)
                {
                    schrittX = grenzeL - xPosBall;
                    xPosBall = grenzeL;
                    vBallX = -vBallX;
                }
                //ball.langsamHorizontalBewegen(schrittX);
                ball.horizontalBewegen(schrittX);
            }
            System.out.println("[X]: " + xPosBall); //debug
            
            
            if(vBallY > 0)
            {
                if(yPosBall + vBallY + ballGroesse <= grenzeO)
                {
                    schrittY = vBallY;
                    yPosBall = yPosBall + vBallY;
                } else if(yPosBall + vBallY + ballGroesse > grenzeO)
                {
                    schrittY = grenzeO - yPosBall - ballGroesse;
                    yPosBall = grenzeO - ballGroesse;
                    vBallY = -vBallY;
                }
                //ball.langsamVertikalBewegen(-schrittY);
                ball.vertikalBewegen(schrittY);
                
            } else if(vBallY < 0)
            {
                 if(yPosBall + vBallY >= grenzeU)
                {
                    schrittY = vBallY;
                    yPosBall = yPosBall + vBallY;
                } else if(yPosBall + vBallY < grenzeU)
                {
                    schrittY = grenzeU - yPosBall;
                    yPosBall = grenzeU;
                    vBallY = -vBallY;
                }
                //ball.langsamVertikalBewegen(-schrittY);
                ball.vertikalBewegen(schrittY);
            }
            System.err.println("[Y]: " + yPosBall); //debug
        }
    }
    
    //Getter und vielleicht später auch Setter
    public static int getFensterHoehe()
    {
       return fensterHoehe; 
    }
    
    public static int getFensterBreite()
    {
        return fensterBreite;
    }
    
}
