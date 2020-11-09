package com.example.calculadora;

public class SimuladorHipoteca {

    public static class Solicitud {
        public int plazaparking;
        public int plazaocupada;

        public Solicitud(int plazaparking, int plazaocupada) {
            this.plazaparking = plazaparking;
            this.plazaocupada = plazaocupada;
        }
    }

    interface Callback {
        void cuandoEsteCalculadaLaCuota(double cuota);
        void cuandoHayaErrorDeCapitalInferiorAlMinimo(int plazaparkingMinimo);
        void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazaocupadaMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    public void calcular(Solicitud solicitud, Callback callback) {
        callback.cuandoEmpieceElCalculo();
        int plazaparkingMinimo = 0;
        int plazaocupadaMinimo = 0;
        callback.cuandoFinaliceElCalculo();

        try {
            Thread.sleep(2500);  // long run operation
            plazaparkingMinimo = 10;
            plazaocupadaMinimo = 10;
        } catch (InterruptedException e) {}

        boolean error = false;
        if (solicitud.plazaparking < plazaparkingMinimo) {
            callback.cuandoHayaErrorDeCapitalInferiorAlMinimo(plazaparkingMinimo);
            error = true;
        }

        if (solicitud.plazaocupada < plazaocupadaMinimo) {
            callback.cuandoHayaErrorDePlazoInferiorAlMinimo(plazaocupadaMinimo);
            error = true;
        }

        if(!error) {
            callback.cuandoEsteCalculadaLaCuota(solicitud.plazaparking - solicitud.plazaocupada );
        }
    }
}
