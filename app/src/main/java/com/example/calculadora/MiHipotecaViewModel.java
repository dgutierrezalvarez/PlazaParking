package com.example.calculadora;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiHipotecaViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> calculando = new MutableLiveData<>();
    Executor executor;

    SimuladorHipoteca simulador;

    MutableLiveData<Double> cuota = new MutableLiveData<>();
    MutableLiveData<Integer> errorPlazaparking = new MutableLiveData<>();
    MutableLiveData<Integer> errorPlazaocupada = new MutableLiveData<>();

    public MiHipotecaViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorHipoteca();
    }

    public void calcular(int plazaparking, int plazaocupada) {

        final SimuladorHipoteca.Solicitud solicitud = new SimuladorHipoteca.Solicitud(plazaparking, plazaocupada);

        executor.execute(new Runnable() {
            @Override
            public void run() {

                simulador.calcular(solicitud, new SimuladorHipoteca.Callback() {


                    @Override
                    public void cuandoEsteCalculadaLaCuota(double cuotaResultante) {
                        errorPlazaparking.postValue(null);
                        errorPlazaocupada.postValue(null);
                        cuota.postValue(cuotaResultante);
                    }

                    @Override
                    public void cuandoHayaErrorDeCapitalInferiorAlMinimo(int plazaparkingMinimo) {
                        errorPlazaparking.postValue(plazaparkingMinimo);
                    }

                    @Override
                    public void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazaocupadaMinimo) {
                        errorPlazaocupada.postValue(plazaocupadaMinimo);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);

                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);


                    }
                });
            }
        });
    }
}
