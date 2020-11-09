package com.example.calculadora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculadora.databinding.FragmentMiHipotecaBinding;

public class MiHipotecaFragment extends Fragment {
    private FragmentMiHipotecaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MiHipotecaViewModel miHipotecaViewModel = new ViewModelProvider(this).get(MiHipotecaViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int plazaparking = Integer.parseInt(binding.plazaparking.getText().toString());
                int plazaocupada = Integer.parseInt(binding.plazaocupada.getText().toString());

                miHipotecaViewModel.calcular(plazaparking, plazaocupada);
            }
        });

        miHipotecaViewModel.cuota.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double cuota) {
                binding.cuota.setText(String.format("%.2f",cuota));
            }
        });
        miHipotecaViewModel.errorPlazaparking.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer plazaparkingMinimo) {
                if (plazaparkingMinimo != null) {
                    binding.plazaparking.setError("La plaza de parking no puede ser inferior a " + plazaparkingMinimo + " plazas");
                } else {
                    binding.plazaparking.setError(null);
                }
            }
        });

        miHipotecaViewModel.errorPlazaocupada.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer plazaocupadaMinimo) {
                if (plazaocupadaMinimo != null) {
                    binding.plazaocupada.setError("La plaza de parking ocupada no puede ser inferior a" + plazaocupadaMinimo + " plazas");
                } else {
                    binding.plazaocupada.setError(null);
                }
            }
        });
        miHipotecaViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.cuota.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.cuota.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

