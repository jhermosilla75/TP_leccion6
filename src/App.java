public class App {
    public static void main(String[] args) throws Exception {
        SistemaJuego sistemaJuego = new SistemaJuego();

        Thread hiloSistema = new Thread(sistemaJuego);
        hiloSistema.start();

        int cantidadJugadores = 10;
        GeneradorJugador generador = new GeneradorJugador(sistemaJuego, cantidadJugadores);

        Thread hiloGenerador = new Thread(generador);
        hiloGenerador.start();

        try {
            hiloSistema.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Fin del programa.");

        System.out.println("Fin del programa.");

    }
}
