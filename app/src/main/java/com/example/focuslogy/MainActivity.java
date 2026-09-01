package com.example.focuslogy;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.focuslogy.data.mysql.MateriaRepositoryMySQL;
import com.example.focuslogy.data.mysql.SessaoRepositoryMySQL;
import com.example.focuslogy.data.mysql.UsuarioRepositoryMySQL;
import com.example.focuslogy.databinding.ActivityMainBinding;
import com.example.focuslogy.viewmodel.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        factory = new ViewModelFactory(
                new UsuarioRepositoryMySQL(),
                new MateriaRepositoryMySQL(),
                new SessaoRepositoryMySQL()
        );

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                int id = destination.getId();
                if (id == R.id.nav_dashboard || id == R.id.nav_materias || id == R.id.nav_historico || id == R.id.nav_sobre) {
                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                } else {
                    binding.bottomNavigation.setVisibility(View.GONE);
                }
            });
        }
    }

    public ViewModelFactory getFactory() {
        return factory;
    }
}
