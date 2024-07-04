public class Partida implements Runnable {
    private  Jugador jugador1;
    private  Jugador jugador2;
    private  SistemaJuego sistemaJuego;

    public Partida(Jugador jugador1, Jugador jugador2, SistemaJuego sistemaJuego) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.sistemaJuego = sistemaJuego;
    }

    @Override
    public void run() {
        sistemaJuego.AumentarPartidasActivas();
        System.out.println("Partida iniciada entre Jugador " + jugador1.getId() + " vs Jugador " + jugador2.getId());
        try {
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        int puntos1 = (int) (Math.random() * 6);
        int puntos2 = (int) (Math.random() * 6);
        
        String resultado;
        if (puntos1 > puntos2) {
            resultado =  "Jugador "+ jugador1.getId() + " ganó la partida con "+ puntos1 +" puntos";
        } else if (puntos1 < puntos2) {
            resultado =  "Jugador "+ jugador2.getId() + " ganó la partida con "+ puntos2 +" puntos";
        } else {
            resultado = "La partida fue empate";
        }
        
        System.out.println("Resultado de la partida: Jugador " + jugador1.getId() + " (" + puntos1 + " puntos) vs Jugador " + jugador2.getId() + " (" + puntos2 + " puntos) - " + resultado);
        sistemaJuego.DisminuirPartidasActivas();
    }
}
