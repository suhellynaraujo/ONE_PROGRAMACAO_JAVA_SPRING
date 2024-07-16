package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        System.out.println("\nTop 10 eposódios: ");
       // dadosEpisodios.add(new DadosEpisodio( "Teste", 3, "10", "2020-01-01"));
        dadosEpisodios.stream()
                // vai filtrar as avaliações e remover da lista os que contem N/A
                .filter(episodio -> !episodio.avaliacao().equalsIgnoreCase("N/A"))
                .peek(episodio -> System.out.println("Primeiro Filtro(N/A) " + episodio))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(episodio -> System.out.println("Ordenação " + episodio))
                .limit(10)
                .peek(episodio -> System.out.println("Limite " + episodio))
                .map(episodio -> episodio.titulo().toUpperCase())
                .peek(episodio -> System.out.println("Mapeamento " + episodio))
                .forEach(System.out::println);

//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numero(), d))
//                ).collect(Collectors.toList());
//
//        episodios.forEach(System.out::println);
//
//
//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = leitura.nextInt();
//        // para cada nextInt dar um nextLine para separa a leitura
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                "Episodios: " + e.getTitulo() +
//                                "Data Lançamento: " + e.getDataLancamento().format(formater)
//                ));

    }

}
