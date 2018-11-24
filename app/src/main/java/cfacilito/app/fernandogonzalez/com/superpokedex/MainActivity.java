package cfacilito.app.fernandogonzalez.com.superpokedex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.Replaceable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;

import cfacilito.app.fernandogonzalez.com.superpokedex.Common.Common;
import cfacilito.app.fernandogonzalez.com.superpokedex.Model.Pokemon;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    BroadcastReceiver showPokemonType = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_POKEMON_TYPE)){

                //REPLACE FRAGMENT
                Fragment pokemonType = PokemonType.getInstance();
                String type = intent.getStringExtra("type");
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                pokemonType.setArguments(bundle);

                getSupportFragmentManager().popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon_fragment,pokemonType);
                fragmentTransaction.addToBackStack("type");
                fragmentTransaction.commit();

                toolbar.setTitle("Pokemon type " + type.toUpperCase());
            }
        }
    };
    BroadcastReceiver showDetail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_ENABLE_HOME)){

                getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Boton de regreso en la Toolbar
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                //REPLACE FRAGMENT
                Fragment detailFragment = PokemonDetail.getInstance();
                String num = intent.getStringExtra("num");
                Bundle bundle = new Bundle();
                bundle.putString("num",num);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Set Nombre Pokemon para la Toolbar
                Pokemon pokemon = Common.findPokemonsByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    BroadcastReceiver showEvolution = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_NUM_EVOLUTION)){


                //REPLACE FRAGMENT
                Fragment detailFragment = PokemonDetail.getInstance();
                Bundle bundle = new Bundle();
                String num = intent.getStringExtra("num");
                bundle.putString("num",num);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(detailFragment); //remove current fragment
                fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Set Nombre Pokemon para la Toolbar
                Pokemon pokemon = Common.findPokemonsByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Pokedex" );
        setSupportActionBar(toolbar);


        //Broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail, new IntentFilter(Common.KEY_ENABLE_HOME));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution, new IntentFilter(Common.KEY_NUM_EVOLUTION));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showPokemonType, new IntentFilter(Common.KEY_POKEMON_TYPE));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                toolbar.setTitle("POKEDEX");
                //Clear all fragment detail and  pop to list fragment
                getSupportFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStack("type", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                Fragment pokemonList = PokemonList.getInstance();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(pokemonList);
                fragmentTransaction.replace(R.id.list_pokemon_fragment,pokemonList);
                fragmentTransaction.commit();


                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                break;
                default:
                    break;

        }
        return true;
    }
}
