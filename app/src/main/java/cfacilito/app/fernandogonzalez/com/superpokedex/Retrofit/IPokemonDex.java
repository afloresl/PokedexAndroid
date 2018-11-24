package cfacilito.app.fernandogonzalez.com.superpokedex.Retrofit;

import io.reactivex.Observable;
import cfacilito.app.fernandogonzalez.com.superpokedex.Model.Pokedex;
import retrofit2.http.GET;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListaPokemon();
}
