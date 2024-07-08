public class GeneradorJugador implements Runnable {
    private SistemaJuego sistemaJuego;
    private int jugadoresTotales;

    public GeneradorJugador(SistemaJuego sistemaJuego, int jugadoresTotales) {
        this.sistemaJuego = sistemaJuego;
        this.jugadoresTotales = jugadoresTotales;
    }

    @Override
    public void run() {
        for (int i = 0; i < jugadoresTotales; i++) {
            try {
                Thread.sleep((int) (Math.random() * 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            int ranking = 1000 + (int) (Math.random() * 23000);
            Jugador jugador = new Jugador(i+1, ranking);
            sistemaJuego.agregarJugador(jugador);
            System.out.println("El Jugador " + jugador.getId() + " (Con el ranking: " + jugador.getRanking() + ") se unió a la cola de espera.");
        }

        sistemaJuego.setGenerandoJugadores(false);
        System.out.println("Se terminó de generar " + jugadoresTotales + " jugadoress.");
    }
}
