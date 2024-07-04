﻿import java.util.ArrayList;
import java.util.List;

public class SistemaJuego implements Runnable {
    private List<Jugador> jugadoresEspera = new ArrayList<>();
    private int partidasActivas = 0;
    private int rango_inicial = 500;
    private int aumento_por_segundo = 100;
    private final Object monitor = new Object();
    private boolean generandoJugadores = true;
    private boolean sistemaEnEjecucion = true;

    public void agregarJugador(Jugador jugador) {
        synchronized (monitor) {
            jugadoresEspera.add(jugador);
        }
    }

    
    public void revisarEstadoSistema() {
        //synchronized (monitor) {
            if (!generandoJugadores && jugadoresEspera.isEmpty() && partidasActivas == 0) {
                sistemaEnEjecucion = false;
            }
        //}
    }

    public void setGenerandoJugadores(boolean generandoJugadores) {
        //synchronized (monitor) {
            this.generandoJugadores = generandoJugadores;
        //}
    }

    public void AumentarPartidasActivas() {
        synchronized (monitor) {
            partidasActivas = partidasActivas + 1;
            System.out.println("Partidas activas: " + partidasActivas);
        }
    }

    public void DisminuirPartidasActivas() {
        synchronized (monitor) {
            partidasActivas = partidasActivas - 1;
            System.out.println("Partidas activas: " + partidasActivas);
        }
    }

    public Jugador encontrarPartida(Jugador jugador) {
        long tiempoEsperado = (System.currentTimeMillis() - jugador.getTiempoEspera()) / 1000;
        int rangoPermitido = rango_inicial + (int) tiempoEsperado * aumento_por_segundo;
        for (Jugador oponente : jugadoresEspera) {
            if (oponente != jugador && Math.abs(oponente.getRanking() - jugador.getRanking()) <= rangoPermitido) {
                  return oponente;
            }
        }
        return null;
    }

    

    @Override
    public void run() {
        while (sistemaEnEjecucion) {
            Jugador jugador1 = null;
            Jugador jugador2 = null;
            synchronized (monitor) {
                if (jugadoresEspera.size() >= 2) {
                    jugador1 = jugadoresEspera.get(0);
                    jugador2 = encontrarPartida(jugador1);

                    if (jugador2 != null) {
                        jugadoresEspera.remove(jugador1);
                        jugadoresEspera.remove(jugador2);
                    
                        Partida partida = new Partida(jugador1, jugador2, this);
                        new Thread(partida).start();
                    } else
                        try {
                            Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                 }
            }
            revisarEstadoSistema();
        
        }

        System.out.println("El sistema dejó de generar partidas");
    }
}
