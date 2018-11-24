package cfacilito.app.fernandogonzalez.com.superpokedex;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cfacilito.app.fernandogonzalez.com.superpokedex.Adapter.PokemonEvolutionAdapter;
import cfacilito.app.fernandogonzalez.com.superpokedex.Adapter.PokemonTypeAdapter;
import cfacilito.app.fernandogonzalez.com.superpokedex.Common.Common;
import cfacilito.app.fernandogonzalez.com.superpokedex.Model.Pokemon;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonDetail extends Fragment {

    ImageView pokemon_img;
    TextView pokemon_name, pokemon_height, pokemon_weight;
    RecyclerView recycler_type, recycler_weakness, recycler_next_evolution, recycler_prev_evolution;






    static PokemonDetail instance;

    public static PokemonDetail getInstance() {
        if(instance == null)
            instance = new PokemonDetail();
        return instance;
    }

    public PokemonDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);


        Pokemon pokemon = Common.findPokemonsByNum(getArguments().getString("num"));

        pokemon_img = (ImageView)itemView.findViewById(R.id.pokemon_image);
        pokemon_name = (TextView)itemView.findViewById(R.id.name);
        pokemon_height = (TextView)itemView.findViewById(R.id.height);
        pokemon_weight = (TextView)itemView.findViewById(R.id.weight);

        recycler_type = (RecyclerView)itemView.findViewById(R.id.recycler_type);
        recycler_type.setHasFixedSize(true);
        recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_weakness = (RecyclerView)itemView.findViewById(R.id.recycler_weakness);
        recycler_weakness.setHasFixedSize(true);
        recycler_weakness.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_prev_evolution = (RecyclerView)itemView.findViewById(R.id.recycler_prev_evolution);
        recycler_prev_evolution.setHasFixedSize(true);
        recycler_prev_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_next_evolution = (RecyclerView)itemView.findViewById(R.id.recycler_next_evolution);
        recycler_next_evolution.setHasFixedSize(true);
        recycler_next_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        setDetailPokemon(pokemon);


        return itemView;
    }

    private void setDetailPokemon(Pokemon pokemon) {
        //Aqui se carga la imagen con glide
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_img);

        pokemon_name.setText(pokemon.getName());

        pokemon_weight.setText("Weight: "+ pokemon.getWeight());
        pokemon_height.setText("Height: "+ pokemon.getHeight());

        //setType
        PokemonTypeAdapter typeAdapter = new PokemonTypeAdapter(getActivity(),pokemon.getType());
        recycler_type.setAdapter(typeAdapter);

        //setWeakness
        PokemonTypeAdapter weakessnAdapter = new PokemonTypeAdapter(getActivity(),pokemon.getWeaknesses());
        recycler_weakness.setAdapter(weakessnAdapter);

        //setEvolution
        PokemonEvolutionAdapter prevEvolutionAdapter = new PokemonEvolutionAdapter(getActivity(),pokemon.getPrev_evolution());
        recycler_prev_evolution.setAdapter(prevEvolutionAdapter);

        PokemonEvolutionAdapter nextEvolutionAdapter = new PokemonEvolutionAdapter(getActivity(),pokemon.getNext_evolution());
        recycler_next_evolution.setAdapter(nextEvolutionAdapter);

    }

}
