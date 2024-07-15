package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=513daa7b";

    public void exibeManu(){
        System.out.println("Digite o nome da série para consultar: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.trim().replace(" ", "%20") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.trim().replace(" ", "%20") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSerie.totalTemporadas(); i ++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        // expressão lambda(função anonima) para substituir os for acima
        temporadas.forEach(temporada -> temporada.episodios().forEach(episodio -> System.out.println(episodio.titulo())));

        //fluxo de dados - ordenação

//         List<String> nomes = Arrays.asList("Jaque", "Iasmin", "Paulo", "Rodrigo", "Nico");
//         // operação encadeada
//         nomes.stream()
//                 .sorted()
//                 .limit(3)
//                 .filter(n -> n.startsWith("N"))
//                 .map(n -> n.toUpperCase())
//                 .forEach(System.out::println);
        // usar o collect caso a lista seja alterada em algum momento
        // usar o toList caso a lista seja imutavel, não vá ser modificada
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(temporada -> temporada.episodios().stream())
                .collect(Collectors.toList());
        System.out.println("\nTop 5 eposódios: ");
       // dadosEpisodios.add(new DadosEpisodio( "Teste", 3, "10", "2020-01-01"));
        dadosEpisodios.stream()
                // vai filtrar as avaliações e remover da lista os que contem N/A
                .filter(episodio -> !episodio.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

    }

}
